package com.subafy.subafy.src.features.auth.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.subafy.subafy.src.core.utils.AvatarGenerator
import com.subafy.subafy.src.features.auth.presentation.viewModel.ProfileViewModel

@Composable
fun ProfileScreen(
    userId: String,
    viewModel: ProfileViewModel = hiltViewModel(),
    onNavigateToDashboard: (nickname: String, avatarUrl: String?) -> Unit  // ← pasar datos
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val isJoined  by viewModel.isJoined.collectAsState()
    val error     by viewModel.error.collectAsState()

    var nickname by remember { mutableStateOf("") }
    val dicebearUrl = AvatarGenerator.generateDicebearUrl(userId)
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(isJoined) {
        if (isJoined) {
            val finalNickname = nickname.ifEmpty { "Anon_${userId.takeLast(4)}" }
            onNavigateToDashboard(finalNickname, dicebearUrl)  // ← pasar nickname y avatarUrl
        }
    }

    LaunchedEffect(error) {
        error?.let { snackbarHostState.showSnackbar(message = it) }
    }

    Scaffold(
        containerColor = Color.White,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .padding(top = 40.dp, bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Personaliza tu Perfil",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Elige un apodo y una foto para identificarte en la subasta de forma anónima.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Box(
                modifier = Modifier.size(120.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                AsyncImage(
                    model = dicebearUrl,
                    contentDescription = "Avatar Generado",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(Color(0xFFF3F4F6))
                )
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .offset(x = (-4).dp, y = (-4).dp)
                        .clip(CircleShape)
                        .background(Color(0xFF3B82F6)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Cambiar foto",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Cargar imagen de perfil",
                color = Color(0xFF3B82F6),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(40.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Tu Apodo",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = nickname,
                    onValueChange = { if (it.length <= 20) nickname = it },
                    placeholder = { Text("Ej. PostorVeloz99", color = Color.LightGray) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedBorderColor = Color(0xFF3B82F6),
                        unfocusedBorderColor = Color(0xFFE5E7EB),
                        focusedContainerColor = Color(0xFFF9FAFB),
                        unfocusedContainerColor = Color(0xFFF9FAFB)
                    ),
                    singleLine = true,
                    supportingText = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Tu identidad será privada durante toda la subasta.",
                                color = Color.Gray,
                                fontSize = 11.sp
                            )
                            Text(
                                text = "${nickname.length}/20",
                                color = Color.Gray,
                                fontSize = 11.sp
                            )
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.joinWithProfile(
                        userId = userId,
                        nickname = nickname.ifEmpty { "Anon_${userId.takeLast(4)}" },
                        avatarFile = null,
                        defaultAvatarUrl = dicebearUrl
                    )
                },
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1C64F2),
                    disabledContainerColor = Color(0xFF93C5FD),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text(
                        text = "ENTRAR A LA SUBASTA",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Al continuar, aceptas participar bajo los términos de anonimato.",
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
    }
}
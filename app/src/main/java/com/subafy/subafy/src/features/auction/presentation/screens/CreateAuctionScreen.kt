package com.subafy.subafy.src.features.auction.presentation.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.subafy.subafy.src.core.utils.toFile
import com.subafy.subafy.src.features.auction.presentation.components.AuctionTextField
import com.subafy.subafy.src.features.auction.presentation.components.ImagePickerBox
import com.subafy.subafy.src.features.auction.presentation.components.SubmitAuctionButton
import com.subafy.subafy.src.features.auction.presentation.viewModel.CreateAuctionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAuctionScreen(
    viewModel: CreateAuctionViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onAuctionCreated: () -> Unit
) {
    val context = LocalContext.current
    val isLoading by viewModel.isLoading.collectAsState()
    val isSuccess by viewModel.isSuccess.collectAsState()
    val error by viewModel.error.collectAsState()

    var productName by remember { mutableStateOf("") }
    var lotNumber by remember { mutableStateOf("") }
    var startingPrice by remember { mutableStateOf("") }
    var durationMinutes by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val snackbarHostState = remember { SnackbarHostState() }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    LaunchedEffect(isSuccess) {
        if (isSuccess) {
            onAuctionCreated()
        }
    }

    LaunchedEffect(error) {
        error?.let {
            snackbarHostState.showSnackbar(message = it)
        }
    }

    Scaffold(
        containerColor = Color.White,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Nueva Subasta",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 22.sp,
                        color = Color(0xFF111827)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color(0xFF111827)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color(0xFF111827)
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF3F4F6))
                    .padding(horizontal = 24.dp, vertical = 32.dp)
            ) {
                ImagePickerBox(
                    selectedImageUri = selectedImageUri,
                    onPickImageClick = { imagePickerLauncher.launch("image/*") }
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(24.dp)
            ) {
                Text(
                    text = "Información del artículo",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F2937)
                )

                Spacer(modifier = Modifier.height(20.dp))

                AuctionTextField(
                    value = productName,
                    onValueChange = { productName = it },
                    label = "Nombre del Producto"
                )

                Spacer(modifier = Modifier.height(16.dp))

                AuctionTextField(
                    value = lotNumber,
                    onValueChange = { lotNumber = it },
                    label = "Número de Lote (Ej. 4421)"
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    AuctionTextField(
                        value = startingPrice,
                        onValueChange = { startingPrice = it },
                        label = "Precio ($)",
                        keyboardType = KeyboardType.Number,
                        modifier = Modifier.weight(1f)
                    )

                    AuctionTextField(
                        value = durationMinutes,
                        onValueChange = { durationMinutes = it },
                        label = "Minutos",
                        keyboardType = KeyboardType.Number,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(48.dp))

                SubmitAuctionButton(
                    isLoading = isLoading,
                    onClick = {
                        val file = selectedImageUri?.toFile(context)
                        viewModel.createAuction(productName, lotNumber, startingPrice, durationMinutes, file)
                    }
                )

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}
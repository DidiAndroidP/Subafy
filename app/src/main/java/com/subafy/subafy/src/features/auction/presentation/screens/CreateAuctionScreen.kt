package com.subafy.subafy.src.features.auction.presentation.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
                title = { Text("Nueva Subasta", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            ImagePickerBox(
                selectedImageUri = selectedImageUri,
                onPickImageClick = { imagePickerLauncher.launch("image/*") }
            )

            Spacer(modifier = Modifier.height(24.dp))

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

            AuctionTextField(
                value = startingPrice,
                onValueChange = { startingPrice = it },
                label = "Precio Inicial ($)",
                keyboardType = KeyboardType.Number
            )

            Spacer(modifier = Modifier.height(16.dp))

            AuctionTextField(
                value = durationMinutes,
                onValueChange = { durationMinutes = it },
                label = "Duración (Minutos)",
                keyboardType = KeyboardType.Number
            )

            Spacer(modifier = Modifier.height(32.dp))

            SubmitAuctionButton(
                isLoading = isLoading,
                onClick = {
                    val file = selectedImageUri?.toFile(context)
                    viewModel.createAuction(productName, lotNumber, startingPrice, durationMinutes, file)
                }
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
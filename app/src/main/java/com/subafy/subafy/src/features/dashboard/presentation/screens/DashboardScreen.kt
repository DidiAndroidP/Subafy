package com.subafy.subafy.src.features.dashboard.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.subafy.subafy.src.features.dashboard.presentation.components.AuctionCard
import com.subafy.subafy.src.features.dashboard.presentation.components.CategoryTabs
import com.subafy.subafy.src.features.dashboard.presentation.components.DashboardTopBar
import com.subafy.subafy.src.features.dashboard.presentation.viewModel.DashboardViewModel

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    onNavigateToAuctionLive: (String) -> Unit,
    onNavigateToCreateAuction: () -> Unit
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val auctions by viewModel.auctions.collectAsState()
    val error by viewModel.error.collectAsState()

    var selectedCategory by remember { mutableStateOf("En Vivo") }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(error) {
        error?.let {
            snackbarHostState.showSnackbar(message = it)
        }
    }

    val filteredAuctions = auctions.filter {
        when (selectedCategory) {
            "En Vivo" -> it.status == "active"
            "Próximas" -> it.status == "upcoming"
            "Terminadas" -> it.status == "closed"
            else -> false
        }
    }

    Scaffold(
        containerColor = Color(0xFFF9FAFB),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToCreateAuction,
                containerColor = Color(0xFF1C64F2),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Crear Subasta")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            DashboardTopBar(
                nickname = "OferenteVeloz",
                avatarUrl = null
            )

            CategoryTabs(
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "SUBASTAS POPULARES",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Ver todas >",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color(0xFF8B5CF6),
                    fontWeight = FontWeight.Bold
                )
            }

            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFF8B5CF6))
                }
            } else if (filteredAuctions.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay subastas en esta categoría",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(filteredAuctions) { auction ->
                        AuctionCard(
                            productName = auction.productName,
                            currentPrice = auction.startingPrice,
                            biddersCount = 0,
                            timeRemaining = auction.durationSeconds.toString(),
                            imageUrl = auction.productImageUrl,
                            onBidClick = { onNavigateToAuctionLive(auction.id) }
                        )
                    }
                }
            }
        }
    }
}
package com.subafy.subafy.src.features.dashboard.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.subafy.subafy.src.features.auction.presentation.components.AuctionPriceCard
import com.subafy.subafy.src.features.dashboard.presentation.components.*
import com.subafy.subafy.src.features.dashboard.presentation.viewModel.AuctionDetailViewModel

@Composable
fun AuctionScreen(
    viewModel: AuctionDetailViewModel = hiltViewModel(),
    onNavigateToResult: () -> Unit
) {
    val state by viewModel.auctionState.collectAsState()
    val bids  by viewModel.bidHistory.collectAsState()
    val isConnected by viewModel.isConnected.collectAsState()
    val showBidModal by viewModel.showBidModal.collectAsState()
    val bidError by viewModel.bidError.collectAsState()

    LaunchedEffect(state.isFinished) {
        if (state.isFinished) onNavigateToResult()
    }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFF8F9FA))) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            item {
                AuctionProductHeader(
                    productName    = state.productName,
                    productSubtitle = state.productSubtitle,
                    productImageUrl = state.productImageUrl,
                    timeRemaining  = state.timeRemaining,
                    isConnected    = isConnected
                )
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
                AuctionPriceCard(
                    currentPrice   = state.currentPrice,
                    leaderNickname = state.leaderNickname
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                BidHistoryHeader(totalBids = bids.size)
            }

            items(bids) { bid ->
                BidHistoryItem(bid = bid)
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = androidx.compose.ui.Alignment.BottomCenter
        ) {
            PlaceBidButton(
                enabled   = isConnected && state.isActive,
                onClick   = { viewModel.openBidModal() }
            )
        }

        if (showBidModal) {
            BidConfirmationModal(
                currentPrice = state.currentPrice,
                minBid       = state.currentPrice + state.bidIncrement,
                bidIncrement = state.bidIncrement,
                error        = bidError,
                onConfirm    = { amount -> viewModel.placeBid(amount) },
                onDismiss    = { viewModel.closeBidModal() }
            )
        }
    }
}
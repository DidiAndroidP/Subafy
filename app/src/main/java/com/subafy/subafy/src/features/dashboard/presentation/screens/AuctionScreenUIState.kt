package com.subafy.subafy.src.features.dashboard.presentation.screens

import com.subafy.subafy.src.features.dashboard.presentation.components.BidUiModel


data class AuctionUIState(
    val auctionId:       String?       = null,
    val productName:     String        = "",
    val productSubtitle: String        = "",
    val productImageUrl: String?       = null,
    val currentPrice:    Double        = 0.0,
    val leaderNickname:  String?       = null,
    val leaderId:        String?       = null,
    val timeRemaining:   Int           = 120,
    val bidIncrement:    Double        = 25.0,
    val isActive:        Boolean       = false,
    val isFinished:      Boolean       = false,
    val isConnected:     Boolean       = false,
    val bidHistory:      List<BidUiModel> = emptyList(),
    val showBidModal:    Boolean       = false,
    val bidError:        String?       = null,
)

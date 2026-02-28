package com.subafy.subafy.src.features.dashboard.presentation.screens

import com.subafy.subafy.src.features.dashboard.presentation.components.BidUiModel

data class AuctionUiState(
    val productName:     String  = "",
    val productSubtitle: String  = "",
    val productImageUrl: String? = null,
    val currentPrice:    Double  = 0.0,
    val leaderNickname:  String? = null,
    val timeRemaining:   Int     = 0,
    val isActive:        Boolean = false,
    val isFinished:      Boolean = false,
    val bidIncrement:    Double  = 10.0
)
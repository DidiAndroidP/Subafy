package com.subafy.subafy.src.features.auction.presentation.screens

import com.subafy.subafy.src.features.auction.domain.entities.AuctionFinalResult

data class FinalResultState(
    val isLoading: Boolean = false,
    val finalResult: AuctionFinalResult? = null,
    val error: String? = null
)
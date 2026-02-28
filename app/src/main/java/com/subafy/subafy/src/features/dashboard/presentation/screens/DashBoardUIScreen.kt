package com.subafy.subafy.src.features.dashboard.presentation.screens


import com.subafy.subafy.src.features.dashboard.domain.entities.Auction
import com.subafy.subafy.src.features.dashboard.domain.entities.ActiveAuction

data class DashboardUiState(
    val isLoading: Boolean = false,
    val auctions: List<Auction> = emptyList(),
    val activeAuction: ActiveAuction? = null,
    val error: String? = null
)
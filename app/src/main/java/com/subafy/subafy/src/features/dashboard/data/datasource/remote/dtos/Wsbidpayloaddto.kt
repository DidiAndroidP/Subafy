package com.subafy.subafy.src.features.dashboard.data.datasource.remote.dto

data class WsBidPayloadDto(
    val type:      String = "place_bid",
    val auctionId: String,           // ← NUEVO
    val amount:    Double
)
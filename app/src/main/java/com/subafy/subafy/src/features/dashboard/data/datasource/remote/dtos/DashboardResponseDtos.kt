package com.subafy.subafy.src.features.dashboard.data.datasource.remote.dtos

data class AuctionDto(
    val id: String,
    val productName: String,
    val status: String,
    val startingPrice: Double,
    val productImageUrl: String?,
    val durationSeconds: Int?
)

data class ActiveAuctionDto(
    val id: String,
    val productName: String,
    val status: String,
    val startingPrice: Double,
    val durationSeconds: Int?
)
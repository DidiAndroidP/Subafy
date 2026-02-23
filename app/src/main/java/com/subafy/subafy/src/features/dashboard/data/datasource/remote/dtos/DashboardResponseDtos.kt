package com.subafy.subafy.src.features.dashboard.data.datasource.remote.dtos

data class BaseResponseDto<T>(
    val success: Boolean,
    val data: T
)

data class AuctionDto(
    val id: String,
    val productName: String,
    val status: String,
    val startingPrice: Double,
    val lotNumber: String
)

data class ActiveAuctionDto(
    val id: String,
    val productName: String,
    val status: String,
    val startingPrice: Double,
    val durationSeconds: Int
)
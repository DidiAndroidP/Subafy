package com.subafy.subafy.src.features.auction.data.datasource.remote.dto

data class CreateAuctionRequestDto(
    val productName: String,
    val lotNumber: String,
    val startingPrice: Double,
    val durationSeconds: Int
)

data class AuctionCreatedDataDto(
    val id: String,
    val productName: String,
    val lotNumber: String,
    val productImageUrl: String?,
    val startingPrice: Double,
    val durationSeconds: Int,
    val status: String,
    val createdAt: String
)
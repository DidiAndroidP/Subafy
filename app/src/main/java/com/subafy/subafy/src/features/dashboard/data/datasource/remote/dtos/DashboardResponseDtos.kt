package com.subafy.subafy.src.features.dashboard.data.datasource.remote.dtos

data class AuctionDto(
    val id: String,
    val productName: String,
    val status: String,
    val startingPrice: String,
    val lotNumber: String,
    val productImageUrl: String?,
    val durationSeconds: Int?,
    val createdAt: String
)

data class ActiveAuctionDto(
    val id: String,
    val productName: String,
    val status: String,
    val productImageUrl: String?,
    val lotNumber: String,
    val startingPrice: String,
    val durationSeconds: Int?,
    val createdAt: String
)
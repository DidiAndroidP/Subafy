package com.subafy.subafy.src.features.dashboard.domain.entities

data class Auction(
    val id: String,
    val productName: String,
    val status: String,
    val startingPrice: Double,
    val productImageUrl: String?,
    val durationSeconds: Int
)

data class ActiveAuction(
    val id: String,
    val productName: String,
    val status: String,
    val startingPrice: Double,
    val durationSeconds: Int
)
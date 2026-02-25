package com.subafy.subafy.src.features.auction.domain.entities

import java.io.File

data class CreateAuctionRequest(
    val productName: String,
    val lotNumber: String,
    val startingPrice: Double,
    val durationSeconds: Int,
    val imageFile: File? = null
)

data class AuctionCreated(
    val id: String,
    val productName: String,
    val lotNumber: String,
    val productImageUrl: String?,
    val startingPrice: Double,
    val durationSeconds: Int,
    val status: String,
    val createdAt: String
)
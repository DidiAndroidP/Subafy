package com.subafy.subafy.src.features.dashboard.data.datasource.remote.dtos

data class BaseResponseDto<T>(
    val success: Boolean,
    val data: T
)

// ── GET /auctions ──────────────────────────────────────────────
data class AuctionDto(
    val id:              String,
    val productName:     String,
    val status:          String,
    val startingPrice:   Double,
    val lotNumber:       String,
    val productImageUrl: String?,    // ← nuevo
    val durationSeconds: Int,        // ← nuevo
    val createdAt:       String      // ← nuevo
)

// ── GET /auctions/active ───────────────────────────────────────
data class ActiveAuctionDto(
    val id:              String,
    val productName:     String,
    val status:          String,
    val startingPrice:   Double,
    val durationSeconds: Int,
    val productImageUrl: String?,    // ← nuevo
    val lotNumber:       String,      // ← nuevo
    val createdAt: String
)

// ── GET /auctions/:id ──────────────────────────────────────────
data class AuctionDetailDto(
    val id:              String,
    val productName:     String,
    val status:          String,
    val startingPrice:   Double,
    val durationSeconds: Int,
    val productImageUrl: String?,
    val lotNumber:       String,
    val createdAt:       String
)
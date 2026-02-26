package com.subafy.subafy.src.features.auth.data.datasource.remote.dto

data class BaseResponseDto<T>(
    val success: Boolean,
    val data: T? = null,
    val error: String? = null
)

data class AvatarUploadDto(
    val avatarUrl: String
)

data class WsJoinPayloadDto(
    val type:      String  = "join",
    val auctionId: String,           // ← NUEVO
    val userId:    String,
    val nickname:  String,
    val avatarUrl: String? = null
)

data class AuctionDto(
    val id:              String,
    val productName:     String,
    val status:          String,
    val startingPrice:   String,
    val lotNumber:       String,
    val productImageUrl: String?,    // ← nuevo
    val durationSeconds: Int,        // ← nuevo
    val createdAt:       String      // ← nuevo
)

data class ActiveAuctionDto(
    val id:              String,
    val productName:     String,
    val status:          String,
    val startingPrice:   String,
    val durationSeconds: Int,
    val productImageUrl: String?,    // ← nuevo
    val lotNumber:       String,      // ← nuevo
    val createdAt: String
)

data class AuctionDetailDto(
    val id:              String,
    val productName:     String,
    val status:          String,
    val startingPrice:   String,
    val durationSeconds: Int,
    val productImageUrl: String?,
    val lotNumber:       String,
    val createdAt:       String
)

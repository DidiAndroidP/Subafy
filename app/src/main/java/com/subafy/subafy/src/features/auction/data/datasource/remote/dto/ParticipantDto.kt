package com.subafy.subafy.src.features.auction.data.datasource.remote.dto
data class ParticipantDto(
    val id: String,
    val nickname: String,
    val avatarUrl: String?,
    val bidCount: Int,
    val isActive: Int,
    val lastBidAt: String
)
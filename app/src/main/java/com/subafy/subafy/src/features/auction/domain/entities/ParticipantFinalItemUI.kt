package com.subafy.subafy.src.features.auction.domain.entities

data class ParticipantFinalItemUI(
    val id: String,
    val nickname: String,
    val avatarUrl: String?,
    val bidCount: Int,
    val isActive: Boolean,
    val isWinner: Boolean = false,
    val lastBidAt: String?
)
package com.subafy.subafy.src.features.dashboard.presentation.screens

data class ParticipantItemUi(
    val id: String,
    val nickname: String,
    val avatarUrl: String?,
    val bidCount: Int,
    val isActive: Boolean,
    val timeAgo: String,
    val isWinner: Boolean = false
)
package com.subafy.subafy.src.features.auth.domain.entities

data class UserIdentity(
    val userId: String,
    val nickname: String,
    val avatarUrl: String? = null,
    val auctionId: String = ""
)
package com.subafy.subafy.src.features.auth.data.datasource.remote.dto

data class BaseResponseDto<T>(
    val success: Boolean,
    val data: T
)

data class AvatarUploadDto(
    val avatarUrl: String
)

data class WsJoinPayloadDto(
    val type: String = "join",
    val userId: String,
    val nickname: String,
    val avatarUrl: String? = null
)
package com.subafy.subafy.src.features.auth.data.mapper

import com.subafy.subafy.src.features.auth.data.datasource.remote.dto.AvatarUploadDto
import com.subafy.subafy.src.features.auth.data.datasource.remote.dto.WsJoinPayloadDto
import com.subafy.subafy.src.features.auth.domain.entities.UserIdentity

fun AvatarUploadDto.toDomainUrl(): String {
    return this.avatarUrl
}

fun UserIdentity.toWsJoinPayloadDto(): WsJoinPayloadDto {
    return WsJoinPayloadDto(
        type = "join",
        userId = this.userId,
        nickname = this.nickname,
        avatarUrl = this.avatarUrl,
        auctionId = this.auctionId
    )
}
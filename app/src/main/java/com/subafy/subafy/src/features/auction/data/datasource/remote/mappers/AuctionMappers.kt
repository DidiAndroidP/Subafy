package com.subafy.subafy.src.features.auction.data.datasource.remote.mappers

import com.subafy.subafy.src.features.auction.data.datasource.remote.dto.AuctionCreatedDataDto
import com.subafy.subafy.src.features.auction.data.datasource.remote.dto.CreateAuctionRequestDto
import com.subafy.subafy.src.features.auction.data.datasource.remote.dto.ParticipantDto
import com.subafy.subafy.src.features.auction.domain.entities.AuctionCreated
import com.subafy.subafy.src.features.auction.domain.entities.CreateAuctionRequest
import com.subafy.subafy.src.features.auction.domain.entities.Participant

fun CreateAuctionRequest.toDto(): CreateAuctionRequestDto {
    return CreateAuctionRequestDto(
        productName = this.productName,
        lotNumber = this.lotNumber,
        startingPrice = this.startingPrice,
        durationSeconds = this.durationSeconds
    )
}

fun AuctionCreatedDataDto.toDomain(): AuctionCreated {
    return AuctionCreated(
        id = this.id,
        productName = this.productName,
        lotNumber = this.lotNumber,
        productImageUrl = this.productImageUrl,
        startingPrice = this.startingPrice,
        durationSeconds = this.durationSeconds,
        status = this.status,
        createdAt = this.createdAt
    )
}

fun ParticipantDto.toDomain(): Participant {
    return Participant(
        id = this.id,
        nickname = this.nickname,
        avatarUrl = this.avatarUrl,
        bidCount = this.bidCount,
        isActive = this.isActive == 1,
        lastBidAt = this.lastBidAt
    )
}
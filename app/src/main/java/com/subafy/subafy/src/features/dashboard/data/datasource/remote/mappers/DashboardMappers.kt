package com.subafy.subafy.src.features.dashboard.data.datasource.remote.mappers

import com.subafy.subafy.src.features.dashboard.data.datasource.remote.dtos.ActiveAuctionDto
import com.subafy.subafy.src.features.dashboard.data.datasource.remote.dtos.AuctionDto
import com.subafy.subafy.src.features.dashboard.domain.entities.ActiveAuction
import com.subafy.subafy.src.features.dashboard.domain.entities.Auction

fun AuctionDto.toDomain(): Auction {
    return Auction(
        id              = this.id,
        productName     = this.productName,
        status          = this.status,
        startingPrice   = this.startingPrice,
        lotNumber       = this.lotNumber,
        productImageUrl = this.productImageUrl,   // ← nuevo
        durationSeconds = this.durationSeconds,   // ← nuevo
        createdAt       = this.createdAt          // ← nuevo
    )
}

fun ActiveAuctionDto.toDomain(): ActiveAuction {
    return ActiveAuction(
        id              = this.id,
        productName     = this.productName,
        status          = this.status,
        startingPrice   = this.startingPrice,
        durationSeconds = this.durationSeconds,
        productImageUrl = this.productImageUrl,   // ← nuevo
        lotNumber       = this.lotNumber,         // ← nuev
        createdAt = this.createdAt

    )
}
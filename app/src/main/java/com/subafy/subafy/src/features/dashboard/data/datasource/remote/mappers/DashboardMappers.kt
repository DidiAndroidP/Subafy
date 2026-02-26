package com.subafy.subafy.src.features.dashboard.data.datasource.remote.mappers

import com.subafy.subafy.src.features.dashboard.data.datasource.remote.dtos.ActiveAuctionDto
import com.subafy.subafy.src.features.dashboard.data.datasource.remote.dtos.AuctionDto
import com.subafy.subafy.src.features.dashboard.domain.entities.ActiveAuction
import com.subafy.subafy.src.features.dashboard.domain.entities.Auction

fun AuctionDto.toDomain(): Auction {
    return Auction(
        id = this.id,
        productName = this.productName,
        status = this.status,
        startingPrice = this.startingPrice,
        lotNumber = this.lotNumber
    )
}

fun ActiveAuctionDto.toDomain(): ActiveAuction {
    return ActiveAuction(
        id = this.id,
        productName = this.productName,
        status = this.status,
        startingPrice = this.startingPrice,
        durationSeconds = this.durationSeconds
    )
}
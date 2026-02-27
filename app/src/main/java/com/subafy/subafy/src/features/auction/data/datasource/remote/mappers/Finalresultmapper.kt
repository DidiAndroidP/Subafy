package com.subafy.subafy.src.features.auction.data.datasource.remote.mappers

import com.subafy.subafy.src.features.auction.data.datasource.remote.dto.FinalResultDto
import com.subafy.subafy.src.features.auction.domain.entities.AuctionFinalResult

fun FinalResultDto.toDomain(): AuctionFinalResult {
    return AuctionFinalResult(
        auctionId = this.auctionId,
        winnerNickname = this.winnerNickname,
        winnerAvatarUrl = this.winnerAvatarUrl,
        winnerRank = this.winnerRank,
        productName = this.productName,
        finalPrice = this.finalPrice,
        isCurrentUserWinner = this.isCurrentUserWinner,
        participants = this.participants.map { it.toDomain() }
    )
}
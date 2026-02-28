package com.subafy.subafy.src.features.auction.data.datasource.remote.mappers

import com.subafy.subafy.src.features.auction.data.datasource.remote.dto.FinalResultDto
import com.subafy.subafy.src.features.auction.domain.entities.AuctionFinalResult

fun FinalResultDto.toDomain(currentUserId: String): AuctionFinalResult {
    return AuctionFinalResult(
        auctionId           = this.auctionId,
        winnerNickname      = this.winnerNickname ?: "Sin ganador",
        winnerAvatarUrl     = this.winnerAvatarUrl,
        winnerRank          = 1,
        productName         = this.productName ?: "Subasta",
        finalPrice          = this.finalPrice,
        isCurrentUserWinner = this.winnerUserId != null && this.winnerUserId == currentUserId,
        participants        = emptyList()
    )
}
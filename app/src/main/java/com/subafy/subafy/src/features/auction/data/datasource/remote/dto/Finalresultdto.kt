package com.subafy.subafy.src.features.auction.data.datasource.remote.dto

data class FinalResultDto(
    val auctionId:         String,
    val winnerUserId:      String?,   // ← cambiar winnerId a winnerUserId
    val winnerNickname:    String?,
    val winnerAvatarUrl:   String?,
    val productName:       String?,
    val finalPrice:        Double,
    val totalBids:         Int,
    val totalParticipants: Int
)
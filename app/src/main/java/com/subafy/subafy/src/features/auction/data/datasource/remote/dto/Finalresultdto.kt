package com.subafy.subafy.src.features.auction.data.datasource.remote.dto

data class FinalResultDto(
    val auctionId: String,
    val winnerNickname: String,
    val winnerAvatarUrl: String?,
    val winnerRank: Int,
    val productName: String,
    val finalPrice: Double,
    val isCurrentUserWinner: Boolean,
    val participants: List<ParticipantDto>
)
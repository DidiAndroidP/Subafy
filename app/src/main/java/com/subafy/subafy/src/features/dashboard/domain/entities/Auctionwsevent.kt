package com.subafy.subafy.src.features.dashboard.domain.entities

sealed class AuctionWsEvent {

    object Connected : AuctionWsEvent()
    data class Disconnected(val reason: String) : AuctionWsEvent()

    data class AuctionState(
        val product:         String,
        val productImageUrl: String?,
        val currentPrice:    Double,
        val leaderId:        String?,
        val leaderNickname:  String?,
        val timeRemaining:   Int,
        val isActive:        Boolean
    ) : AuctionWsEvent()


    data class AuctionStarted(
        val auctionId:       String?,
        val product:         String,
        val lotNumber:       String,
        val startingPrice:   Double,
        val durationSeconds: Int
    ) : AuctionWsEvent()


    data class BidAccepted(
        val bidId:         String,
        val userId:        String,
        val nickname:      String,
        val avatarUrl:     String?,
        val newPrice:      Double,
        val timeRemaining: Int,
        val extended:      Boolean
    ) : AuctionWsEvent()


    data class BidRejected(
        val reason:       String,
        val currentPrice: Double
    ) : AuctionWsEvent()


    data class TimerTick(val timeRemaining: Int) : AuctionWsEvent()

    data class AuctionClosed(
        val winnerId:          String?,
        val winnerNickname:    String?,
        val finalPrice:        Double,
        val totalBids:         Int,
        val totalParticipants: Int
    ) : AuctionWsEvent()


    data class ParticipantJoined(
        val userId:         String,
        val nickname:       String,
        val totalConnected: Int
    ) : AuctionWsEvent()

    data class ParticipantLeft(
        val userId:         String,
        val totalConnected: Int
    ) : AuctionWsEvent()


    data class Error(val message: String) : AuctionWsEvent()
}
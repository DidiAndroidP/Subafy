package com.subafy.subafy.src.features.dashboard.data.repositories

import com.google.gson.Gson
import com.subafy.subafy.src.features.dashboard.data.datasource.remote.api.AuctionWsApi
import com.subafy.subafy.src.features.dashboard.data.datasource.remote.dto.WsBidPayloadDto
import com.subafy.subafy.src.features.dashboard.domain.entities.AuctionWsEvent
import com.subafy.subafy.src.features.dashboard.domain.repositories.AuctionRepositoryDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuctionRepositoryDetailImpl @Inject constructor(
    private val wsApi: AuctionWsApi,
    private val gson:  Gson
) : AuctionRepositoryDetail {

    override fun connect(auctionId: String, userId: String, nickname: String, avatarUrl: String?): Flow<AuctionWsEvent> {
        wsApi.connect(auctionId, userId, nickname, avatarUrl)
        return wsApi.observeEvents().map { raw -> parseEvent(raw) }
    }

    override fun placeBid(auctionId: String, amount: Double) {
        wsApi.sendBidEvent(WsBidPayloadDto(auctionId = auctionId, amount = amount))
    }

    override fun disconnect() {
        wsApi.disconnect()
    }

    private fun parseEvent(raw: String): AuctionWsEvent {
        return try {
            val base = gson.fromJson(raw, RawEvent::class.java)
            when (base.type) {
                "connected"    -> AuctionWsEvent.Connected

                "disconnected" -> {
                    val r = gson.fromJson(raw, DisconnectedRaw::class.java)
                    AuctionWsEvent.Disconnected(r.data?.reason ?: "Unknown")
                }

                "auction_state" -> {
                    val e = gson.fromJson(raw, AuctionStateRaw::class.java).data
                    AuctionWsEvent.AuctionState(
                        product         = e.product,
                        productImageUrl = e.productImageUrl,
                        currentPrice    = e.currentPrice,
                        leaderId        = e.leaderId,
                        leaderNickname  = e.leaderNickname,
                        timeRemaining   = e.timeRemaining,
                        isActive        = e.isActive
                    )
                }

                "auction_started" -> {
                    val e = gson.fromJson(raw, AuctionStartedRaw::class.java).data
                    AuctionWsEvent.AuctionStarted(
                        auctionId       = e.auctionId,
                        product         = e.product,
                        lotNumber       = e.lotNumber,
                        startingPrice   = e.startingPrice,
                        durationSeconds = e.durationSeconds
                    )
                }

                "bid_accepted" -> {
                    val e = gson.fromJson(raw, BidAcceptedRaw::class.java).data
                    AuctionWsEvent.BidAccepted(
                        bidId         = e.bidId,
                        userId        = e.userId,
                        nickname      = e.nickname,
                        avatarUrl     = e.avatarUrl,
                        newPrice      = e.newPrice,
                        timeRemaining = e.timeRemaining,
                        extended      = e.extended
                    )
                }

                "bid_rejected" -> {
                    val e = gson.fromJson(raw, BidRejectedRaw::class.java).data
                    AuctionWsEvent.BidRejected(
                        reason       = e.reason,
                        currentPrice = e.currentPrice
                    )
                }

                "timer_tick" -> {
                    val e = gson.fromJson(raw, TimerTickRaw::class.java).data
                    AuctionWsEvent.TimerTick(timeRemaining = e.timeRemaining)
                }

                "auction_closed" -> {
                    val e = gson.fromJson(raw, AuctionClosedRaw::class.java).data
                    AuctionWsEvent.AuctionClosed(
                        winnerId          = e.winnerId,
                        winnerNickname    = e.winnerNickname,
                        finalPrice        = e.finalPrice,
                        totalBids         = e.totalBids,
                        totalParticipants = e.totalParticipants
                    )
                }

                "participant_joined" -> {
                    val e = gson.fromJson(raw, ParticipantJoinedRaw::class.java).data
                    AuctionWsEvent.ParticipantJoined(
                        userId         = e.userId,
                        nickname       = e.nickname,
                        totalConnected = e.totalConnected
                    )
                }

                "participant_left" -> {
                    val e = gson.fromJson(raw, ParticipantLeftRaw::class.java).data
                    AuctionWsEvent.ParticipantLeft(
                        userId         = e.userId,
                        totalConnected = e.totalConnected
                    )
                }

                else -> AuctionWsEvent.Error("Unknown event: ${base.type}")
            }
        } catch (e: Exception) {
            AuctionWsEvent.Error("Parse error: ${e.message}")
        }
    }

    private data class RawEvent(val type: String)
    private data class DisconnectedRaw(val data: DisconnectedData?)
    private data class DisconnectedData(val reason: String)
    private data class AuctionStateRaw(val data: AuctionStateData)
    private data class AuctionStateData(
        val product: String, val productImageUrl: String?,
        val currentPrice: Double, val leaderId: String?,
        val leaderNickname: String?, val timeRemaining: Int, val isActive: Boolean
    )
    private data class AuctionStartedRaw(val data: AuctionStartedData)
    private data class AuctionStartedData(
        val auctionId: String?, val product: String, val lotNumber: String,
        val startingPrice: Double, val durationSeconds: Int
    )
    private data class BidAcceptedRaw(val data: BidAcceptedData)
    private data class BidAcceptedData(
        val bidId: String, val userId: String, val nickname: String,
        val avatarUrl: String?, val newPrice: Double,
        val timeRemaining: Int, val extended: Boolean
    )
    private data class BidRejectedRaw(val data: BidRejectedData)
    private data class BidRejectedData(val reason: String, val currentPrice: Double)
    private data class TimerTickRaw(val data: TimerTickData)
    private data class TimerTickData(val timeRemaining: Int)
    private data class AuctionClosedRaw(val data: AuctionClosedData)
    private data class AuctionClosedData(
        val winnerId: String?, val winnerNickname: String?,
        val finalPrice: Double, val totalBids: Int, val totalParticipants: Int
    )
    private data class ParticipantJoinedRaw(val data: ParticipantJoinedData)
    private data class ParticipantJoinedData(val userId: String, val nickname: String, val totalConnected: Int)
    private data class ParticipantLeftRaw(val data: ParticipantLeftData)
    private data class ParticipantLeftData(val userId: String, val totalConnected: Int)
}
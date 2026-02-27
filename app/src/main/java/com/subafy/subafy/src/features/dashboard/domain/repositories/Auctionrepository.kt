package com.subafy.subafy.src.features.dashboard.domain.repositories

import com.subafy.subafy.src.features.dashboard.domain.entities.AuctionWsEvent
import kotlinx.coroutines.flow.Flow

interface AuctionRepositoryDetail {
    fun connect(auctionId: String, userId: String, nickname: String, avatarUrl: String? = null): Flow<AuctionWsEvent>
    fun placeBid(auctionId: String, amount: Double)
    fun disconnect()
}
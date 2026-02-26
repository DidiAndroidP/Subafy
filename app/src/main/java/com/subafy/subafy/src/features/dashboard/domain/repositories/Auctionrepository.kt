package com.subafy.subafy.src.features.dashboard.domain.repositories

import com.subafy.subafy.src.features.dashboard.domain.entities.AuctionWsEvent
import kotlinx.coroutines.flow.Flow

interface AuctionRepositoryDetail {
    fun connect(): Flow<AuctionWsEvent>
    fun placeBid(amount: Double)
    fun disconnect()
}
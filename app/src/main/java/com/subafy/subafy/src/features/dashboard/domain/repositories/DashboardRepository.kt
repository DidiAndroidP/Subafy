package com.subafy.subafy.src.features.dashboard.domain.repositories

import com.subafy.subafy.src.features.dashboard.domain.entities.ActiveAuction
import com.subafy.subafy.src.features.dashboard.domain.entities.Auction

interface DashboardRepository {
    suspend fun getAuctions(): Result<List<Auction>>
    suspend fun getActiveAuction(): Result<ActiveAuction>
    suspend fun getAuctionById(id: String): Result<Auction>        // ← nuevo
}
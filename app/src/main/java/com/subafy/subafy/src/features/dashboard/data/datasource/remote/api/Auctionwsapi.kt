package com.subafy.subafy.src.features.dashboard.data.datasource.remote.api

import com.subafy.subafy.src.features.dashboard.data.datasource.remote.dto.WsBidPayloadDto
import kotlinx.coroutines.flow.Flow

interface AuctionWsApi {
    fun connect(auctionId: String, userId: String, nickname: String, avatarUrl: String? = null)
    fun disconnect()
    fun sendBidEvent(payload: WsBidPayloadDto)
    fun observeEvents(): Flow<String>
}
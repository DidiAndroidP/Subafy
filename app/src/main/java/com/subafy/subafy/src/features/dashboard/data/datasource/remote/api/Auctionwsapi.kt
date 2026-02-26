package com.subafy.subafy.src.features.dashboard.data.datasource.remote.api

import com.subafy.subafy.src.features.dashboard.data.datasource.remote.dto.WsBidPayloadDto
import kotlinx.coroutines.flow.Flow

interface AuctionWsApi {
    fun connect()
    fun disconnect()
    fun sendBidEvent(payload: WsBidPayloadDto)
    fun observeEvents(): Flow<String>
}
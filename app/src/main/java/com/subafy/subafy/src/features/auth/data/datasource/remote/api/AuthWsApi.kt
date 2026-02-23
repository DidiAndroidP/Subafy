package com.subafy.subafy.src.features.auth.data.datasource.remote.api
import com.subafy.subafy.src.features.auth.data.datasource.remote.dto.WsJoinPayloadDto
import kotlinx.coroutines.flow.Flow

interface AuthWsApi {
    fun connect()
    fun disconnect()
    fun sendJoinEvent(payload: WsJoinPayloadDto)
    fun observeAuctionState(): Flow<String>
}
package com.subafy.subafy.src.features.auth.data.repositories

import com.google.gson.Gson
import com.subafy.subafy.src.features.auth.data.datasource.remote.api.AuthWsApi
import com.subafy.subafy.src.features.auth.data.datasource.remote.dto.WsJoinPayloadDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject

class AuthWsApiImpl @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val gson: Gson
) : AuthWsApi {

    private var webSocket: WebSocket? = null
    private val _auctionStateFlow = MutableSharedFlow<String>(extraBufferCapacity = 10)

    override fun connect() {
        if (webSocket != null) return

        val request = Request.Builder()
            .url("wss://api.auction-onion.shop/ws")
            .build()

        webSocket = okHttpClient.newWebSocket(request, object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                _auctionStateFlow.tryEmit(text)
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                this@AuthWsApiImpl.webSocket = null
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                this@AuthWsApiImpl.webSocket = null
            }
        })
    }

    override fun disconnect() {
        webSocket?.close(1000, "Client disconnected")
        webSocket = null
    }

    override fun sendJoinEvent(payload: WsJoinPayloadDto) {
        val jsonString = gson.toJson(payload)
        webSocket?.send(jsonString)
    }

    override fun observeAuctionState(): Flow<String> {
        return _auctionStateFlow.asSharedFlow()
    }
}
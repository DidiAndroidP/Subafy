package com.subafy.subafy.src.features.dashboard.data.datasource.remote.api

import com.google.gson.Gson
import com.subafy.subafy.src.features.dashboard.data.datasource.remote.dto.WsBidPayloadDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject

class AuctionWsApiImpl @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val gson:         Gson
) : AuctionWsApi {

    private var webSocket: WebSocket? = null
    private val _eventsFlow = MutableSharedFlow<String>(extraBufferCapacity = 64)

    override fun connect() {
        if (webSocket != null) return

        val request = Request.Builder()
            .url("wss://api.auction-onion.shop/ws")
            .build()

        webSocket = okHttpClient.newWebSocket(request, object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                _eventsFlow.tryEmit(text)
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                this@AuctionWsApiImpl.webSocket = null
                // Emitir evento de desconexión para que el repo lo maneje
                _eventsFlow.tryEmit("""{"type":"disconnected","data":{"reason":"$reason"}}""")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                this@AuctionWsApiImpl.webSocket = null
                _eventsFlow.tryEmit("""{"type":"disconnected","data":{"reason":"${t.message}"}}""")
            }

            override fun onOpen(webSocket: WebSocket, response: Response) {
                _eventsFlow.tryEmit("""{"type":"connected"}""")
            }
        })
    }

    override fun disconnect() {
        webSocket?.close(1000, "Client disconnected")
        webSocket = null
    }

    override fun sendBidEvent(payload: WsBidPayloadDto) {
        val json = gson.toJson(payload)
        webSocket?.send(json)
    }

    override fun observeEvents(): Flow<String> = _eventsFlow.asSharedFlow()
}
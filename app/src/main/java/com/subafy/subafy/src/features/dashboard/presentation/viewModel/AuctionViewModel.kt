package com.subafy.subafy.src.features.auction.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subafy.subafy.src.features.auction.presentation.components.BidUiModel
import com.subafy.subafy.src.features.dashboard.presentation.screens.AuctionUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuctionViewModel @Inject constructor(
    // TODO: inyecta tu WsRepository aquí cuando lo tengas
    // private val wsRepository: AuctionWsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuctionUIState())
    val uiState: StateFlow<AuctionUIState> = _uiState.asStateFlow()

    // Shortcuts para que la Screen pueda observar directamente
    val auctionState: StateFlow<AuctionUIState> = _uiState
    val bidHistory    get() = _uiState.map { it.bidHistory }.stateIn(
        viewModelScope, SharingStarted.Eagerly, emptyList()
    )
    val isConnected   get() = _uiState.map { it.isConnected }.stateIn(
        viewModelScope, SharingStarted.Eagerly, false
    )
    val showBidModal  get() = _uiState.map { it.showBidModal }.stateIn(
        viewModelScope, SharingStarted.Eagerly, false
    )
    val bidError      get() = _uiState.map { it.bidError }.stateIn(
        viewModelScope, SharingStarted.Eagerly, null
    )

    private var _priceBeforeBid: Double = 0.0

    init {
        observeWebSocketEvents()
    }

    private fun observeWebSocketEvents() {
        viewModelScope.launch {
            // Descomenta cuando tengas el WsRepository:
            //
            // wsRepository.events.collect { event ->
            //     when (event) {
            //         is ServerEvent.Connected -> {
            //             _uiState.update { it.copy(isConnected = true) }
            //         }
            //         is ServerEvent.Disconnected -> {
            //             _uiState.update { it.copy(isConnected = false) }
            //         }
            //         is ServerEvent.AuctionState -> {
            //             _uiState.update { it.copy(
            //                 currentPrice   = event.data.currentPrice,
            //                 leaderNickname = event.data.leaderNickname,
            //                 leaderId       = event.data.leaderId,
            //                 timeRemaining  = event.data.timeRemaining,
            //                 isActive       = event.data.isActive,
            //                 productName    = event.data.product,
            //             )}
            //         }
            //         is ServerEvent.AuctionStarted -> {
            //             _uiState.update { it.copy(
            //                 auctionId     = event.data.auctionId,
            //                 productName   = event.data.product,
            //                 timeRemaining = event.data.durationSeconds,
            //                 isActive      = true,
            //             )}
            //         }
            //         is ServerEvent.BidAccepted -> {
            //             _uiState.update { it.copy(
            //                 currentPrice   = event.data.newPrice,
            //                 leaderNickname = event.data.nickname,
            //                 leaderId       = event.data.userId,
            //                 timeRemaining  = event.data.timeRemaining,
            //                 showBidModal   = false,
            //                 bidError       = null,
            //                 bidHistory     = listOf(buildBidUiModel(event.data)) + it.bidHistory
            //             )}
            //         }
            //         is ServerEvent.BidRejected -> {
            //             _uiState.update { it.copy(
            //                 currentPrice = _priceBeforeBid,
            //                 bidError     = event.data.reason
            //             )}
            //         }
            //         is ServerEvent.TimerTick -> {
            //             _uiState.update { it.copy(timeRemaining = event.data.timeRemaining) }
            //         }
            //         is ServerEvent.AuctionClosed -> {
            //             _uiState.update { it.copy(isFinished = true) }
            //         }
            //         else -> Unit
            //     }
            // }
        }
    }

    // ── Acciones de la UI ──────────────────────────────────────

    fun openBidModal() {
        _uiState.update { it.copy(showBidModal = true, bidError = null) }
    }

    fun closeBidModal() {
        _uiState.update { it.copy(showBidModal = false, bidError = null) }
    }

    fun placeBid(amount: Double) {
        viewModelScope.launch {
            _priceBeforeBid = _uiState.value.currentPrice
            // Optimistic update
            _uiState.update { it.copy(currentPrice = amount) }
            // wsRepository.placeBid(amount)
        }
    }

    override fun onCleared() {
        super.onCleared()
        // wsRepository.disconnect()
    }
}
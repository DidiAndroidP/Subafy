
package com.subafy.subafy.src.features.dashboard.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subafy.subafy.src.features.dashboard.presentation.components.BidUiModel
import com.subafy.subafy.src.features.dashboard.presentation.screens.AuctionUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.subafy.subafy.src.features.dashboard.domain.entities.AuctionWsEvent
import com.subafy.subafy.src.features.dashboard.domain.usecase.ConnectToAuctionUseCase
import com.subafy.subafy.src.features.dashboard.domain.usecase.DisconnectFromAuctionUseCase
import com.subafy.subafy.src.features.dashboard.domain.usecase.PlaceBidUseCase

@HiltViewModel
class AuctionDetailViewModel @Inject constructor(
    private val connectToAuctionUseCase:      ConnectToAuctionUseCase,
    private val placeBidUseCase:              PlaceBidUseCase,
    private val disconnectFromAuctionUseCase: DisconnectFromAuctionUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuctionUIState())
    val uiState: StateFlow<AuctionUIState> = _uiState.asStateFlow()

    val auctionState: StateFlow<AuctionUIState> = _uiState
    val bidHistory   = _uiState.map { it.bidHistory }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    val isConnected  = _uiState.map { it.isConnected }.stateIn(viewModelScope, SharingStarted.Eagerly, false)
    val showBidModal = _uiState.map { it.showBidModal }.stateIn(viewModelScope, SharingStarted.Eagerly, false)
    val bidError     = _uiState.map { it.bidError }.stateIn(viewModelScope, SharingStarted.Eagerly, null)

    private var _priceBeforeBid: Double = 0.0

    init {
        observeWebSocketEvents()
    }

    private fun observeWebSocketEvents() {
        viewModelScope.launch {
            connectToAuctionUseCase().collect { event ->
                when (event) {
                    is AuctionWsEvent.Connected -> {
                        _uiState.update { it.copy(isConnected = true) }
                    }
                    is AuctionWsEvent.Disconnected -> {
                        _uiState.update { it.copy(isConnected = false) }
                    }
                    is AuctionWsEvent.AuctionState -> {
                        _uiState.update { it.copy(
                            productName     = event.product,
                            productImageUrl = event.productImageUrl,
                            currentPrice    = event.currentPrice,
                            leaderNickname  = event.leaderNickname,
                            leaderId        = event.leaderId,
                            timeRemaining   = event.timeRemaining,
                            isActive        = event.isActive,
                        )}
                    }
                    is AuctionWsEvent.AuctionStarted -> {
                        _uiState.update { it.copy(
                            auctionId       = event.auctionId,
                            productName     = event.product,
                            productSubtitle = "Lote #${event.lotNumber}",
                            timeRemaining   = event.durationSeconds,
                            isActive        = true,
                        )}
                    }
                    is AuctionWsEvent.BidAccepted -> {
                        val increment = event.newPrice - _uiState.value.currentPrice
                        val newBid = BidUiModel(
                            userId    = event.userId,
                            nickname  = event.nickname,
                            avatarUrl = event.avatarUrl,
                            amount    = event.newPrice,
                            increment = if (increment > 0) increment else _uiState.value.bidIncrement,
                            timeAgo   = "Hace 0s",
                            isLeader  = true
                        )
                        _uiState.update { it.copy(
                            currentPrice   = event.newPrice,
                            leaderNickname = event.nickname,
                            leaderId       = event.userId,
                            timeRemaining  = event.timeRemaining,
                            showBidModal   = false,
                            bidError       = null,
                            bidHistory     = listOf(newBid) + it.bidHistory
                                .map { bid -> bid.copy(isLeader = false) }
                        )}
                    }
                    is AuctionWsEvent.BidRejected -> {
                        _uiState.update { it.copy(
                            currentPrice = _priceBeforeBid,
                            bidError     = event.reason
                        )}
                    }
                    is AuctionWsEvent.TimerTick -> {
                        _uiState.update { it.copy(timeRemaining = event.timeRemaining) }
                    }
                    is AuctionWsEvent.AuctionClosed -> {
                        _uiState.update { it.copy(isFinished = true) }
                    }
                    // ParticipantJoined/Left los maneja ParticipantsViewModel
                    else -> Unit
                }
            }
        }
    }

    fun openBidModal() {
        _uiState.update { it.copy(showBidModal = true, bidError = null) }
    }

    fun closeBidModal() {
        _uiState.update { it.copy(showBidModal = false, bidError = null) }
    }

    fun placeBid(amount: Double) {
        viewModelScope.launch {
            _priceBeforeBid = _uiState.value.currentPrice
            _uiState.update { it.copy(currentPrice = amount) } // optimistic update
            placeBidUseCase(amount)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disconnectFromAuctionUseCase()
    }
}
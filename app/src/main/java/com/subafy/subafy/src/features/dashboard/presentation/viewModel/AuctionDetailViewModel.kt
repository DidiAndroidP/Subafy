package com.subafy.subafy.src.features.dashboard.presentation.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subafy.subafy.src.features.dashboard.domain.entities.AuctionWsEvent
import com.subafy.subafy.src.features.dashboard.domain.usecase.ConnectToAuctionUseCase
import com.subafy.subafy.src.features.dashboard.domain.usecase.DisconnectFromAuctionUseCase
import com.subafy.subafy.src.features.dashboard.domain.usecase.PlaceBidUseCase
import com.subafy.subafy.src.features.dashboard.presentation.components.BidUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

data class AuctionUiState(
    val productName:     String  = "",
    val productSubtitle: String  = "",
    val productImageUrl: String? = null,
    val currentPrice:    Double  = 0.0,
    val leaderNickname:  String? = null,
    val timeRemaining:   Int     = 0,
    val isActive:        Boolean = false,
    val isFinished:      Boolean = false,
    val bidIncrement:    Double  = 10.0
)

@HiltViewModel
class AuctionDetailViewModel @Inject constructor(
    savedStateHandle:              SavedStateHandle,
    private val connectUseCase:    ConnectToAuctionUseCase,
    private val placeBidUseCase:   PlaceBidUseCase,
    private val disconnectUseCase: DisconnectFromAuctionUseCase
) : ViewModel() {

    // ── Parámetros de navegación ────────────────────────────────
    private val auctionId: String = savedStateHandle["auctionId"] ?: ""
    private val userId:    String = savedStateHandle["userId"]    ?: ""
    private val nickname:  String = savedStateHandle["nickname"]  ?: "Anon"
    private val avatarUrl: String? = savedStateHandle["avatarUrl"]

    // ── Estado UI ───────────────────────────────────────────────
    private val _auctionState = MutableStateFlow(AuctionUiState())
    val auctionState: StateFlow<AuctionUiState> = _auctionState.asStateFlow()

    private val _bidHistory = MutableStateFlow<List<BidUiModel>>(emptyList())
    val bidHistory: StateFlow<List<BidUiModel>> = _bidHistory.asStateFlow()

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()

    private val _showBidModal = MutableStateFlow(false)
    val showBidModal: StateFlow<Boolean> = _showBidModal.asStateFlow()

    private val _bidError = MutableStateFlow<String?>(null)
    val bidError: StateFlow<String?> = _bidError.asStateFlow()

    private var priceBeforeBid: Double = 0.0

    init {
        observeEvents()
    }

    private fun observeEvents() {
        viewModelScope.launch {
            connectUseCase(auctionId, userId, nickname, avatarUrl)
                .collect { event -> handleEvent(event) }
        }
    }

    private fun handleEvent(event: AuctionWsEvent) {
        when (event) {
            is AuctionWsEvent.Connected -> _isConnected.value = true

            is AuctionWsEvent.Disconnected -> _isConnected.value = false

            is AuctionWsEvent.AuctionState -> {
                _auctionState.update { it.copy(
                    productName     = event.product,
                    productImageUrl = event.productImageUrl,
                    currentPrice    = event.currentPrice,
                    leaderNickname  = event.leaderNickname,
                    timeRemaining   = event.timeRemaining,
                    isActive        = event.isActive
                )}
            }

            is AuctionWsEvent.AuctionStarted -> {
                _auctionState.update { it.copy(
                    productName   = event.product,
                    currentPrice  = event.startingPrice,
                    timeRemaining = event.durationSeconds,
                    isActive      = true
                )}
            }

            is AuctionWsEvent.BidAccepted -> {
                _bidError.value = null
                _auctionState.update { it.copy(
                    currentPrice   = event.newPrice,
                    leaderNickname = event.nickname,
                    timeRemaining  = event.timeRemaining
                )}
                val bid = BidUiModel(
                    userId    = event.userId,
                    nickname  = event.nickname,
                    avatarUrl = event.avatarUrl,
                    amount    = event.newPrice,
                    increment = _auctionState.value.bidIncrement,  // ← 10.0
                    timeAgo   = "ahora",
                    isLeader  = true   // ← el que puja es el nuevo líder
                )
                _bidHistory.update { currentList ->
                    // El anterior líder ya no es líder
                    val updated = currentList.map { it.copy(isLeader = false) }
                    listOf(bid) + updated
                }
            }

            is AuctionWsEvent.BidRejected -> {
                // Rollback precio optimista
                _auctionState.update { it.copy(currentPrice = priceBeforeBid) }
                _bidError.value = event.reason
            }

            is AuctionWsEvent.TimerTick -> {
                _auctionState.update { it.copy(timeRemaining = event.timeRemaining) }
            }

            is AuctionWsEvent.AuctionClosed -> {
                _auctionState.update { it.copy(isActive = false, isFinished = true) }
            }

            is AuctionWsEvent.ParticipantJoined -> { /* opcional: mostrar notificación */ }
            is AuctionWsEvent.ParticipantLeft   -> { /* opcional */ }
            is AuctionWsEvent.Error             -> { /* opcional: mostrar snackbar */ }
        }
    }

    fun placeBid(amount: Double) {
        priceBeforeBid = _auctionState.value.currentPrice
        _auctionState.update { it.copy(currentPrice = amount) } // optimistic
        _showBidModal.value = false
        placeBidUseCase(auctionId, amount)
    }

    fun openBidModal()  { _showBidModal.value = true;  _bidError.value = null }
    fun closeBidModal() { _showBidModal.value = false }

    override fun onCleared() {
        super.onCleared()
        disconnectUseCase()
    }
}
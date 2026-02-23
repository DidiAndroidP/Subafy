package com.subafy.subafy.src.features.dashboard.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subafy.subafy.src.features.dashboard.domain.entities.ActiveAuction
import com.subafy.subafy.src.features.dashboard.domain.entities.Auction
import com.subafy.subafy.src.features.dashboard.domain.usecase.GetActiveAuctionUseCase
import com.subafy.subafy.src.features.dashboard.domain.usecase.GetAuctionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getAuctionsUseCase: GetAuctionsUseCase,
    private val getActiveAuctionUseCase: GetActiveAuctionUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _auctions = MutableStateFlow<List<Auction>>(emptyList())
    val auctions: StateFlow<List<Auction>> = _auctions.asStateFlow()

    private val _activeAuction = MutableStateFlow<ActiveAuction?>(null)
    val activeAuction: StateFlow<ActiveAuction?> = _activeAuction.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        fetchDashboardData()
    }

    fun fetchDashboardData() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val auctionsResult = getAuctionsUseCase()
            val activeAuctionResult = getActiveAuctionUseCase()

            auctionsResult.onSuccess { auctionsList ->
                _auctions.value = auctionsList
            }.onFailure { exception ->
                _error.value = exception.message
            }

            activeAuctionResult.onSuccess { activeAuctionData ->
                _activeAuction.value = activeAuctionData
            }.onFailure { exception ->
                if (_error.value == null) {
                    _error.value = exception.message
                }
            }

            _isLoading.value = false
        }
    }
}
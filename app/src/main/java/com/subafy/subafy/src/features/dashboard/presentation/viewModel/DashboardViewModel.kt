package com.subafy.subafy.src.features.dashboard.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subafy.subafy.src.features.dashboard.domain.entities.ActiveAuction
import com.subafy.subafy.src.features.dashboard.domain.entities.Auction
import com.subafy.subafy.src.features.dashboard.domain.usecase.GetActiveAuctionUseCase
import com.subafy.subafy.src.features.dashboard.domain.usecase.GetAuctionsUseCase
import com.subafy.subafy.src.features.dashboard.domain.usecase.GetUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getAuctionsUseCase: GetAuctionsUseCase,
    private val getActiveAuctionUseCase: GetActiveAuctionUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _auctions = MutableStateFlow<List<Auction>>(emptyList())
    val auctions: StateFlow<List<Auction>> = _auctions.asStateFlow()

    private val _activeAuction = MutableStateFlow<ActiveAuction?>(null)
    val activeAuction: StateFlow<ActiveAuction?> = _activeAuction.asStateFlow()

    private val _nickname = MutableStateFlow("Usuario")
    val nickname: StateFlow<String> = _nickname.asStateFlow()

    private val _avatarUrl = MutableStateFlow<String?>(null)
    val avatarUrl: StateFlow<String?> = _avatarUrl.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadUserData()
        fetchDashboardData()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            getUserProfileUseCase.getNickname().collectLatest { name ->
                _nickname.value = name.ifEmpty { "OferenteVeloz" }
            }
        }
        viewModelScope.launch {
            getUserProfileUseCase.getAvatar().collectLatest { url ->
                _avatarUrl.value = url
            }
        }
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
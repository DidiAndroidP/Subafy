package com.subafy.subafy.src.features.dashboard.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subafy.subafy.src.features.dashboard.domain.usecase.GetActiveAuctionUseCase
import com.subafy.subafy.src.features.dashboard.domain.usecase.GetAuctionsUseCase
import com.subafy.subafy.src.features.dashboard.presentation.screens.DashboardUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getAuctionsUseCase: GetAuctionsUseCase,
    private val getActiveAuctionUseCase: GetActiveAuctionUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        fetchDashboardData()
    }

    fun fetchDashboardData() {
        viewModelScope.launch {

            _uiState.update { state ->
                state.copy(
                    isLoading = true,
                    error = null
                )
            }

            val auctionsResult = getAuctionsUseCase()
            val activeAuctionResult = getActiveAuctionUseCase()

            auctionsResult.onSuccess { auctionsList ->
                _uiState.update { state ->
                    state.copy(auctions = auctionsList)
                }
            }.onFailure { exception ->
                _uiState.update { state ->
                    state.copy(error = exception.message)
                }
            }

            activeAuctionResult.onSuccess { activeAuctionData ->
                _uiState.update { state ->
                    state.copy(activeAuction = activeAuctionData)
                }
            }.onFailure { exception ->
                _uiState.update { state ->
                    state.copy(
                        error = state.error ?: exception.message
                    )
                }
            }

            _uiState.update { state ->
                state.copy(isLoading = false)
            }
        }
    }
}
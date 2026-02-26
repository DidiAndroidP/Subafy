package com.subafy.subafy.src.features.auction.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subafy.subafy.src.features.auction.domain.usecase.GetFinalResultUseCase
import com.subafy.subafy.src.features.auction.presentation.screens.FinalResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FinalResultViewModel @Inject constructor(
    private val getFinalResultUseCase: GetFinalResultUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(FinalResultState())
    val state: StateFlow<FinalResultState> = _state.asStateFlow()

    fun loadFinalResult(auctionId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            val result = getFinalResultUseCase(auctionId)

            result.onSuccess { finalResult ->
                _state.value = _state.value.copy(
                    isLoading = false,
                    finalResult = finalResult
                )
            }.onFailure { exception ->
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = exception.message ?: "Error desconocido"
                )
            }
        }
    }
}
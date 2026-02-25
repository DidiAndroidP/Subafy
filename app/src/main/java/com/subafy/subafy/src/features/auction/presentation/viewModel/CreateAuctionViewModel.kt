package com.subafy.subafy.src.features.auction.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subafy.subafy.src.features.auction.domain.entities.CreateAuctionRequest
import com.subafy.subafy.src.features.auction.domain.usecase.CreateAuctionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CreateAuctionViewModel @Inject constructor(
    private val createAuctionUseCase: CreateAuctionUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isSuccess = MutableStateFlow(false)
    val isSuccess: StateFlow<Boolean> = _isSuccess.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun createAuction(name: String, lot: String, priceStr: String, durationMinutesStr: String, imageFile: File?) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val price = priceStr.toDoubleOrNull()
                val minutes = durationMinutesStr.toIntOrNull()

                if (name.isBlank() || lot.isBlank() || price == null || minutes == null) {
                    _error.value = "Por favor, completa todos los campos correctamente."
                    _isLoading.value = false
                    return@launch
                }

                val request = CreateAuctionRequest(
                    productName = name,
                    lotNumber = lot,
                    startingPrice = price,
                    durationSeconds = minutes * 60,
                    imageFile = imageFile
                )

                val result = createAuctionUseCase(request)

                result.onSuccess {
                    _isSuccess.value = true
                }.onFailure { exception ->
                    _error.value = exception.message
                }
            } catch (e: Exception) {
                _error.value = "Error inesperado"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
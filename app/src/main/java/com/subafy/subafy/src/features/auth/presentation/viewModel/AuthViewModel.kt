package com.subafy.subafy.src.features.auth.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subafy.subafy.src.features.auth.domain.entities.UserIdentity
import com.subafy.subafy.src.features.auth.domain.usecase.JoinAuctionUseCase
import com.subafy.subafy.src.features.auth.domain.usecase.ObserveAuctionStateUseCase
import com.subafy.subafy.src.features.auth.domain.usecase.UploadAvatarUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val uploadAvatarUseCase: UploadAvatarUseCase,
    private val joinAuctionUseCase: JoinAuctionUseCase,
    private val observeAuctionStateUseCase: ObserveAuctionStateUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _userId = MutableStateFlow("")
    val userId: StateFlow<String> = _userId.asStateFlow()

    private val _isJoined = MutableStateFlow(false)
    val isJoined: StateFlow<Boolean> = _isJoined.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        generateTemporaryId()
        observeAuctionState()
    }

    private fun generateTemporaryId() {
        val randomId = "user_" + UUID.randomUUID().toString().substring(0, 8)
        _userId.value = randomId
    }

    fun enterAuction(nickname: String = "Anon", avatarFile: File? = null, defaultAvatarUrl: String? = null) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                var finalAvatarUrl: String? = defaultAvatarUrl

                if (avatarFile != null) {
                    val result = uploadAvatarUseCase(_userId.value, avatarFile)
                    result.onSuccess { url ->
                        finalAvatarUrl = url
                    }.onFailure { exception ->
                        _isLoading.value = false
                        _error.value = "Error al subir imagen: ${exception.message}"
                        return@launch
                    }
                }

                val identity = UserIdentity(
                    userId = _userId.value,
                    nickname = nickname,
                    avatarUrl = finalAvatarUrl
                )

                joinAuctionUseCase(identity)

                _isLoading.value = false
                _isJoined.value = true

            } catch (e: Exception) {
                _isLoading.value = false
                _error.value = "Error inesperado: ${e.message}"
            }
        }
    }

    private fun observeAuctionState() {
        viewModelScope.launch {
            observeAuctionStateUseCase().collect { stateJson ->

            }
        }
    }
}
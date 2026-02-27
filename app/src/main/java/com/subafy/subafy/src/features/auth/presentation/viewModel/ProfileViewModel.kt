package com.subafy.subafy.src.features.auth.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subafy.subafy.src.features.auth.domain.entities.UserIdentity
import com.subafy.subafy.src.features.auth.domain.usecase.JoinAuctionUseCase
import com.subafy.subafy.src.features.auth.domain.usecase.UploadAvatarUseCase
import com.subafy.subafy.src.features.dashboard.data.repositories.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val uploadAvatarUseCase: UploadAvatarUseCase,
    private val joinAuctionUseCase: JoinAuctionUseCase,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isJoined = MutableStateFlow(false)
    val isJoined: StateFlow<Boolean> = _isJoined.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun joinWithProfile(userId: String, nickname: String, avatarFile: File?, defaultAvatarUrl: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                var finalAvatarUrl = defaultAvatarUrl

                if (avatarFile != null) {
                    val result = uploadAvatarUseCase(userId, avatarFile)
                    result.onSuccess { url ->
                        finalAvatarUrl = url
                    }.onFailure { exception ->
                        _isLoading.value = false
                        _error.value = "Error al subir imagen: ${exception.message}"
                        return@launch
                    }
                }

                userPreferences.saveUser(nickname, finalAvatarUrl)

                val identity = UserIdentity(
                    userId = userId,
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
}
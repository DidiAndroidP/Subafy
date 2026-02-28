package com.subafy.subafy.src.features.auth.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subafy.subafy.src.features.auth.domain.usecase.JoinAuctionUseCase
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
    private val joinAuctionUseCase:  JoinAuctionUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _userId = MutableStateFlow("")
    val userId: StateFlow<String> = _userId.asStateFlow()

    private val _nickname = MutableStateFlow("Anon")
    val nickname: StateFlow<String> = _nickname.asStateFlow()

    private val _avatarUrl = MutableStateFlow<String?>(null)
    val avatarUrl: StateFlow<String?> = _avatarUrl.asStateFlow()

    private val _isJoined = MutableStateFlow(false)
    val isJoined: StateFlow<Boolean> = _isJoined.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        generateTemporaryId()
    }

    private fun generateTemporaryId() {
        _userId.value = "user_" + UUID.randomUUID().toString().substring(0, 8)
    }

    // ← Llamado desde NavigationWrapper cuando ProfileScreen termina
    fun updateProfile(nickname: String, avatarUrl: String?) {
        _nickname.value = nickname
        _avatarUrl.value = avatarUrl
    }

    fun enterAuction() {
        viewModelScope.launch {
            _isJoined.value = true
        }
    }
}
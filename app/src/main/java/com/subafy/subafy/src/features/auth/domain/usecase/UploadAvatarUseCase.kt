package com.subafy.subafy.src.features.auth.domain.usecase

import com.subafy.subafy.src.features.auth.domain.repositories.AuthRepository
import java.io.File
import javax.inject.Inject

class UploadAvatarUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(userId: String, imageFile: File): Result<String> {
        return repository.uploadAvatar(userId, imageFile)
    }
}
package com.subafy.subafy.src.features.auth.domain.usecase

import com.subafy.subafy.src.features.auth.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveAuctionStateUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): Flow<String> {
        return repository.getAuctionStateFlow()
    }
}
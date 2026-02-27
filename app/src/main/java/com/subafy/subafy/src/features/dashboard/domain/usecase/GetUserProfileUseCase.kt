package com.subafy.subafy.src.features.dashboard.domain.usecase

import com.subafy.subafy.src.features.dashboard.domain.repositories.DashboardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val repository: DashboardRepository
) {
    fun getNickname(): Flow<String> = repository.getUserNickname()
    fun getAvatar(): Flow<String?> = repository.getUserAvatar()
}
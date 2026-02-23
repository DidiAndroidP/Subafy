package com.subafy.subafy.src.features.dashboard.domain.usecase

import com.subafy.subafy.src.features.dashboard.domain.entities.ActiveAuction
import com.subafy.subafy.src.features.dashboard.domain.repositories.DashboardRepository
import javax.inject.Inject

class GetActiveAuctionUseCase @Inject constructor(
    private val repository: DashboardRepository
) {
    suspend operator fun invoke(): Result<ActiveAuction> {
        return repository.getActiveAuction()
    }
}
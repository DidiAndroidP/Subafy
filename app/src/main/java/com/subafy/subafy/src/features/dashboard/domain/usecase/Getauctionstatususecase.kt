package com.subafy.subafy.src.features.dashboard.domain.usecase

import com.subafy.subafy.src.features.dashboard.domain.repositories.AuctionRepositoryDetail
import javax.inject.Inject

class GetAuctionStatusUseCase @Inject constructor(
    private val repository: AuctionRepositoryDetail
) {
    suspend operator fun invoke(auctionId: String): String? {
        return repository.getAuctionStatus(auctionId)
    }
}
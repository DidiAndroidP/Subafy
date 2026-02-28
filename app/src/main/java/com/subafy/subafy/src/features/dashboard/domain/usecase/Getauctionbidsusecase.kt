package com.subafy.subafy.src.features.dashboard.domain.usecase

import com.subafy.subafy.src.features.dashboard.domain.repositories.AuctionRepositoryDetail
import com.subafy.subafy.src.features.dashboard.presentation.components.BidUiModel
import javax.inject.Inject

class GetAuctionBidsUseCase @Inject constructor(
    private val repository: AuctionRepositoryDetail
) {
    suspend operator fun invoke(auctionId: String): Result<List<BidUiModel>> {
        return repository.getBids(auctionId)
    }
}
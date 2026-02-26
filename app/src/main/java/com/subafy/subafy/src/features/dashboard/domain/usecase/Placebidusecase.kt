package com.subafy.subafy.src.features.dashboard.domain.usecase

import com.subafy.subafy.src.features.dashboard.domain.repositories.AuctionRepositoryDetail
import javax.inject.Inject

class PlaceBidUseCase @Inject constructor(
    private val repository: AuctionRepositoryDetail
) {
    operator fun invoke(amount: Double) {
        repository.placeBid(amount)
    }
}
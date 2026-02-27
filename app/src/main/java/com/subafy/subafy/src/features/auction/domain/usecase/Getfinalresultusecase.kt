package com.subafy.subafy.src.features.auction.domain.usecase

import com.subafy.subafy.src.features.auction.domain.entities.AuctionFinalResult
import com.subafy.subafy.src.features.auction.domain.repositories.AuctionRepository
import javax.inject.Inject

class GetFinalResultUseCase @Inject constructor(
    private val repository: AuctionRepository
) {
    suspend operator fun invoke(auctionId: String): Result<AuctionFinalResult> {
        return repository.getFinalResult(auctionId)
    }
}
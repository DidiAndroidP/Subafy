package com.subafy.subafy.src.features.auction.domain.usecase

import com.subafy.subafy.src.features.auction.domain.entities.AuctionCreated
import com.subafy.subafy.src.features.auction.domain.entities.CreateAuctionRequest
import com.subafy.subafy.src.features.auction.domain.repositories.AuctionRepository
import javax.inject.Inject

class CreateAuctionUseCase @Inject constructor(
    private val repository: AuctionRepository
) {
    suspend operator fun invoke(request: CreateAuctionRequest): Result<AuctionCreated> {
        return repository.createAuction(request)
    }
}
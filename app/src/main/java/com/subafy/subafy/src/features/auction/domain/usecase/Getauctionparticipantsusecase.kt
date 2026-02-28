package com.subafy.subafy.src.features.auction.domain.usecase

import com.subafy.subafy.src.features.auction.domain.entities.Participant
import com.subafy.subafy.src.features.auction.domain.repositories.AuctionRepository
import javax.inject.Inject

class GetAuctionParticipantsUseCase @Inject constructor(
    private val repository: AuctionRepository
) {
    suspend operator fun invoke(auctionId: String): Result<List<Participant>> {
        return repository.getAuctionParticipants(auctionId)
    }
}
package com.subafy.subafy.src.features.auction.domain.usecase

import com.subafy.subafy.src.features.auction.domain.entities.Participant
import com.subafy.subafy.src.features.auction.domain.repositories.AuctionRepository
import javax.inject.Inject

class GetParticipantsUseCase @Inject constructor(
    private val repository: AuctionRepository
) {
    suspend operator fun invoke(): Result<List<Participant>> {
        return repository.getParticipants()
    }
}
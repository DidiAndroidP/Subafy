package com.subafy.subafy.src.features.dashboard.domain.usecase

import com.subafy.subafy.src.features.dashboard.domain.entities.AuctionWsEvent
import com.subafy.subafy.src.features.dashboard.domain.repositories.AuctionRepositoryDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConnectToAuctionUseCase @Inject constructor(
    private val repository: AuctionRepositoryDetail
) {
    operator fun invoke(): Flow<AuctionWsEvent> {
        return repository.connect()
    }
}
package com.subafy.subafy.src.features.auth.domain.usecase

import com.subafy.subafy.src.features.auth.domain.entities.UserIdentity
import com.subafy.subafy.src.features.auth.domain.repositories.AuthRepository
import javax.inject.Inject

class JoinAuctionUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(identity: UserIdentity) {
        repository.connectToAuctionRoom()

        repository.joinAuction(identity)
    }
}
package com.subafy.subafy.src.features.auction.domain.repositories

import com.subafy.subafy.src.features.auction.domain.entities.AuctionCreated
import com.subafy.subafy.src.features.auction.domain.entities.CreateAuctionRequest
import com.subafy.subafy.src.features.auction.domain.entities.Participant

interface AuctionRepository {
    suspend fun createAuction(request: CreateAuctionRequest): Result<AuctionCreated>
    suspend fun getParticipants(): Result<List<Participant>>
}
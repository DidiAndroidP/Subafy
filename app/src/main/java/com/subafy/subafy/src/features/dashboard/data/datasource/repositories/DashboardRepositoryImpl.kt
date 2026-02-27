package com.subafy.subafy.src.features.dashboard.data.repositories

import com.subafy.subafy.src.features.dashboard.data.datasource.remote.api.DashboardHttpApi
import com.subafy.subafy.src.features.dashboard.data.datasource.remote.mappers.toDomain
import com.subafy.subafy.src.features.dashboard.domain.entities.ActiveAuction
import com.subafy.subafy.src.features.dashboard.domain.entities.Auction
import com.subafy.subafy.src.features.dashboard.domain.repositories.DashboardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val api: DashboardHttpApi,
    private val userPreferences: UserPreferences
) : DashboardRepository {

    override suspend fun getAuctions(): Result<List<Auction>> {
        return try {
            val response = api.getAuctions()
            val data = response.body()?.data

            if (response.isSuccessful && data != null) {

            if (response.isSuccessful && !data.isNullOrEmpty()) {
                Result.success(data.map { it.toDomain() })
            } else {
                Result.failure(Exception("Error fetching auctions"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getActiveAuction(): Result<ActiveAuction> {
        return try {
            val response = api.getActiveAuction()

            val data = response.body()?.data
            if (response.isSuccessful && data != null) {
                Result.success(data.toDomain())
            } else {
                Result.failure(Exception("No active auction"))
            val list = response.body()?.data
            if (response.isSuccessful && !list.isNullOrEmpty()) {
                Result.success(list.first().toDomain())
            } else {
                Result.failure(Exception("No active auction"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    override suspend fun getAuctionById(id: String): Result<Auction> {
        return try {
            val response = api.getAuctionById(id)
            val dto = response.body()?.data
            if (response.isSuccessful && dto != null) {
                val auction = Auction(
                    id              = dto.id,
                    productName     = dto.productName,
                    status          = dto.status,
                    startingPrice   = dto.startingPrice,
                    lotNumber       = dto.lotNumber,
                    productImageUrl = dto.productImageUrl,
                    durationSeconds = dto.durationSeconds,
                    createdAt       = dto.createdAt
                )
                Result.success(auction)
            } else {
                Result.failure(Exception("Auction not found: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getUserNickname(): Flow<String> = userPreferences.nickname

    override fun getUserAvatar(): Flow<String?> = userPreferences.avatarUrl
}
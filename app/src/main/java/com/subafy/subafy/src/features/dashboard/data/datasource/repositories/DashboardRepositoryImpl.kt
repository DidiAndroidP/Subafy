package com.subafy.subafy.src.features.dashboard.data.repositories

import com.subafy.subafy.src.features.dashboard.data.datasource.remote.api.DashboardHttpApi
import com.subafy.subafy.src.features.dashboard.data.datasource.remote.mappers.toDomain
import com.subafy.subafy.src.features.dashboard.domain.entities.ActiveAuction
import com.subafy.subafy.src.features.dashboard.domain.entities.Auction
import com.subafy.subafy.src.features.dashboard.domain.repositories.DashboardRepository
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val api: DashboardHttpApi
) : DashboardRepository {

    override suspend fun getAuctions(): Result<List<Auction>> {
        return try {
            val response = api.getAuctions()
            if (response.isSuccessful && response.body()?.success == true) {
                val auctions = response.body()!!.data.map { it.toDomain() }
                Result.success(auctions)
            } else {
                Result.failure(Exception("Error fetching auctions: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getActiveAuction(): Result<ActiveAuction> {
        return try {
            val response = api.getActiveAuction()
            if (response.isSuccessful && response.body()?.success == true) {
                val activeAuction = response.body()!!.data.toDomain()
                Result.success(activeAuction)
            } else {
                Result.failure(Exception("Error fetching active auction: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
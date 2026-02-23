package com.subafy.subafy.src.features.dashboard.data.datasource.remote.api

import com.subafy.subafy.src.features.dashboard.data.datasource.remote.dtos.ActiveAuctionDto
import com.subafy.subafy.src.features.dashboard.data.datasource.remote.dtos.AuctionDto
import com.subafy.subafy.src.features.dashboard.data.datasource.remote.dtos.BaseResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface DashboardHttpApi {
    @GET("/auctions")
    suspend fun getAuctions(): Response<BaseResponseDto<List<AuctionDto>>>

    @GET("/auctions/active")
    suspend fun getActiveAuction(): Response<BaseResponseDto<ActiveAuctionDto>>
}
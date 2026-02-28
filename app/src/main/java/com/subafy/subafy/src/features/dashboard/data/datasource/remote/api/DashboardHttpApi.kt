package com.subafy.subafy.src.features.dashboard.data.datasource.remote.api

import com.subafy.subafy.src.features.auth.data.datasource.remote.dto.AuctionDetailDto
import com.subafy.subafy.src.features.auth.data.datasource.remote.dto.BaseResponseDto
import com.subafy.subafy.src.features.auth.data.datasource.remote.dto.BidDto
import com.subafy.subafy.src.features.dashboard.data.datasource.remote.dtos.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DashboardHttpApi {

    @GET("/auctions")
    suspend fun getAuctions(): Response<BaseResponseDto<List<AuctionDto>>>

    @GET("/auctions/active")
    suspend fun getActiveAuction(): Response<BaseResponseDto<List<ActiveAuctionDto>>>

    @GET("/auctions/{id}")
    suspend fun getAuctionById(
        @Path("id") id: String
    ): Response<BaseResponseDto<AuctionDetailDto>>

    @GET("/auctions/{id}/bids")
    suspend fun getAuctionBids(@Path("id") auctionId: String): Response<BaseResponseDto<List<BidDto>>>

    // ← reutiliza el mismo endpoint, solo cambia el nombre para claridad
    @GET("/auctions/{id}")
    suspend fun getAuctionStatus(
        @Path("id") auctionId: String
    ): Response<BaseResponseDto<AuctionDetailDto>>
}
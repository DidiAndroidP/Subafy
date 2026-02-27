package com.subafy.subafy.src.features.auction.data.datasource.remote.api

import com.subafy.subafy.src.features.auth.data.datasource.remote.dto.BaseResponseDto
import com.subafy.subafy.src.features.auction.data.datasource.remote.dto.AuctionCreatedDataDto
import com.subafy.subafy.src.features.auction.data.datasource.remote.dto.CreateAuctionRequestDto
import com.subafy.subafy.src.features.auction.data.datasource.remote.dto.FinalResultDto
import com.subafy.subafy.src.features.auction.data.datasource.remote.dto.ParticipantDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface AuctionHttpApi {
    @POST("/auctions")
    suspend fun createAuctionJson(
        @Body request: CreateAuctionRequestDto
    ): Response<BaseResponseDto<AuctionCreatedDataDto>>

    @Multipart
    @POST("/auctions")
    suspend fun createAuctionMultipart(
        @Part("productName") productName: RequestBody,
        @Part("lotNumber") lotNumber: RequestBody,
        @Part("startingPrice") startingPrice: RequestBody,
        @Part("durationSeconds") durationSeconds: RequestBody,
        @Part productImage: MultipartBody.Part
    ): Response<BaseResponseDto<AuctionCreatedDataDto>>

    @GET("/participants")
    suspend fun getParticipants(): Response<BaseResponseDto<List<ParticipantDto>>>

    @GET("/auctions/{auctionId}/result")
    suspend fun getFinalResult(
        @Path("auctionId") auctionId: String
    ): Response<BaseResponseDto<FinalResultDto>>
}
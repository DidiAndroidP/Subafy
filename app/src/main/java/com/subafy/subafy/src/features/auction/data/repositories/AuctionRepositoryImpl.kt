package com.subafy.subafy.src.features.auction.data.repositories

import com.subafy.subafy.src.features.auction.data.datasource.remote.api.AuctionHttpApi
import com.subafy.subafy.src.features.auction.data.datasource.remote.mappers.toDomain
import com.subafy.subafy.src.features.auction.data.datasource.remote.mappers.toDto
import com.subafy.subafy.src.features.auction.domain.entities.AuctionCreated
import com.subafy.subafy.src.features.auction.domain.entities.AuctionFinalResult
import com.subafy.subafy.src.features.auction.domain.entities.CreateAuctionRequest
import com.subafy.subafy.src.features.auction.domain.entities.Participant
import com.subafy.subafy.src.features.auction.domain.repositories.AuctionRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class AuctionRepositoryImpl @Inject constructor(
    private val api: AuctionHttpApi
) : AuctionRepository {

    override suspend fun createAuction(request: CreateAuctionRequest): Result<AuctionCreated> {
        return try {
            val response = if (request.imageFile != null) {
                val nameBody = request.productName.toRequestBody("text/plain".toMediaTypeOrNull())
                val lotBody = request.lotNumber.toRequestBody("text/plain".toMediaTypeOrNull())
                val priceBody = request.startingPrice.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val durationBody = request.durationSeconds.toString().toRequestBody("text/plain".toMediaTypeOrNull())

                val fileBody = request.imageFile.asRequestBody("image/*".toMediaTypeOrNull())
                val imagePart = MultipartBody.Part.createFormData("productImage", request.imageFile.name, fileBody)

                api.createAuctionMultipart(nameBody, lotBody, priceBody, durationBody, imagePart)
            } else {
                api.createAuctionJson(request.toDto())
            }

            if (response.isSuccessful && response.body()?.success == true) {
                val data = response.body()?.data
                if (data != null) {
                    Result.success(data.toDomain())
                } else {
                    Result.failure(Exception("Respuesta exitosa pero sin datos del servidor"))
                }
            } else {
                Result.failure(Exception("Error al crear subasta: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getParticipants(): Result<List<Participant>> {
        return try {
            val response = api.getParticipants()

            if (response.isSuccessful && response.body()?.success == true) {
                val data = response.body()?.data
                if (data != null) {
                    val domainList = data.map { it.toDomain() }
                    Result.success(domainList)
                } else {
                    Result.success(emptyList())
                }
            } else {
                Result.failure(Exception("Error al obtener participantes: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getFinalResult(auctionId: String, currentUserId: String): Result<AuctionFinalResult> {
        return try {
            val response = api.getFinalResult(auctionId)

            if (response.isSuccessful && response.body()?.success == true) {
                val data = response.body()?.data
                if (data != null) {
                    Result.success(data.toDomain(currentUserId))
                } else {
                    Result.failure(Exception("Respuesta exitosa pero sin datos del servidor"))
                }
            } else {
                Result.failure(Exception("Error al obtener resultado final: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAuctionParticipants(auctionId: String): Result<List<Participant>> {
        return try {
            val response = api.getAuctionParticipants(auctionId)
            val data = response.body()?.data
            if (response.isSuccessful && data != null) {
                Result.success(data.map { it.toDomain() })
            } else {
                Result.success(emptyList())
            }
        } catch (e: Exception) {
            Result.success(emptyList())
        }
    }
}
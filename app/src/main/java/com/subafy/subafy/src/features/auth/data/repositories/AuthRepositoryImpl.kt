package com.subafy.subafy.src.features.auth.data.repositories
import com.subafy.subafy.src.features.auth.data.datasource.remote.api.AuthHttpApi
import com.subafy.subafy.src.features.auth.data.datasource.remote.api.AuthWsApi
import com.subafy.subafy.src.features.auth.data.mapper.toDomainUrl
import com.subafy.subafy.src.features.auth.data.mapper.toWsJoinPayloadDto
import com.subafy.subafy.src.features.auth.domain.entities.UserIdentity
import com.subafy.subafy.src.features.auth.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val httpApi: AuthHttpApi,
    private val wsApi: AuthWsApi
) : AuthRepository {

    override suspend fun uploadAvatar(userId: String, imageFile: File): Result<String> {
        return try {
            val requestFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("avatar", imageFile.name, requestFile)
            val userIdBody = userId.toRequestBody("text/plain".toMediaTypeOrNull())

            val response = httpApi.uploadAvatar(userIdBody, body)

            if (response.isSuccessful && response.body()?.success == true) {
                val avatarUrl = response.body()!!.data!!.toDomainUrl()
                Result.success(avatarUrl)
            } else {
                Result.failure(Exception("Error al subir el avatar: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun connectToAuctionRoom() {
        wsApi.connect()
    }

    override fun joinAuction(identity: UserIdentity) {
        val payloadDto = identity.toWsJoinPayloadDto()
        wsApi.sendJoinEvent(payloadDto)
    }

    override fun getAuctionStateFlow(): Flow<String> {
        return wsApi.observeAuctionState()
    }

    override fun disconnect() {
        wsApi.disconnect()
    }
}
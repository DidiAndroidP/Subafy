package com.subafy.subafy.src.features.auth.data.datasource.remote.api
import com.subafy.subafy.src.features.auth.data.datasource.remote.dto.AvatarUploadDto
import com.subafy.subafy.src.features.auth.data.datasource.remote.dto.BaseResponseDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AuthHttpApi {
    @Multipart
    @POST("/avatar/upload")
    suspend fun uploadAvatar(
        @Part("userId") userId: RequestBody,
        @Part avatar: MultipartBody.Part
    ): Response<BaseResponseDto<AvatarUploadDto>>
}

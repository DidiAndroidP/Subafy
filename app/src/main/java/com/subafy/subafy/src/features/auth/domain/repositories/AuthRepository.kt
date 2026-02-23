package com.subafy.subafy.src.features.auth.domain.repositories
import com.subafy.subafy.src.features.auth.domain.entities.UserIdentity
import java.io.File
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun uploadAvatar(userId: String, imageFile: File): Result<String>

    fun connectToAuctionRoom()
    fun joinAuction(identity: UserIdentity)
    fun getAuctionStateFlow(): Flow<String>
    fun disconnect()
}
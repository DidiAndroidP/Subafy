package com.subafy.subafy.src.core.utils

object AvatarGenerator {
    private const val BASE_DICEBEAR_URL = "https://api.dicebear.com/9.x/identicon/png?seed="

    fun generateDicebearUrl(seed: String): String {
        return "$BASE_DICEBEAR_URL$seed"
    }
}
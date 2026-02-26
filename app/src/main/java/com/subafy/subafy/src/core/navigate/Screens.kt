package com.subafy.subafy.src.core.navigate

sealed class Screens(val route: String) {
    object Identity : Screens("identity")

    object Profile : Screens("profile/{userId}") {
        fun createRoute(userId: String) = "profile/$userId"
    }

    object Dashboard : Screens("dashboard")

    object CreateAuction : Screens("create_auction")

    // ← auctionId, userId y nickname viajan en la ruta
    object AuctionLive : Screens("auction_live/{auctionId}/{userId}/{nickname}") {
        fun createRoute(auctionId: String, userId: String, nickname: String) =
            "auction_live/$auctionId/$userId/${nickname.encode()}"

        private fun String.encode() =
            java.net.URLEncoder.encode(this, "UTF-8")
    }

    object Participants : Screens("participants")
}
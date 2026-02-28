package com.subafy.subafy.src.core.navigate

sealed class Screens(val route: String) {
    object Identity : Screens("identity")

    object Profile : Screens("profile/{userId}") {
        fun createRoute(userId: String) = "profile/$userId"
    }

    object Dashboard : Screens("dashboard")

    object CreateAuction : Screens("create_auction")

    object AuctionLive : Screens("auction_live/{auctionId}/{userId}/{nickname}?avatarUrl={avatarUrl}") {
        fun createRoute(auctionId: String, userId: String, nickname: String, avatarUrl: String) =
            "auction_live/$auctionId/$userId/${nickname.encode()}?avatarUrl=${avatarUrl.encode()}"

        private fun String.encode() = java.net.URLEncoder.encode(this, "UTF-8")
    }

    object FinalResult : Screens("final_result/{auctionId}/{userId}") {
        fun createRoute(auctionId: String, userId: String) = "final_result/$auctionId/$userId"
    }

    object Participants : Screens("participants/{auctionId}") {
        fun createRoute(auctionId: String) = "participants/$auctionId"
    }
}
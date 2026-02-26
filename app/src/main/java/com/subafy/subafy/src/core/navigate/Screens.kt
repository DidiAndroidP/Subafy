package com.subafy.subafy.src.core.navigate

sealed class Screens(val route: String) {
    object Identity : Screens("identity_screen")
    object Profile : Screens("profile_screen/{userId}") {
        fun createRoute(userId: String) = "profile_screen/$userId"
    }
    object Dashboard : Screens("dashboard_screen")
    object CreateAuction : Screens("create_auction_screen")
    object AuctionLive : Screens("auction_live_screen/{auctionId}") {
        fun createRoute(auctionId: String) = "auction_live_screen/$auctionId"
    }
    object Participants : Screens("participants_screen")
}
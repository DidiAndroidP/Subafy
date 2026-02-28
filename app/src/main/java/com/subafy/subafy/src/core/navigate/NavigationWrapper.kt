package com.subafy.subafy.src.core.navigate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.subafy.subafy.src.features.auth.presentation.screens.IdentityScreen
import com.subafy.subafy.src.features.auth.presentation.screens.ProfileScreen
import com.subafy.subafy.src.features.auth.presentation.viewModel.AuthViewModel
import com.subafy.subafy.src.features.dashboard.presentation.screens.DashboardScreen
import com.subafy.subafy.src.features.auction.presentation.screens.CreateAuctionScreen
import com.subafy.subafy.src.features.dashboard.presentation.screens.AuctionScreen
import com.subafy.subafy.src.features.dashboard.presentation.screens.ParticipantsScreen
import com.subafy.subafy.src.features.auction.presentation.screens.FinalResultScreen

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Screens.Identity.route
    ) {
        composable(route = Screens.Identity.route) {
            val userId by authViewModel.userId.collectAsState()
            IdentityScreen(
                viewModel = authViewModel,
                onNavigateToAuction = {
                    navController.navigate(Screens.Profile.createRoute(userId))
                }
            )
        }

        composable(route = Screens.Profile.route) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: return@composable
            ProfileScreen(
                userId = userId,
                onNavigateToDashboard = { nickname, avatarUrl ->
                    authViewModel.updateProfile(nickname, avatarUrl)
                    navController.navigate(Screens.Dashboard.route) {
                        popUpTo(Screens.Identity.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Screens.Dashboard.route) {
            val userId    by authViewModel.userId.collectAsState()
            val nickname  by authViewModel.nickname.collectAsState()
            val avatarUrl by authViewModel.avatarUrl.collectAsState()

            DashboardScreen(
                nickname  = nickname,
                avatarUrl = avatarUrl,
                onNavigateToAuctionLive = { auctionId ->
                    navController.navigate(
                        Screens.AuctionLive.createRoute(auctionId, userId, nickname, avatarUrl ?: "")
                    )
                },
                onNavigateToCreateAuction = {
                    navController.navigate(Screens.CreateAuction.route)
                }
            )
        }

        composable(route = Screens.CreateAuction.route) {
            CreateAuctionScreen(
                onNavigateBack   = { navController.popBackStack() },
                onAuctionCreated = { navController.popBackStack() }
            )
        }

        composable(
            route = Screens.AuctionLive.route,
            arguments = listOf(
                navArgument("avatarUrl") {
                    type         = NavType.StringType
                    defaultValue = ""
                    nullable     = true
                }
            )
        ) { backStackEntry ->
            val auctionId = backStackEntry.arguments?.getString("auctionId") ?: return@composable
            val userId    = backStackEntry.arguments?.getString("userId") ?: ""
            val avatarUrl = backStackEntry.arguments?.getString("avatarUrl")
                ?.let { java.net.URLDecoder.decode(it, "UTF-8") }

            AuctionScreen(
                onNavigateToResult = {
                    navController.navigate(Screens.FinalResult.createRoute(auctionId, userId)) {
                        popUpTo(Screens.AuctionLive.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Screens.FinalResult.route) { backStackEntry ->
            val auctionId = backStackEntry.arguments?.getString("auctionId") ?: return@composable
            FinalResultScreen(
                auctionId = auctionId,
                onViewParticipants = {
                    navController.navigate(Screens.Participants.createRoute(auctionId))
                },
                onExit = {
                    navController.navigate(Screens.Dashboard.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Screens.Participants.route) { backStackEntry ->
            val auctionId = backStackEntry.arguments?.getString("auctionId") ?: return@composable
            ParticipantsScreen(
                auctionId      = auctionId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
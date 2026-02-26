package com.subafy.subafy.src.core.navigate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.subafy.subafy.src.features.auth.presentation.screens.IdentityScreen
import com.subafy.subafy.src.features.auth.presentation.screens.ProfileScreen
import com.subafy.subafy.src.features.auth.presentation.viewModel.AuthViewModel
import com.subafy.subafy.src.features.dashboard.presentation.screens.DashboardScreen
import com.subafy.subafy.src.features.auction.presentation.screens.CreateAuctionScreen
import com.subafy.subafy.src.features.dashboard.presentation.screens.AuctionScreen
import com.subafy.subafy.src.features.dashboard.presentation.screens.ParticipantsScreen

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    // AuthViewModel compartido para acceder a userId/nickname desde cualquier pantalla
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
                onNavigateToDashboard = {
                    navController.navigate(Screens.Dashboard.route) {
                        popUpTo(Screens.Identity.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Screens.Dashboard.route) {
            val userId   by authViewModel.userId.collectAsState()
            val nickname by authViewModel.nickname.collectAsState()

            DashboardScreen(
                onNavigateToAuctionLive = { auctionId ->
                    navController.navigate(
                        Screens.AuctionLive.createRoute(auctionId, userId, nickname)
                    )
                },
                onNavigateToCreateAuction = {
                    navController.navigate(Screens.CreateAuction.route)
                }
            )
        }

        composable(route = Screens.CreateAuction.route) {
            CreateAuctionScreen(
                onNavigateBack    = { navController.popBackStack() },
                onAuctionCreated  = { navController.popBackStack() }
            )
        }

        composable(route = Screens.AuctionLive.route) {
            AuctionScreen(
                onNavigateToResult = {
                    navController.navigate(Screens.Dashboard.route) {
                        popUpTo(Screens.Dashboard.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Screens.Participants.route) {
            ParticipantsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
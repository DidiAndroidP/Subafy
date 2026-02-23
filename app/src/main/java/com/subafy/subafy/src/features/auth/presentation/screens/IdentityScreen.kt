package com.subafy.subafy.src.features.auth.presentation.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.subafy.subafy.src.features.auth.presentation.components.*
import com.subafy.subafy.src.features.auth.presentation.viewModel.AuthViewModel

@Composable
fun IdentityScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel(),
    onNavigateToAuction: () -> Unit
) {
    val userId by viewModel.userId.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isJoined by viewModel.isJoined.collectAsState()
    val error by viewModel.error.collectAsState()

    var startAnimation by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    val alpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 800)
    )

    val offsetY by animateDpAsState(
        targetValue = if (startAnimation) 0.dp else 40.dp,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
    }

    LaunchedEffect(isJoined) {
        if (isJoined) {
            onNavigateToAuction()
        }
    }

    LaunchedEffect(error) {
        error?.let {
            snackbarHostState.showSnackbar(message = it)
        }
    }

    Scaffold(
        modifier = modifier,
        containerColor = Color.White,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .padding(top = 48.dp, bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            AuthHeader(
                modifier = Modifier.graphicsLayer {
                    this.alpha = alpha
                    this.translationY = offsetY.toPx()
                }
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.graphicsLayer {
                    this.alpha = alpha
                    this.translationY = (offsetY * 1.5f).toPx()
                }
            ) {
                IdentityCard(userId = userId)
                Spacer(modifier = Modifier.height(32.dp))
                PrivacyInfo()
            }

            EnterActionGroup(
                onEnterClick = { viewModel.enterAuction() },
                isLoading = isLoading,
                modifier = Modifier.graphicsLayer {
                    this.alpha = alpha
                    this.translationY = (offsetY * 2f).toPx()
                }
            )
        }
    }
}
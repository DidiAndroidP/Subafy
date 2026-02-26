package com.subafy.subafy.src.features.auction.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.subafy.subafy.src.features.auction.presentation.components.*
import com.subafy.subafy.src.features.auction.presentation.viewModel.FinalResultViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinalResultScreen(
    auctionId: String,
    viewModel: FinalResultViewModel = hiltViewModel(),
    onViewParticipants: () -> Unit,
    onExit: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(auctionId) {
        viewModel.loadFinalResult(auctionId)
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Resultado Final",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp,
                        color = Color(0xFF111827)
                    )
                },
                actions = {
                    IconButton(onClick = onExit) {
                        Text(text = "↗", fontSize = 20.sp, color = Color(0xFF6B7280))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { paddingValues ->

        when {
            state.isLoading -> {
                FinalResultLoadingState()
            }

            state.error != null -> {
                FinalResultErrorState(
                    message = state.error!!,
                    onRetry = { viewModel.loadFinalResult(auctionId) }
                )
            }

            state.finalResult != null -> {
                val result = state.finalResult!!

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // ── Banner de subasta terminada ──────────────────────────
                    AuctionFinishedBanner()

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Spacer(modifier = Modifier.height(20.dp))

                        // ── Felicitaciones si el usuario actual ganó ─────────
                        if (result.isCurrentUserWinner) {
                            WinnerCongratsBanner()
                            Spacer(modifier = Modifier.height(24.dp))
                        }

                        // ── Avatar + nombre del ganador ──────────────────────
                        WinnerAvatar(
                            avatarUrl = result.winnerAvatarUrl,
                            nickname = result.winnerNickname,
                            rank = result.winnerRank
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // ── Tarjeta del producto ─────────────────────────────
                        FinalProductCard(
                            productName = result.productName,
                            finalPrice = result.finalPrice,
                            productImageUrl = result.participants
                                .firstOrNull { it.nickname == result.winnerNickname }
                                ?.avatarUrl  // o el campo de imagen del producto si lo tienes
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        // ── Botones ──────────────────────────────────────────
                        ViewParticipantsButton(onClick = onViewParticipants)

                        Spacer(modifier = Modifier.height(12.dp))

                        ExitAuctionButton(onClick = onExit)

                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
            }
        }
    }
}
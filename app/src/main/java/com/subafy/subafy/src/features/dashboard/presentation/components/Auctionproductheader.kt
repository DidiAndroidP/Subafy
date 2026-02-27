package com.subafy.subafy.src.features.dashboard.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.delay

@Composable
fun AuctionProductHeader(
    productName:     String,
    productSubtitle: String,
    productImageUrl: String?,
    timeRemaining:   Int,
    isConnected:     Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
    ) {
        AsyncImage(
            model             = productImageUrl,
            contentDescription = productName,
            contentScale      = ContentScale.Crop,
            modifier          = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.75f)
                        ),
                        startY = 80f
                    )
                )
        )

        // ── Chip CONECTADO arriba a la derecha ─────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = if (isConnected) Color.White else Color(0xFFEF4444)
            ) {
                Text(
                    text  = if (isConnected) "CONECTADO" else "DESCONECTADO",
                    color = if (isConnected) Color(0xFF1C64F2) else Color.White,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }

        // ── Timer + nombre del producto abajo ──────────────────
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Timer AuctionTimer(timeRemaining = timeRemaining)

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text       = productName,
                color      = Color.White,
                fontSize   = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text     = productSubtitle,
                color    = Color.White.copy(alpha = 0.85f),
                fontSize = 13.sp
            )
        }
    }
}

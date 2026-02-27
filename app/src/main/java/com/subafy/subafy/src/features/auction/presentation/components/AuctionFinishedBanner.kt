package com.subafy.subafy.src.features.auction.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

// ── Colores del sistema ──────────────────────────────────────────────────────
val PrimaryBlue = Color(0xFF3B82F6)
val GoldColor   = Color(0xFFF59E0B)
val GreenBadge  = Color(0xFF10B981)
val DarkText    = Color(0xFF111827)
val SubText     = Color(0xFF6B7280)
val SurfaceCard = Color(0xFFF9FAFB)
val BorderColor = Color(0xFFE5E7EB)

// ── Trofeo + banner "¡Subasta Terminada!" ───────────────────────────────────
@Composable
fun AuctionFinishedBanner() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFFFFFBEB), Color(0xFFFEF3C7))
                )
            )
            .padding(vertical = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "🏆", fontSize = 56.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "¡Subasta Terminada!",
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            color = DarkText
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "La puja ha finalizado oficialmente.",
            fontSize = 13.sp,
            color = SubText
        )
    }
}

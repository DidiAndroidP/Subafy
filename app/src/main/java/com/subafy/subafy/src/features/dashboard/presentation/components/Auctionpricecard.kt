package com.subafy.subafy.src.features.auction.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AuctionPriceCard(
    currentPrice:   Double,
    leaderNickname: String?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // ── Precio actual ──────────────────────────────────────
        Surface(
            modifier      = Modifier.weight(1f),
            shape         = RoundedCornerShape(16.dp),
            color         = Color(0xFFEFF6FF),
            tonalElevation = 0.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector        = Icons.Default.TrendingUp,
                        contentDescription = null,
                        tint               = Color(0xFF1C64F2),
                        modifier           = Modifier.size(14.dp)
                    )
                    Text(
                        text      = "PRECIO ACTUAL",
                        fontSize  = 10.sp,
                        color     = Color(0xFF1C64F2),
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text       = "$${"%,.0f".format(currentPrice)}",
                    fontSize   = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color      = Color(0xFF1C64F2)
                )
            }
        }

        // ── Líder actual ───────────────────────────────────────
        Surface(
            modifier      = Modifier.weight(1f),
            shape         = RoundedCornerShape(16.dp),
            color         = Color(0xFFFFFBEB),
            tonalElevation = 0.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector        = Icons.Default.EmojiEvents,
                        contentDescription = null,
                        tint               = Color(0xFFF59E0B),
                        modifier           = Modifier.size(14.dp)
                    )
                    Text(
                        text       = "LÍDER ACTUAL",
                        fontSize   = 10.sp,
                        color      = Color(0xFFF59E0B),
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text       = leaderNickname ?: "Sin líder",
                    fontSize   = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color      = Color(0xFF1F2937),
                    maxLines   = 1
                )
            }
        }
    }
}
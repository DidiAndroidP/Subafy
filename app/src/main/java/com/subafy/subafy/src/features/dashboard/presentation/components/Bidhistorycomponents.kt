package com.subafy.subafy.src.features.dashboard.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import coil.compose.AsyncImage

// ── Modelo de puja para la UI ──────────────────────────────────
data class BidUiModel(
    val userId:       String,
    val nickname:     String,
    val avatarUrl:    String?,
    val amount:       Double,
    val increment:    Double,
    val timeAgo:      String,    // "Hace 2s", "Hace 1m", etc.
    val isLeader:     Boolean
)

// ── Header del historial ───────────────────────────────────────
@Composable
fun BidHistoryHeader(totalBids: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment     = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector        = Icons.Default.History,
                contentDescription = null,
                tint               = Color(0xFF6B7280),
                modifier           = Modifier.size(18.dp)
            )
            Text(
                text       = "Historial de Actividad",
                fontSize   = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color      = Color(0xFF1F2937)
            )
        }
        Text(
            text     = "$totalBids pujas hoy",
            fontSize = 12.sp,
            color    = Color(0xFF6B7280)
        )
    }

    HorizontalDivider(
        modifier  = Modifier.padding(horizontal = 16.dp),
        thickness = 0.5.dp,
        color     = Color(0xFFE5E7EB)
    )
}

// ── Item de puja ───────────────────────────────────────────────
@Composable
fun BidHistoryItem(bid: BidUiModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment     = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Avatar
        Box(modifier = Modifier.size(44.dp)) {
            AsyncImage(
                model              = bid.avatarUrl,
                contentDescription = bid.nickname,
                contentScale       = ContentScale.Crop,
                modifier           = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(Color(0xFFE5E7EB))
            )
        }

        // Nickname + tiempo
        Column(modifier = Modifier.weight(1f)) {
            Row(
                verticalAlignment     = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text       = bid.nickname,
                    fontSize   = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color      = Color(0xFF1F2937)
                )
                // Badge LÍDER
                if (bid.isLeader) {
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = Color(0xFF1C64F2)
                    ) {
                        Text(
                            text     = "Líder",
                            fontSize = 10.sp,
                            color    = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }
            Text(
                text = formatDate(bid.timeAgo),
                fontSize = 12.sp,
                color = Color(0xFF9CA3AF)
            )
        }

        // Monto + incremento
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text       = "$${"%,.0f".format(bid.amount)}",
                fontSize   = 15.sp,
                fontWeight = FontWeight.Bold,
                color      = Color(0xFF1F2937)
            )
            Text(
                text     = "+$${"%,.0f".format(bid.increment)}",
                fontSize = 12.sp,
                color    = Color(0xFF16A34A)
            )
        }
    }

    HorizontalDivider(
        modifier  = Modifier.padding(horizontal = 16.dp),
        thickness = 0.5.dp,
        color     = Color(0xFFF3F4F6)
    )
}

fun formatDate(isoDate: String): String {
    return try {
        val instant = Instant.parse(isoDate)

        val formatter = DateTimeFormatter
            .ofPattern("dd MMM yyyy • HH:mm")
            .withZone(ZoneId.systemDefault())

        formatter.format(instant)

    } catch (e: Exception) {
        isoDate // si no es una fecha válida, lo devuelve tal cual
    }
}
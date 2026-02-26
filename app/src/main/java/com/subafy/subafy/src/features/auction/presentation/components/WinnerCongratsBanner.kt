package com.subafy.subafy.src.features.auction.presentation.components


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun WinnerCongratsBanner() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF0FDF4), RoundedCornerShape(12.dp))
            .border(1.dp, Color(0xFFBBF7D0), RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = GreenBadge,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = "¡Felicidades, tú ganaste!",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = Color(0xFF065F46)
            )
            Text(
                text = "Has asegurado este artículo\ncon la puja más alta.",
                fontSize = 12.sp,
                color = Color(0xFF047857),
                lineHeight = 17.sp
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(text = "🎉", fontSize = 22.sp)
    }
}
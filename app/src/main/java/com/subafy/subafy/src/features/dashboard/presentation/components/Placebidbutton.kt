package com.subafy.subafy.src.features.dashboard.presentation.components


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PlaceBidButton(
    enabled: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color    = Color.White,
        shadowElevation = 12.dp
    ) {
        Button(
            onClick  = onClick,
            enabled  = enabled,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .height(54.dp),
            shape  = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor         = Color(0xFF1C64F2),
                disabledContainerColor = Color(0xFF93C5FD),
                contentColor           = Color.White
            )
        ) {
            Icon(
                imageVector        = Icons.Default.TrendingUp,
                contentDescription = null,
                modifier           = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text       = "Pujar Ahora",
                fontSize   = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
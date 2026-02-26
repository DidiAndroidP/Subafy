package com.subafy.subafy.src.features.dashboard.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BidConfirmationModal(
    currentPrice: Double,
    minBid:       Double,
    bidIncrement: Double,
    error:        String?,
    onConfirm:    (Double) -> Unit,
    onDismiss:    () -> Unit
) {
    var bidAmount by remember { mutableStateOf(minBid.toInt().toString()) }
    val parsedAmount = bidAmount.toDoubleOrNull() ?: 0.0
    val isValid = parsedAmount >= minBid

    ModalBottomSheet(
        onDismissRequest  = onDismiss,
        containerColor    = Color.White,
        shape             = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        sheetState        = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text       = "Ingresa tu Puja",
                fontSize   = 20.sp,
                fontWeight = FontWeight.Bold,
                color      = Color(0xFF111827)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ── Campo de monto ─────────────────────────────────
            OutlinedTextField(
                value         = bidAmount,
                onValueChange = { bidAmount = it.filter { c -> c.isDigit() } },
                modifier      = Modifier.fillMaxWidth(),
                shape         = RoundedCornerShape(14.dp),
                leadingIcon   = {
                    Text(
                        text      = "$",
                        fontSize  = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color     = Color(0xFF6B7280),
                        modifier  = Modifier.padding(start = 16.dp)
                    )
                },
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontSize   = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color      = Color(0xFF111827),
                    textAlign  = TextAlign.End
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor   = Color(0xFF1C64F2),
                    unfocusedBorderColor = Color(0xFFE5E7EB),
                    focusedContainerColor   = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Puja mínima
            Text(
                text     = "La puja mínima es $${"%,.0f".format(minBid)}",
                fontSize = 13.sp,
                color    = Color(0xFF6B7280)
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Incremento recomendado
            Surface(
                shape = RoundedCornerShape(6.dp),
                color = Color(0xFFEFF6FF)
            ) {
                Text(
                    text     = "INCREMENTO RECOMENDADO: +$${"%,.0f".format(bidIncrement)}",
                    fontSize = 11.sp,
                    color    = Color(0xFF1C64F2),
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }

            // Error si la puja fue rechazada
            error?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text     = it,
                    fontSize = 12.sp,
                    color    = Color(0xFFEF4444),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── Botones ────────────────────────────────────────
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Cancelar
                OutlinedButton(
                    onClick   = onDismiss,
                    modifier  = Modifier.weight(1f).height(50.dp),
                    shape     = RoundedCornerShape(12.dp),
                    colors    = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFF374151)
                    )
                ) {
                    Text(text = "Cancelar", fontWeight = FontWeight.SemiBold)
                }

                // Confirmar
                Button(
                    onClick  = { if (isValid) onConfirm(parsedAmount) },
                    enabled  = isValid,
                    modifier = Modifier.weight(1f).height(50.dp),
                    shape    = RoundedCornerShape(12.dp),
                    colors   = ButtonDefaults.buttonColors(
                        containerColor         = Color(0xFF1C64F2),
                        disabledContainerColor = Color(0xFF93C5FD),
                        contentColor           = Color.White
                    )
                ) {
                    Text(text = "Confirmar Puja", fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text      = "Al confirmar, te comprometes legalmente a adquirir el lote si resultas ganador al finalizar el tiempo.",
                fontSize  = 11.sp,
                color     = Color(0xFF9CA3AF),
                textAlign = TextAlign.Center
            )
        }
    }
}
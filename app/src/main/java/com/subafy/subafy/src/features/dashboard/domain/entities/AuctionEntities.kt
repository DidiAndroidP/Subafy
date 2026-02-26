package com.subafy.subafy.src.features.dashboard.domain.entities

data class Auction(
    val id:              String,
    val productName:     String,
    val status:          String,   // "waiting" | "active" | "closed"
    val startingPrice:   String,
    val lotNumber:       String,
    val productImageUrl: String?,  // ← nuevo
    val durationSeconds: Int,      // ← nuevo
    val createdAt:       String    // ← nuevo
)

// ── Subasta activa (GET /auctions/active) ──────────────────────
data class ActiveAuction(
    val id:              String,
    val productName:     String,
    val status:          String,
    val startingPrice:   String,
    val durationSeconds: Int,
    val productImageUrl: String?,  // ← nuevo
    val lotNumber:       String,   // ← nuevo
    val createdAt: String
)
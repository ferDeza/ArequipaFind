package com.gamecodeschool.arequipafind.domain.models

data class Proposer (
    val id: String = "",
    val jobId: String = "",
    val proposerId: String = "", // worker who proposed
    val price: Double = 0.0,
    val message: String? = null, // optional note
    val status: String = "open", // open | accepted | rejected
    val createdAt: Long = System.currentTimeMillis()
)

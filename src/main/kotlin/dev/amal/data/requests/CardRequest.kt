package dev.amal.data.requests

import kotlinx.serialization.Serializable

@Serializable
data class CardRequest(
    val title: String,
    val cardHolderName: String,
    val cardNumber: String,
    val expirationDate: String,
    val CVV: String,
    val cardPin: String,
    val ZIP: String
)
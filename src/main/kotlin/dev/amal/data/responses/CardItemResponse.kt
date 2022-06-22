package dev.amal.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class CardItemResponse(
    val id: String,
    val userId: String,
    val title: String,
    val cardHolderName: String,
    val cardNumber: String,
    val expirationDate: String,
    val cvv: String,
    val cardPin: String,
    val zip: String
)

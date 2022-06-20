package dev.amal.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class PasswordItemResponse(
    val id: String,
    val userId: String,
    val title: String,
    val email: String,
    val password: String,
    val website: String
)
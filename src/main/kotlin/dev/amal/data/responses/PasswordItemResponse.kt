package dev.amal.data.responses

data class PasswordItemResponse(
    val id: String,
    val userId: String,
    val title: String,
    val email: String,
    val password: String,
    val website: String
)
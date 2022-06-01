package dev.amal.data.requests

import kotlinx.serialization.Serializable

@Serializable
data class AddPasswordRequest(
    val title: String,
    val email: String,
    val password: String,
    val website: String
)
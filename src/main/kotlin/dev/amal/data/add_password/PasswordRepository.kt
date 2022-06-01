package dev.amal.data.add_password

import dev.amal.data.models.PasswordItem
import dev.amal.data.responses.PasswordItemResponse

interface PasswordRepository {
    suspend fun addPassword(passwordItem: PasswordItem): Boolean
    suspend fun getPasswords(userId: String): List<PasswordItemResponse>
}
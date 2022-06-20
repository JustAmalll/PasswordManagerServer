package dev.amal.data.add_password

import dev.amal.data.models.PasswordItem
import dev.amal.data.responses.PasswordItemResponse
import dev.amal.utils.Constants

interface PasswordRepository {
    suspend fun addPassword(passwordItem: PasswordItem): Boolean
    suspend fun getPasswords(
        userId: String,
        page: Int = 0,
        pageSize: Int = Constants.DEFAULT_PAGE_SIZE
    ): List<PasswordItemResponse>

    suspend fun getPasswordDetails(passwordId: String, userId: String,): PasswordItemResponse?

    suspend fun searchForPasswords(query: String): List<PasswordItem>
}
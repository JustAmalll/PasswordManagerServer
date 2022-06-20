package dev.amal.data.add_password

import dev.amal.data.models.PasswordItem
import dev.amal.data.responses.PasswordItemResponse
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.or
import org.litote.kmongo.regex

class PasswordRepositoryImpl(
    db: CoroutineDatabase
) : PasswordRepository {

    private val passwords = db.getCollection<PasswordItem>()

    override suspend fun addPassword(passwordItem: PasswordItem): Boolean =
        passwords.insertOne(passwordItem).wasAcknowledged()

    override suspend fun getPasswords(
        userId: String, page: Int, pageSize: Int
    ): List<PasswordItemResponse> =
        passwords.find(PasswordItem::userId eq userId)
            .skip(page * pageSize)
            .limit(pageSize)
            .toList()
            .map { passwordItem ->
                PasswordItemResponse(
                    id = passwordItem.id,
                    userId = userId,
                    title = passwordItem.title,
                    email = passwordItem.email,
                    password = passwordItem.password,
                    website = passwordItem.website
                )
            }

    override suspend fun getPasswordDetails(
        passwordId: String, userId: String,
    ): PasswordItemResponse? {
        val password = passwords.findOneById(passwordId) ?: return null
        return PasswordItemResponse(
            id = password.id,
            userId = userId,
            title = password.title,
            email = password.email,
            password = password.password,
            website = password.website
        )
    }

    override suspend fun searchForPasswords(
        query: String
    ): List<PasswordItem> = passwords.find(
        or(
            PasswordItem::title regex Regex("(?i).*$query.*"),
            PasswordItem::email eq query,
            PasswordItem::website eq query
        )
    ).toList()
}
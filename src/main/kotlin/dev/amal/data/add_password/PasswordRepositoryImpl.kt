package dev.amal.data.add_password

import dev.amal.data.models.PasswordItem
import dev.amal.data.responses.PasswordItemResponse
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

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
                    title = passwordItem.title,
                    email = passwordItem.email,
                    password = passwordItem.password,
                    website = passwordItem.website
                )
            }

    override suspend fun getPasswordDetails(
        passwordId: String
    ): PasswordItemResponse? {
        val password = passwords.findOneById(passwordId) ?: return null
        return PasswordItemResponse(
            id = password.id,
            title = password.title,
            email = password.email,
            password = password.password,
            website = password.website
        )
    }
}
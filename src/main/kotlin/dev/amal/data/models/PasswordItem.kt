package dev.amal.data.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class PasswordItem(
    @BsonId val id: String = ObjectId().toString(),
    val userId: String,
    val title: String,
    val email: String,
    val password: String,
    val website: String,
)
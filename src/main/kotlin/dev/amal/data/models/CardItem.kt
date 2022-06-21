package dev.amal.data.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class CardItem(
    @BsonId val id: String = ObjectId().toString(),
    val title: String,
    val cardHolderName: String,
    val cardNumber: String,
    val expirationDate: String,
    val CVV: String,
    val cardPin: String,
    val ZIP: String
)

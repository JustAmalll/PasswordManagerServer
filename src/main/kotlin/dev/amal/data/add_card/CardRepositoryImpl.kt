package dev.amal.data.add_card

import dev.amal.data.models.CardItem
import org.litote.kmongo.coroutine.CoroutineDatabase

class CardRepositoryImpl(
    db: CoroutineDatabase
): CardRepository {

    private val cards = db.getCollection<CardItem>()

    override suspend fun addCard(cardItem: CardItem): Boolean =
        cards.insertOne(cardItem).wasAcknowledged()

}
package dev.amal.data.add_card

import dev.amal.data.models.CardItem
import dev.amal.data.responses.CardItemResponse
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class CardRepositoryImpl(
    db: CoroutineDatabase
) : CardRepository {

    private val cards = db.getCollection<CardItem>()

    override suspend fun addCard(cardItem: CardItem): Boolean =
        cards.insertOne(cardItem).wasAcknowledged()

    override suspend fun getCards(
        userId: String, page: Int, pageSize: Int
    ): List<CardItemResponse> =
        cards.find(CardItem::userId eq userId)
            .skip(page * pageSize)
            .limit(pageSize)
            .toList()
            .map { cardItem ->
                CardItemResponse(
                    id = cardItem.id,
                    userId = userId,
                    title = cardItem.title,
                    cardHolderName = cardItem.cardHolderName,
                    cardNumber = cardItem.cardNumber,
                    expirationDate = cardItem.expirationDate,
                    cvv = cardItem.cvv,
                    cardPin = cardItem.cardPin,
                    zip = cardItem.zip
                )
            }

    override suspend fun getCardDetails(
        cardId: String, userId: String
    ): CardItemResponse? {
        val cardItem = cards.findOneById(cardId) ?: return null
        return CardItemResponse(
            id = cardItem.id,
            userId = userId,
            title = cardItem.title,
            cardHolderName = cardItem.cardHolderName,
            cardNumber = cardItem.cardNumber,
            expirationDate = cardItem.expirationDate,
            cvv = cardItem.cvv,
            cardPin = cardItem.cardPin,
            zip = cardItem.zip
        )
    }
}
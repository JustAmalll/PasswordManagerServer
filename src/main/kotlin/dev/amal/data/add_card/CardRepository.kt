package dev.amal.data.add_card

import dev.amal.data.models.CardItem
import dev.amal.data.responses.CardItemResponse
import dev.amal.utils.Constants

interface CardRepository {
    suspend fun addCard(cardItem: CardItem): Boolean

    suspend fun getCards(
        userId: String,
        page: Int = 0,
        pageSize: Int = Constants.DEFAULT_PAGE_SIZE
    ): List<CardItemResponse>

    suspend fun getCardDetails(cardId: String, userId: String,): CardItemResponse?
}
package dev.amal.data.add_card

import dev.amal.data.models.CardItem

interface CardRepository {
    suspend fun addCard(cardItem: CardItem): Boolean
}
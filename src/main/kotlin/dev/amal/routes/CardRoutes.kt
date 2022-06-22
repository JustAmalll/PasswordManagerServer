package dev.amal.routes

import dev.amal.data.add_card.CardRepository
import dev.amal.data.models.CardItem
import dev.amal.data.requests.CardRequest
import dev.amal.data.responses.BasicApiResponse
import dev.amal.utils.Constants
import dev.amal.utils.QueryParams
import dev.amal.utils.userId
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.addCard(
    cardRepository: CardRepository
) {
    authenticate {
        post("/add/card") {
            val request = call.receiveOrNull<CardRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val cardItem = CardItem(
                userId = call.userId,
                title = request.title,
                cardHolderName = request.cardHolderName,
                cardNumber = request.cardNumber,
                expirationDate = request.expirationDate,
                cvv = request.cvv,
                cardPin = request.cardPin,
                zip = request.zip
            )

            val wasAcknowledged = cardRepository.addCard(cardItem)
            if (!wasAcknowledged) {
                call.respond(HttpStatusCode.Conflict)
                return@post
            }

            call.respond(
                status = HttpStatusCode.OK,
                message = BasicApiResponse<Unit>(successful = true)
            )
        }
    }
}

fun Route.getCards(
    cardRepository: CardRepository
) {
    authenticate {
        get("/user/cards") {
            val page = call.parameters[QueryParams.PARAM_PAGE]?.toIntOrNull() ?: 0
            val pageSize = call.parameters[QueryParams.PARAM_PAGE_SIZE]?.toIntOrNull()
                ?: Constants.DEFAULT_PAGE_SIZE

            val cards = cardRepository.getCards(
                userId = call.userId,
                page = page,
                pageSize = pageSize
            )
            call.respond(HttpStatusCode.OK, cards)
            println(cards)
        }
    }
}

fun Route.getCardDetails(cardRepository: CardRepository) {
    authenticate {
        get("/card/details") {
            val passwordId = call.parameters["cardId"] ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }
            val password = cardRepository.getCardDetails(
                passwordId, call.userId
            ) ?: kotlin.run {
                call.respond(HttpStatusCode.NotFound)
                return@get
            }
            call.respond(
                HttpStatusCode.OK,
                BasicApiResponse(successful = true, data = password)
            )
        }
    }
}
package dev.amal.routes

import dev.amal.data.add_card.CardRepository
import dev.amal.data.add_password.PasswordRepository
import dev.amal.data.models.CardItem
import dev.amal.data.models.PasswordItem
import dev.amal.data.requests.AddPasswordRequest
import dev.amal.data.requests.CardRequest
import dev.amal.data.responses.BasicApiResponse
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

            val passwordItem = CardItem(
                title = request.title,
                cardHolderName = request.cardHolderName,
                cardNumber = request.cardNumber,
                expirationDate = request.expirationDate,
                CVV = request.CVV,
                cardPin = request.cardPin,
                ZIP = request.ZIP
            )

            val wasAcknowledged = cardRepository.addCard(passwordItem)
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
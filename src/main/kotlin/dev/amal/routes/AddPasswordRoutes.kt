package dev.amal.routes

import dev.amal.data.add_password.PasswordRepository
import dev.amal.data.models.PasswordItem
import dev.amal.data.requests.AddPasswordRequest
import dev.amal.data.responses.BasicApiResponse
import dev.amal.utils.userId
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.addPassword(
    passwordDataSource: PasswordRepository
) {
    authenticate {
        post("/api/add/password") {
            val request = call.receiveOrNull<AddPasswordRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val passwordItem = PasswordItem(
                userId = call.userId,
                title = request.title,
                email = request.email,
                password = request.password,
                website = request.website
            )

            val wasAcknowledged = passwordDataSource.addPassword(passwordItem)
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

fun Route.getPasswords(
    passwordDataSource: PasswordRepository
) {
    authenticate {
        get("api/user/passwords") {
            val passwords = passwordDataSource.getPasswords(call.userId)
            call.respond(HttpStatusCode.OK, passwords)
        }
    }
}
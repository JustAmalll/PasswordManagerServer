package dev.amal.plugins

import dev.amal.data.add_card.CardRepository
import dev.amal.data.add_password.PasswordRepository
import dev.amal.data.user.UserDataSource
import dev.amal.routes.*
import dev.amal.security.hashing.HashingService
import dev.amal.security.token.TokenConfig
import dev.amal.security.token.TokenService
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureRouting(
    userDataSource: UserDataSource,
    hashingService: HashingService,
    tokenService: TokenService,
    tokenConfig: TokenConfig,
    passwordRepository: PasswordRepository,
    cardRepository: CardRepository
) {
    routing {
        signIn(userDataSource, hashingService, tokenService, tokenConfig)
        signUp(hashingService, userDataSource)
        authenticate()
        getSecretInfo()

        addPassword(passwordRepository)
        getPasswords(passwordRepository)
        getPasswordDetails(passwordRepository)
        searchPassword(passwordRepository)
        root()

        addCard(cardRepository)
        getCards(cardRepository)
        getCardDetails(cardRepository)
    }
}

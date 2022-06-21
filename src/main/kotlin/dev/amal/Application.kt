package dev.amal

import dev.amal.data.add_card.CardRepositoryImpl
import dev.amal.data.add_password.PasswordRepositoryImpl
import dev.amal.data.user.MongoUserDataSource
import dev.amal.plugins.configureMonitoring
import dev.amal.plugins.configureRouting
import dev.amal.plugins.configureSecurity
import dev.amal.plugins.configureSerialization
import dev.amal.security.hashing.SHA256HashingService
import dev.amal.security.token.JwtTokenService
import dev.amal.security.token.TokenConfig
import io.ktor.server.application.*
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    val mongoPw = System.getenv("MONGO_PW")
    val dbName = "password-manager"
    val db = KMongo.createClient(
        connectionString = "mongodb+srv://JustAmalll:$mongoPw@cluster0.lhqkk30.mongodb.net/$dbName?retryWrites=true&w=majority"
    ).coroutine.getDatabase(dbName)

    val userDataSource = MongoUserDataSource(db)
    val tokenService = JwtTokenService()
    val tokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        expiresIn = 365L * 1000L * 60L * 60L * 24L,
        secret = System.getenv("JWT_SECRET")
    )
    val hashingService = SHA256HashingService()

    val passwordRepository = PasswordRepositoryImpl(db)
    val cardRepository = CardRepositoryImpl(db)

    configureRouting(
        userDataSource,
        hashingService,
        tokenService,
        tokenConfig,
        passwordRepository,
        cardRepository
    )
    configureSerialization()
    configureMonitoring()
    configureSecurity(tokenConfig)
}

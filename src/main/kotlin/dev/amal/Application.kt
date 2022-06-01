package dev.amal

import dev.amal.data.add_password.PasswordRepository
import dev.amal.data.add_password.PasswordRepositoryImpl
import dev.amal.data.user.MongoUserDataSource
import io.ktor.server.application.*
import dev.amal.plugins.*
import dev.amal.security.hashing.SHA256HashingService
import dev.amal.security.token.JwtTokenService
import dev.amal.security.token.TokenConfig
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

    configureRouting(userDataSource, hashingService, tokenService, tokenConfig, passwordRepository)
    configureSerialization()
    configureMonitoring()
    configureSecurity(tokenConfig)
}

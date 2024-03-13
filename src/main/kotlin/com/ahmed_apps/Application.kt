package com.ahmed_apps

import com.ahmed_apps.data.user.model.dataSource.MongoUserDataSource
import com.ahmed_apps.plugins.configureMonitoring
import com.ahmed_apps.plugins.configureRouting
import com.ahmed_apps.plugins.configureSecurity
import com.ahmed_apps.plugins.configureSerialization
import com.ahmed_apps.security.hash.service.SHA256HashService
import com.ahmed_apps.security.jwt.model.TokenConfig
import com.ahmed_apps.security.jwt.service.JWTTokenService
import com.mongodb.kotlin.client.coroutine.MongoClient
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    val mongoPassword = System.getenv("MONGO_PASS")
    val databaseName = "watchy-database"

    val connectionString =
        "mongodb+srv://watch-course-user:$mongoPassword@cluster-watchy-course.nyitfg8.mongodb.net/$databaseName?retryWrites=true&w=majority"

    val mongoClient = MongoClient.create(connectionString)
    val database = mongoClient.getDatabase(databaseName)

    val userDataSource = MongoUserDataSource(database)

    val tokenService = JWTTokenService()
    val tokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        expireDate = 30L * 1000L * 60L * 60L * 24L,
        secret = System.getenv("JWT_SECRET")
    )
    val hashingService = SHA256HashService()


    configureSecurity(tokenConfig)
    configureSerialization()
    configureMonitoring()
    configureRouting(
        hashingService, userDataSource, tokenService, tokenConfig
    )
}





















package com.ahmed_apps

import com.ahmed_apps.plugins.*
import io.ktor.server.application.*
import org.litote.kmongo.KMongo

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    val mongoPassword = System.getenv("MONGO_PASSWORD")
    val databaseName = "watchy-course-database"

    val database = KMongo.createClient(
        connectionString =
        "mongodb+srv://watch-course-user:$mongoPassword@cluster-watchy-course.nyitfg8.mongodb.net/$databaseName?retryWrites=true&w=majority"
    ).getDatabase(databaseName)

    configureSecurity()
    configureSerialization()
    configureMonitoring()
    configureRouting()
}

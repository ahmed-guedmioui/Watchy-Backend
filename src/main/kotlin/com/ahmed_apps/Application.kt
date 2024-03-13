package com.ahmed_apps

import com.ahmed_apps.data.user.model.User
import com.ahmed_apps.data.user.model.dataSource.MongoUserDataSource
import com.ahmed_apps.plugins.configureMonitoring
import com.ahmed_apps.plugins.configureRouting
import com.ahmed_apps.plugins.configureSecurity
import com.ahmed_apps.plugins.configureSerialization
import com.mongodb.kotlin.client.coroutine.MongoClient
import io.ktor.server.application.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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



    configureSecurity()
    configureSerialization()
    configureMonitoring()
    configureRouting()
}





















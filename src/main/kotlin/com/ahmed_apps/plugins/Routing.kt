package com.ahmed_apps.plugins

import com.ahmed_apps.data.user.dataSource.UserDataSource
import com.ahmed_apps.route.authenticate
import com.ahmed_apps.route.login
import com.ahmed_apps.route.register
import com.ahmed_apps.security.hash.service.HashingService
import com.ahmed_apps.security.jwt.model.TokenConfig
import com.ahmed_apps.security.jwt.service.TokenService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    hashingService: HashingService,
    userDataSource: UserDataSource,
    tokenService: TokenService,
    tokenConfig: TokenConfig
) {
    routing {
        register(
            hashingService, userDataSource
        )
        login(
            hashingService, userDataSource, tokenService, tokenConfig
        )
        authenticate()
    }
}



















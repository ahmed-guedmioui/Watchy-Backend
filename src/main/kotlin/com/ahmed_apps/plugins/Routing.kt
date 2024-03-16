package com.ahmed_apps.plugins

import com.ahmed_apps.data.media.dataSource.MediaDataSource
import com.ahmed_apps.data.user.dataSource.UserDataSource
import com.ahmed_apps.route.*
import com.ahmed_apps.security.hash.service.HashingService
import com.ahmed_apps.security.jwt.model.TokenConfig
import com.ahmed_apps.security.jwt.service.TokenService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    mediaDataSource: MediaDataSource,
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

        getLikeMediaList(mediaDataSource, userDataSource)
        getBookmarkedMediaList(mediaDataSource, userDataSource)
        getMediaById(mediaDataSource, userDataSource)
        deleteMediaFromUser(mediaDataSource, userDataSource)
        upsertMediaToUser(mediaDataSource, userDataSource)

    }
}



















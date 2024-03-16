package com.ahmed_apps.route

import com.ahmed_apps.data.media.dataSource.MediaDataSource
import com.ahmed_apps.data.media.mappers.toMedia
import com.ahmed_apps.data.media.mappers.toMediaRespond
import com.ahmed_apps.data.media.model.requests.MediaByIdRequest
import com.ahmed_apps.data.media.model.requests.UpsertMediaRequest
import com.ahmed_apps.data.user.dataSource.UserDataSource
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getLikeMediaList(
    mediaDataSource: MediaDataSource,
    userDataSource: UserDataSource
) {
    route("get-liked-media-list/{email}") {
        get {
            val email = call.parameters["email"]
            if (email.isNullOrBlank()) {
                call.respond(
                    HttpStatusCode.BadRequest, "Email must be specified"
                )
                return@get
            }

            val user = userDataSource.getUserByEmail(email)
            if (user == null) {
                call.respond(
                    HttpStatusCode.NotFound, "No such user"
                )
                return@get
            }

            val likedMediaList = mediaDataSource.getLikedMediaListOfUser(email)
            if (likedMediaList == null) {
                call.respond(
                    HttpStatusCode.NoContent,
                    "No liked media for this user"
                )
                return@get
            }

            call.respond(
                status = HttpStatusCode.OK,
                message = likedMediaList.map { it.toMediaRespond() }
            )
        }
    }
}

fun Route.getBookmarkedMediaList(
    mediaDataSource: MediaDataSource,
    userDataSource: UserDataSource
) {
    route("get-bookmarked-media-list/{email}") {
        get {
            val email = call.parameters["email"]
            if (email.isNullOrBlank()) {
                call.respond(
                    HttpStatusCode.BadRequest, "Email must be specified"
                )
                return@get
            }

            val user = userDataSource.getUserByEmail(email)
            if (user == null) {
                call.respond(
                    HttpStatusCode.NotFound, "No such user"
                )
                return@get
            }

            val bookmarkedMediaList = mediaDataSource.getBookmarkedMediaListOfUser(email)
            if (bookmarkedMediaList == null) {
                call.respond(
                    HttpStatusCode.NoContent,
                    "No bookmarked media for this user"
                )
                return@get
            }

            call.respond(
                status = HttpStatusCode.OK,
                message = bookmarkedMediaList.map { it.toMediaRespond() }
            )
        }
    }
}


fun Route.getMediaById(
    mediaDataSource: MediaDataSource,
    userDataSource: UserDataSource
) {
    route("get-media-by-id/{mediaId}/{email}") {
        get {
            val mediaIdString = call.parameters["mediaId"]
            val email = call.parameters["email"]

            if (email.isNullOrBlank() || mediaIdString.isNullOrBlank()) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Email and media id must be specified"
                )
                return@get
            }

            val mediaId = mediaIdString.toIntOrNull()
            if (mediaId == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "invalid media id format"
                )
                return@get
            }

            val user = userDataSource.getUserByEmail(email)
            if (user == null) {
                call.respond(
                    HttpStatusCode.NotFound, "No such user"
                )
                return@get
            }

            val mediaFromDb = mediaDataSource.getMediaByIdAndUser(
                mediaId, email
            )
            if (mediaFromDb == null) {
                call.respond(
                    HttpStatusCode.NoContent, "No such media"
                )
                return@get
            }

            call.respond(
                status = HttpStatusCode.OK,
                message = mediaFromDb.toMediaRespond()
            )

        }
    }
}

fun Route.deleteMediaFromUser(
    mediaDataSource: MediaDataSource,
    userDataSource: UserDataSource
) {

    post("delete-media-from-user") {
        val request = call.receiveNullable<MediaByIdRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }


        if (request.email.isBlank()) {
            call.respond(
                HttpStatusCode.Conflict, "Email can't be blank"
            )
            return@post
        }

        val user = userDataSource.getUserByEmail(request.email)
        if (user == null) {
            call.respond(
                HttpStatusCode.Conflict, "No such user"
            )
            return@post
        }

        val wasAcknowledge = mediaDataSource.deleteMediaFromUser(
            request.email, request.mediaId
        )

        if (!wasAcknowledge) {
            call.respond(
                HttpStatusCode.NotFound,
                "Media deletion failed or media not found"
            )
            return@post
        }

        call.respond(
            HttpStatusCode.OK,
            message = "Media deletion succeeded"
        )

    }

}


fun Route.upsertMediaToUser(
    mediaDataSource: MediaDataSource,
    userDataSource: UserDataSource
) {

    post("upsert-media-to-user") {
        val request = call.receiveNullable<UpsertMediaRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }


        if (request.email.isBlank()) {
            call.respond(
                HttpStatusCode.Conflict, "Email can't be blank"
            )
            return@post
        }

        val user = userDataSource.getUserByEmail(request.email)
        if (user == null) {
            call.respond(
                HttpStatusCode.Conflict, "No such user"
            )
            return@post
        }

        val wasAcknowledge = mediaDataSource.upsertMediaToUser(
            request.email, request.mediaRequest.toMedia()
        )

        if (!wasAcknowledge) {
            call.respond(
                HttpStatusCode.NotFound,
                "Media upsert failed"
            )
            return@post
        }

        call.respond(
            HttpStatusCode.OK,
            message = "Media upset succeeded"
        )

    }

}













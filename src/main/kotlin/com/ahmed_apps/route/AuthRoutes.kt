package com.ahmed_apps.route

import com.ahmed_apps.data.auth.AuthRequest
import com.ahmed_apps.data.auth.AuthRespond
import com.ahmed_apps.data.user.model.User
import com.ahmed_apps.data.user.dataSource.UserDataSource
import com.ahmed_apps.security.hash.service.HashingService
import com.ahmed_apps.security.jwt.model.TokenClaim
import com.ahmed_apps.security.jwt.model.TokenConfig
import com.ahmed_apps.security.jwt.service.TokenService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.register(
    hashingService: HashingService,
    userDataSource: UserDataSource
) {

    post("register") {
        val request = call.receiveNullable<AuthRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val areEmailAndPasswordBlank =
            request.email.isBlank() || request.password.isBlank()
        val isValidPassword = request.password.length > 8

        if (areEmailAndPasswordBlank || !isValidPassword) {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val userWithRequestEmail = userDataSource.getUserByEmail(request.email)
        if (userWithRequestEmail != null) {
            call.respond(
                HttpStatusCode.Conflict, "This email is already taken"
            )
            return@post
        }

        val hashAndSalt = hashingService.generateHash(request.password)

        val user = User(
            name = request.name,
            email = request.email,
            hash = hashAndSalt.hash,
            salt = hashAndSalt.salt,
            mediaList = ArrayList()
        )

        val wasAcknowledge = userDataSource.insertUser(user)
        if (!wasAcknowledge) {
            call.respond(HttpStatusCode.Conflict)
            return@post
        }

        call.respond(HttpStatusCode.OK)

    }

}

fun Route.login(
    hashingService: HashingService,
    userDataSource: UserDataSource,
    tokenService: TokenService,
    tokenConfig: TokenConfig
) {

    post("login") {
        val request = call.receiveNullable<AuthRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }


        val areEmailAndPasswordBlank =
            request.email.isBlank() || request.password.isBlank()

        if (areEmailAndPasswordBlank) {
            call.respond(
                HttpStatusCode.Conflict, "Wrong email or password"
            )
            return@post
        }

        val user = userDataSource.getUserByEmail(request.email)
        if (user == null) {
            call.respond(
                HttpStatusCode.Conflict, "Wrong email or password"
            )
            return@post
        }

        val isValidPassword = hashingService.verifyHash(
            password = request.password,
            salt = user.salt,
            hash = user.hash
        )

        if (!isValidPassword) {
            call.respond(
                HttpStatusCode.Conflict, "Wrong email or password"
            )
            return@post
        }

        val token = tokenService.generate(
            tokenConfig = tokenConfig,
            TokenClaim(
                name = "userId",
                value = user.id.toString()
            )
        )

        call.respond(
            status = HttpStatusCode.OK,
            message = AuthRespond(
                name = user.name,
                token = token
            )
        )

    }

}


fun Route.authenticate() {
    authenticate {
        get("authenticate") {
            call.respond(HttpStatusCode.OK)
        }
    }
}


















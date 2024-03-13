package com.ahmed_apps.security.jwt.model

data class TokenConfig(
    val issuer: String,
    val audience: String,
    val expireDate: Long,
    val secret: String
)
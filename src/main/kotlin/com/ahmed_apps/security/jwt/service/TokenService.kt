package com.ahmed_apps.security.jwt.service

import com.ahmed_apps.security.jwt.model.TokenClaim
import com.ahmed_apps.security.jwt.model.TokenConfig

interface TokenService {
    fun generate(
        tokenConfig: TokenConfig,
        vararg tokenClaims: TokenClaim
    ): String
}
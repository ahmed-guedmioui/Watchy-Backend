package com.ahmed_apps.security.hash.service

import com.ahmed_apps.security.hash.model.Hash

interface HashingService {

    fun generateHash(
        password: String,
        saltLength: Int = 23
    ): Hash

    fun verifyHash(
        password: String,
        hash: String,
        salt: String
    ): Boolean

}





















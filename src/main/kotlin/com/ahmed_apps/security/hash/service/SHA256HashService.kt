package com.ahmed_apps.security.hash.service

import com.ahmed_apps.security.hash.model.Hash
import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils
import java.security.SecureRandom

class SHA256HashService : HashingService {
    override fun generateHash(
        password: String, saltLength: Int
    ): Hash {

        val salt = SecureRandom.getInstance(
            "SHA1PRNG"
        ).generateSeed(saltLength)
        val saltHex = Hex.encodeHexString(salt)

        val hash = DigestUtils.sha256Hex("$saltHex$password")

        return Hash(
            hash = hash,
            salt = saltHex
        )

    }

    override fun verifyHash(
        password: String, hash: String, salt: String
    ): Boolean {
        val isCorrect = DigestUtils.sha256Hex("$salt$password") == hash
        return isCorrect
    }
}

















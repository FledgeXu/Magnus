package com.otakusaikou.magnus.sercrecy

import at.favre.lib.crypto.bcrypt.BCrypt
import at.favre.lib.crypto.bcrypt.LongPasswordStrategy

/**
 * We use Bcrypt to save user's password
 * About Bcrypt:
 * version: VERSION_2B, round: 5, salt = hashCodeTale.salt1, LongPasswordStrategy: PassThroughStrategy
 */
fun genHashedPassword(password: ByteArray, salt: ByteArray): ByteArray {
    return BCrypt.with(BCrypt.Version.VERSION_2B, LongPasswordStrategy.PassThroughStrategy()).hash(5, salt, password);
}
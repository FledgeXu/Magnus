package com.otakusaikou.magnus.service

import com.otakusaikou.magnus.domain.UserTable

interface IUserDao {

    fun createUser(userName: String, nickName: String, password: ByteArray, isAdmin: Boolean = false): Boolean
    fun deleteUser(userName: String): Boolean
    fun findUser(userName: String): UserTable?
    fun validateUser(userName: String, inputPassword: ByteArray): Boolean
    fun getLoginSalt(userName: String): ByteArray
    fun changePassword(userName: String, oldPassword: ByteArray, newPassword: ByteArray): Boolean
    fun changeNickName(userName: String, password: ByteArray, newNickName: String): Boolean
    fun changePermission(userName: String, password: ByteArray, isAdmin: Boolean): Boolean
    fun isPermission(userName: String): Boolean
    fun deleteAll(): Boolean
}
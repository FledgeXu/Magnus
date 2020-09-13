package com.otakusaikou.magnus.service.impl

import com.otakusaikou.magnus.domain.HashCodeTable
import com.otakusaikou.magnus.domain.IUserTableRepository
import com.otakusaikou.magnus.domain.UserTable
import com.otakusaikou.magnus.sercrecy.genHashedPassword
import com.otakusaikou.magnus.service.IUserDao
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class UserDao @Autowired constructor(val userTableRepository: IUserTableRepository) : IUserDao {

    override fun createUser(userName: String, nickName: String, password: ByteArray, isAdmin: Boolean): Boolean {
        if (userTableRepository.findByUserName(userName) != null) {
            return false
        }
        val hashCodeTable = HashCodeTable(RandomStringUtils.randomAscii(16).toByteArray())
        val hashedPassword = genHashedPassword(password, hashCodeTable.salt1)
        userTableRepository.save(UserTable(userName, nickName, hashedPassword, hashCodeTable, isAdmin))
        return true
    }


    @Transactional
    override fun deleteUser(userName: String): Boolean {
        userTableRepository.deleteByUserName(userName)
        return true
    }

    override fun findUser(userName: String): UserTable? {
        return userTableRepository.findByUserName(userName)
    }

    override fun validateUser(userName: String, inputPassword: ByteArray): Boolean {
        return (findUser(userName)?.hashedPassword?.contentEquals(inputPassword)) ?: false
    }

    override fun getLoginSalt(userName: String): ByteArray {
        return userTableRepository.findByUserName(userName)?.hashCodeTable?.salt1 ?: ByteArray(0)
    }

    override fun changePassword(userName: String, oldPassword: ByteArray, newPassword: ByteArray): Boolean {
        return validateUserAndDo(userName, oldPassword) {
            val oldUser = findUser(userName)!!
            val hashedPassword = genHashedPassword(newPassword, oldUser.hashCodeTable.salt1)
            val newUser = UserTable(oldUser.userName, oldUser.nickName, hashedPassword, oldUser.hashCodeTable, oldUser.isAdmin, oldUser.subscriptionTable, oldUser.id, oldUser.categoryTables)
            userTableRepository.save(newUser)
            true
        }
    }

    private fun validateUserAndDo(userName: String, password: ByteArray, something: () -> Boolean): Boolean {
        if (validateUser(userName, password)) {
            return something.invoke()
        }
        return false
    }

    override fun changeNickName(userName: String, password: ByteArray, newNickName: String): Boolean {
        return validateUserAndDo(userName, password) {
            val oldUser = findUser(userName)!!
            val newUser = UserTable(oldUser.userName, newNickName, oldUser.hashedPassword, oldUser.hashCodeTable, oldUser.isAdmin, oldUser.subscriptionTable, oldUser.id, oldUser.categoryTables)
            userTableRepository.save(newUser)
            true
        }
    }

    override fun changePermission(userName: String, password: ByteArray, isAdmin: Boolean): Boolean {
        return validateUserAndDo(userName, password) {
            val oldUser = findUser(userName)!!
            val newUser = UserTable(oldUser.userName, oldUser.nickName, oldUser.hashedPassword, oldUser.hashCodeTable, isAdmin, oldUser.subscriptionTable, oldUser.id, oldUser.categoryTables)
            userTableRepository.save(newUser)
            true
        }
    }

    override fun isPermission(userName: String): Boolean {
        return userTableRepository.findByUserName(userName)?.isAdmin ?: false
    }

    override fun deleteAll(): Boolean {
        userTableRepository.deleteAll()
        return true
    }
}
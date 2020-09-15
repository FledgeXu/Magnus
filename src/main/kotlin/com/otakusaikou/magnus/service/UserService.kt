package com.otakusaikou.magnus.service

import com.otakusaikou.magnus.domain.HashCodeTable
import com.otakusaikou.magnus.domain.IUserInfoRepository
import com.otakusaikou.magnus.domain.UserInfo
import com.otakusaikou.magnus.model.User
import com.otakusaikou.magnus.sercrecy.genHashedPassword
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService(@Autowired val userInfoRepository: IUserInfoRepository) {
    private lateinit var user: User
    fun initService(user: User) {
        this.user = user;
    }

    fun createUser(userName: String, password: ByteArray, nickName: String, isAdmin: Boolean): Boolean {
        if (userInfoRepository.findByUserName(userName) != null) {
            return false
        }
        val hashCodeTable = HashCodeTable(RandomStringUtils.randomAscii(16).toByteArray())
        val hashedPassword = genHashedPassword(password, hashCodeTable.salt1)
        userInfoRepository.save(UserInfo(userName, nickName, hashedPassword, hashCodeTable, isAdmin))
        return true
    }

    fun deleteUser(): Boolean {
        return validateUserAndDo {
            userInfoRepository.deleteByUserName(user.userName)
            true
        }
    }

    fun findUser(): UserInfo? {
        return userInfoRepository.findByUserName(user.userName)
    }

    fun validateUser(): Boolean {
        return (findUser()?.hashedPassword?.contentEquals(user.password)) ?: false
    }

    fun getLoginSalt(userName: String): ByteArray {
        return userInfoRepository.findByUserName(userName)?.hashCodeTable?.salt1 ?: ByteArray(0)
    }

    fun changePassword(newPassword: ByteArray): Boolean {
        return validateUserAndDo {
            updateUserInfo {
                UserInfo(it.userName, it.nickName, genHashedPassword(newPassword, it.hashCodeTable.salt1), it.hashCodeTable, it.isAdmin, it.subscriptionTable, it.id, it.categoryTables)
            }
        }
    }

    private fun validateUserAndDo(something: () -> Boolean): Boolean {
        if (validateUser()) {
            return something.invoke()
        }
        return false
    }

    fun changeNickName(newNickName: String): Boolean {
        return validateUserAndDo {
            updateUserInfo {
                UserInfo(it.userName, newNickName, it.hashedPassword, it.hashCodeTable, it.isAdmin, it.subscriptionTable, it.id, it.categoryTables)
            }
        }
    }

    fun changePermission(isAdmin: Boolean): Boolean {
        return validateUserAndDo {
            updateUserInfo {
                UserInfo(it.userName, it.nickName, it.hashedPassword, it.hashCodeTable, isAdmin, it.subscriptionTable, it.id, it.categoryTables)
            }
        }
    }

    private fun updateUserInfo(getNewUserInfo: (oldUserInfo: UserInfo) -> UserInfo): Boolean {
        val oldUserInfo = findUser()!!
        val newUserInfo: UserInfo = getNewUserInfo(oldUserInfo)
        userInfoRepository.save(newUserInfo)
        return true
    }

    fun isPermission(): Boolean {
        return userInfoRepository.findByUserName(user.userName)?.isAdmin ?: false
    }

    fun deleteAll(): Boolean {
        userInfoRepository.deleteAll()
        return true
    }
}
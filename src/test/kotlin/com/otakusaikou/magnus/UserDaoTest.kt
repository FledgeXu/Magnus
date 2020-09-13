package com.otakusaikou.magnus

import com.otakusaikou.magnus.sercrecy.genHashedPassword
import com.otakusaikou.magnus.service.IUserDao
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserDaoTest @Autowired constructor(val userDao: IUserDao) {
    @Test
    fun doTest() {
        userDao.createUser("test", "Test", "123456".toByteArray())
        userDao.createUser("test2", "Test 2", "password".toByteArray())
        val salt = userDao.getLoginInfo("test")
        userDao.changePassword("test", genHashedPassword("123456".toByteArray(), salt), "654321".toByteArray())
    }
}
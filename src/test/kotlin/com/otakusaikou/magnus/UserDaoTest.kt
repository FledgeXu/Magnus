package com.otakusaikou.magnus

import com.otakusaikou.magnus.sercrecy.genHashedPassword
import com.otakusaikou.magnus.service.IUserDao
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class UserDaoTest @Autowired constructor(val userDao: IUserDao) {

    @Test
    fun doTest() {
        //Create
        userDao.createUser("test", "Test", "123456".toByteArray())
        Assertions.assertEquals(userDao.findUser("test")?.userName, "test")
        userDao.deleteAll()

        //Delete
        userDao.createUser("test2", "Test 2", "123456".toByteArray())
        Assertions.assertEquals(userDao.findUser("test2")?.userName, "test2")
        userDao.deleteUser("test2")
        Assertions.assertNull(userDao.findUser("test2"))
        userDao.deleteAll()

        //getLoginSalt
        userDao.createUser("test4", "Test 4", "123456".toByteArray())
        Assertions.assertNotEquals(userDao.getLoginSalt("test4"), ByteArray(0))
        userDao.deleteAll()

        //validateUser
        userDao.createUser("test3", "Test 3", "123456".toByteArray())
        val validateUserSalt = userDao.getLoginSalt("test3")
        Assertions.assertEquals(userDao.validateUser("test3", genHashedPassword("123456".toByteArray(), validateUserSalt)), true)
        Assertions.assertEquals(userDao.validateUser("test3", genHashedPassword("654321".toByteArray(), validateUserSalt)), false)
        userDao.deleteAll()

        //Change Password
        userDao.createUser("test5", "Test 5", "123456".toByteArray())
        val changePasswordSalt = userDao.getLoginSalt("test5")
        Assertions.assertEquals(userDao.changePassword("test5", genHashedPassword("111111".toByteArray(), changePasswordSalt), "222222".toByteArray()), false)
        Assertions.assertEquals(userDao.changePassword("test5", genHashedPassword("123456".toByteArray(), changePasswordSalt), "654321".toByteArray()), true)
        userDao.deleteAll()

        //Change Nick Name
        userDao.createUser("test6", "Test 6", "123456".toByteArray())
        val changeNickNameSalt = userDao.getLoginSalt("test6")
        Assertions.assertEquals(userDao.changeNickName("test6", genHashedPassword("111111".toByteArray(), changeNickNameSalt), "6 Test"), false)
        Assertions.assertEquals(userDao.changeNickName("test6", genHashedPassword("123456".toByteArray(), changeNickNameSalt), "6 Test"), true)
        userDao.deleteAll()

        //Change Permission
        userDao.createUser("test7", "Test 7", "123456".toByteArray())
        val changePermissionSalt = userDao.getLoginSalt("test7")
        Assertions.assertEquals(userDao.changePermission("test7", genHashedPassword("111111".toByteArray(), changePermissionSalt), true), false)
        Assertions.assertEquals(userDao.changePermission("test7", genHashedPassword("123456".toByteArray(), changePermissionSalt), true), true)
        userDao.deleteAll()
    }
}
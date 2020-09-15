package com.otakusaikou.magnus

import com.otakusaikou.magnus.model.User
import com.otakusaikou.magnus.sercrecy.genHashedPassword
import com.otakusaikou.magnus.service.UserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.transaction.Transactional


@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest(@Autowired val userService: UserService) {
    private lateinit var userName: String
    private lateinit var password: ByteArray

    @BeforeAll
    fun beforeTest() {
        userName = "test"
        password = "123456".toByteArray()
        userService.createUser(userName, password, "Test", false)
        val user = User(userName, genHashedPassword(password, userService.getLoginSalt(userName)))
        userService.initService(user);
    }

    @Test
    fun `delete user`() {
        userService.deleteUser()
        Assertions.assertNull(userService.findUser())
    }

    @Test
    fun `get login salt`() {
        Assertions.assertEquals(userService.getLoginSalt(userName).contentEquals(ByteArray(0)), false)
    }

    @Test
    fun `validate User`() {
        Assertions.assertEquals(userService.validateUser(), true)
    }

    @Test
    fun `change password`() {
        Assertions.assertEquals(userService.changePassword("111111".toByteArray()), true)
    }

    @Test
    fun `change nickname`() {
        Assertions.assertEquals(userService.changeNickName("Test 2"), true)
    }

    @Test
    fun `change permission`() {
        Assertions.assertEquals(userService.changePermission(true), true)
    }
}
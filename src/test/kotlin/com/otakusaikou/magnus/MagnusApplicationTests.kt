package com.otakusaikou.magnus

import com.otakusaikou.magnus.domain.*
import org.apache.commons.lang3.RandomStringUtils
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.net.URL

@SpringBootTest
class MagnusApplicationTests @Autowired constructor(val userTableRepository: IUserTableRepository) {

    @Test
    fun contextLoads() {
        userTableRepository.save(
                UserTable(
                        "test",
                        "Test",
                        RandomStringUtils.randomAscii(10).toByteArray(),
                        HashCodeTable(RandomStringUtils.randomAscii(512), RandomStringUtils.randomAscii(512)),
                        true,
                        subscriptionTable = setOf(SubscriptionTable("Baidu", URL("https://www.baidu.com"))),
                        categoryTables = setOf(CategoryTable("Cate 1"))
                )
        )
    }

}

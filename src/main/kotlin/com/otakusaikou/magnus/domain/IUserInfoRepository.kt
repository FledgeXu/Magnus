package com.otakusaikou.magnus.domain

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface IUserInfoRepository : JpaRepository<UserInfo, UUID> {
    fun findByUserName(name: String): UserInfo?
    fun deleteByUserName(name: String): Int
}
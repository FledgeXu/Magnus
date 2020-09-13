package com.otakusaikou.magnus.domain

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface IUserTableRepository : JpaRepository<UserTable, UUID> {
    fun findByUserName(name: String): UserTable?
    fun deleteByUserName(name: String): Int
}
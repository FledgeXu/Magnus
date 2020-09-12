package com.otakusaikou.magnus.domain

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface IUserTableRepository : JpaRepository<UserTable, UUID> {
    fun findByUserName(name: String): UserTable
//    fun findByName(name: String): UserTable
//    fun findByNameAndAge(name: String, age: Int): UserTable
//    @Query("from UserTable u where u.name=:name")
//    fun findUserTable(@Param("name") name: String)
}
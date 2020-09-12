package com.otakusaikou.magnus.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.NotNull

/**
 * https://blog.coderzh.com/2016/01/10/a-password-security-design-example/
 */
@Entity
class HashCodeTable(
        @NotNull
        @Column(columnDefinition = "TEXT")
        val salt1: String,
        @NotNull
        @Column(columnDefinition = "TEXT")
        val salt2: String,
        @Id
        @GeneratedValue
        val id: Long? = null
)
package com.otakusaikou.magnus.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.NotNull

@Entity
class CategoryTable(
        @NotNull
        val name: String,
        @Id
        @GeneratedValue
        val id: Long? = null
)
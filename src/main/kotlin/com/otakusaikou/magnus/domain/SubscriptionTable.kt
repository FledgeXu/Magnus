package com.otakusaikou.magnus.domain

import java.net.URL
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
class SubscriptionTable(
        @NotNull
        val name: String,
        @NotNull
        @org.hibernate.validator.constraints.URL
        val url: URL,
        @ManyToMany(cascade = [CascadeType.ALL])
        @JoinTable
        val categoryTable: Set<CategoryTable?>? = null,
        @Id
        @GeneratedValue
        val id: Long? = null
)
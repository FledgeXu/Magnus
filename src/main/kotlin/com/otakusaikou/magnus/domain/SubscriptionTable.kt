package com.otakusaikou.magnus.domain

import java.net.URL
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.validation.constraints.NotNull

@Entity
class SubscriptionTable(
        @NotNull
        val name: String,
        @NotNull
        @org.hibernate.validator.constraints.URL
        val url: URL,
        @ManyToMany(mappedBy = "subscriptionTable")
        val userTables: Set<UserTable>? = null,
        @Id
        @GeneratedValue
        val id: Long? = null
)
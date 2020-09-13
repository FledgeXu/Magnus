package com.otakusaikou.magnus.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
class UserTable(
        @NotNull
        val userName: String,
        @NotNull
        val nickName: String,
        @NotNull
        val hashedPassword: ByteArray,
        @OneToOne(cascade = [CascadeType.ALL])
        val hashCodeTable: HashCodeTable,
        @NotNull
        val isAdmin: Boolean,
        @JsonIgnoreProperties(value = ["userTables"])
        @ManyToMany(cascade = [CascadeType.ALL])
        @JoinTable()
        val subscriptionTable: Set<SubscriptionTable>? = null,
        @Id
        @GeneratedValue
        val id: UUID? = null,
        @OneToMany(cascade = [CascadeType.ALL])
        @JoinColumn(name = "userUUID")
        val categoryTables: Set<CategoryTable>? = null
)
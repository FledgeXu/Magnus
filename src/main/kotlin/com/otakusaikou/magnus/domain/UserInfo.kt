package com.otakusaikou.magnus.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
class UserInfo(
        @NotNull
        val userName: String,
        @NotNull
        val nickName: String,
        @NotNull
        @JsonIgnoreProperties
        val hashedPassword: ByteArray,
        @OneToOne(cascade = [CascadeType.ALL])
        @JsonIgnoreProperties
        val hashCodeTable: HashCodeTable,
        @NotNull
        val isAdmin: Boolean = false,
        @JsonIgnoreProperties()
        @ManyToMany(cascade = [CascadeType.ALL])
        @JoinTable()
        val subscriptionTable: Set<SubscriptionTable>? = null,
        @Id
        @GeneratedValue
        val id: UUID? = null,
        @OneToMany(cascade = [CascadeType.ALL])
        @JoinColumn(name = "userUUID")
        @JsonIgnoreProperties
        val categoryTables: Set<CategoryTable>? = null
)
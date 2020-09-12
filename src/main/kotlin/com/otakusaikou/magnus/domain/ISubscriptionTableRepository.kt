package com.otakusaikou.magnus.domain

import org.springframework.data.jpa.repository.JpaRepository

interface ISubscriptionTableRepository : JpaRepository<SubscriptionTable, Long> {
}
package com.example.springjms.repository

import com.example.springjms.model.entity.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<Order, Long> {
    fun findOrderById(id: Long?): Order
}
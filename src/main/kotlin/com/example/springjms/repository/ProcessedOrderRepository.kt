package com.example.springjms.repository

import com.example.springjms.model.entity.ProcessedOrder
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProcessedOrderRepository: JpaRepository<ProcessedOrder, Long> {
}
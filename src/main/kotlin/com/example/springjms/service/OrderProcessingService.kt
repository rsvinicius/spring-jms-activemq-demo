package com.example.springjms.service

import com.example.springjms.model.entity.Order
import com.example.springjms.model.entity.ProcessedOrder
import com.example.springjms.repository.OrderRepository
import com.example.springjms.repository.ProcessedOrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
class OrderProcessingService(
    private val orderRepository: OrderRepository,
    private val processedOrderRepository: ProcessedOrderRepository
) {

    @Transactional(transactionManager = "jpaTM")
    fun processOrderByOrderState(order: Order, orderState: String): ProcessedOrder {
        return when (orderState) {
            "NEW" -> addOrder(order)
            "UPDATE" -> updateOrder(order)
            "DELETE" -> deleteOrder(order)
            else -> throw IllegalArgumentException(
                "processOrder: OrderState=$orderState does not match expected criteria!"
            )
        }
    }

    private fun addOrder(order: Order): ProcessedOrder {
        if (isOrderValueInvalid(order)) {
            throw RuntimeException("processOrder: Invalid value for orderId=${order.id}")
        }

        val addedOrder = orderRepository.save(order)

        return processOrder(addedOrder)
    }

    private fun updateOrder(order: Order): ProcessedOrder {
        val updatedOrder = orderRepository.findOrderById(order.id).apply {
            customerId = order.customerId ?: customerId
            value = order.value ?: value
        }

        val savedOrder = orderRepository.save(updatedOrder)

        return processOrder(savedOrder)
    }

    private fun deleteOrder(order: Order): ProcessedOrder {
        val dbOrder = orderRepository.findOrderById(order.id).also {
            orderRepository.delete(it)
        }

        return processOrder(dbOrder)
    }

    private fun processOrder(order: Order): ProcessedOrder {
        val processedOrder = ProcessedOrder().apply {
            orderId = order.id
        }

        return processedOrderRepository.save(processedOrder)
    }

    private fun isOrderValueInvalid(order: Order): Boolean {
        val orderValue = order.value ?: BigDecimal.valueOf(-1)
        return orderValue < BigDecimal.ZERO
    }
}
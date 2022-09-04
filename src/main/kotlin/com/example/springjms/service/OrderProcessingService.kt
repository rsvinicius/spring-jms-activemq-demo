package com.example.springjms.service

import com.example.springjms.model.entity.Order
import com.example.springjms.model.entity.ProcessedOrder
import com.example.springjms.repository.OrderRepository
import com.example.springjms.repository.ProcessedOrderRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
class OrderProcessingService(
    private val orderRepository: OrderRepository,
    private val processedOrderRepository: ProcessedOrderRepository
) {
    private val logger = KotlinLogging.logger {}

    @Transactional(transactionManager = "jpaTM")
    fun processOrder(order: Order, orderState: String): ProcessedOrder {
        if (order.value < BigDecimal.ZERO) {
            throw RuntimeException("processOrder: Invalid value for orderId=${order.id}")
        }

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
        logger.info { "addOrder: Adding a new order to db, orderId=${order.id}" }

        val addedOrder = orderRepository.save(order)

        val processedOrder = ProcessedOrder().apply {
            this.orderId = addedOrder.id
        }

        return processedOrderRepository.save(processedOrder)
    }

    private fun updateOrder(order: Order): ProcessedOrder {
        logger.info { "updateOrder: Updating a order to db, orderId=${order.id}" }

        val dbOrder = orderRepository.findOrderById(order.id)

        val updatedOrder = dbOrder.apply {
            this.customerId = order.customerId
            this.value = order.value
        }

        val savedOrder = orderRepository.save(updatedOrder)

        val processedOrder = ProcessedOrder().apply {
            this.orderId = savedOrder.customerId
        }

        return processedOrderRepository.save(processedOrder)
    }

    private fun deleteOrder(order: Order): ProcessedOrder {
        logger.info { "deleteOrder: Deleting a order from db, orderId=${order.id}" }

        val dbOrder = orderRepository.findOrderById(order.id).also {
            orderRepository.delete(it)
        }

        val processedOrder = ProcessedOrder().apply {
            this.orderId = dbOrder.customerId
        }

        return processedOrderRepository.save(processedOrder)
    }
}
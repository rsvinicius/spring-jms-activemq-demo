package com.example.springjms.controller

import com.example.springjms.model.entity.Order
import com.example.springjms.service.OrderService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import mu.KotlinLogging
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/orders")
@Tag(name = "OrderController", description = "Order")
class OrderController(private val orderService: OrderService) {
    private val logger = KotlinLogging.logger {}

    @PostMapping
    @Operation(summary = "Send order")
    fun sendOrder(@RequestBody order: Order, @RequestParam orderState: String) {
        logger.info {
            "sendOrder: sending order from customerId=${order.customerId}"
        }
        orderService.sendOrderToQueue(order, orderState).also {
            logger.info {
                "sendOrder: order sent, customerId=${order.customerId}"
            }
        }
    }
}
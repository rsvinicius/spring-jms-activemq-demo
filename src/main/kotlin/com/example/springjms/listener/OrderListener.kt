package com.example.springjms.listener

import com.example.springjms.model.entity.Order
import com.example.springjms.model.entity.ProcessedOrder
import com.example.springjms.service.OrderProcessingService
import mu.KotlinLogging
import org.springframework.jms.annotation.JmsListener
import org.springframework.messaging.MessageHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Component

@Component
class OrderListener(private val orderProcessingService: OrderProcessingService) {
    private val logger = KotlinLogging.logger {}

    @JmsListener(destination = "\${spring.activemq.queue-name.order}")
    @SendTo("\${spring.activemq.queue-name.processed-order}")
    fun receiveOrder(
        @Payload order: Order,
        @Header(name = "orderState") orderState: String,
        messageHeaders: MessageHeaders
    ): ProcessedOrder {
        logger.info { "receiveOrder: Message received, orderState=$orderState" }
        return orderProcessingService.processOrder(order, orderState).also {
            logger.info {
                "receiveOrder: Order processed successfully, " +
                        "orderState=$orderState, orderId=${it.orderId}, processingDateTime=${it.processingDateTime}"
            }
        }
    }
}
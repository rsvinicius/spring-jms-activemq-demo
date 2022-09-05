package com.example.springjms.service

import com.example.springjms.model.entity.Order
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.jms.core.JmsTemplate
import org.springframework.jms.core.MessagePostProcessor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(@Autowired private val jmsTemplate: JmsTemplate) {
    @Value("\${spring.activemq.queue-name.order}")
    lateinit var queueName: String

    @Transactional(transactionManager = "jmsTM")
    fun sendOrderToQueue(order: Order, orderState: String) {
        jmsTemplate.convertAndSend(queueName, order, MessagePostProcessor() { message ->
            order.id?.let { message.setLongProperty("OrderId", it) }
            message.setStringProperty("orderState", orderState)
            return@MessagePostProcessor message
        })
    }
}
package com.example.springjms.config

import org.apache.activemq.ActiveMQConnectionFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jms.annotation.EnableJms
import org.springframework.jms.config.DefaultJmsListenerContainerFactory
import org.springframework.jms.connection.SingleConnectionFactory
import org.springframework.jms.support.converter.MappingJackson2MessageConverter
import org.springframework.jms.support.converter.MessageType

// Jms Config using ActiveMQ Connection Factory

@Configuration
@EnableJms
class JmsConfigCachedConnection {
    @Value("\${spring.activemq.broker-url}")
    lateinit var brokerUrl: String

    @Value("\${spring.activemq.user}")
    lateinit var user: String

    @Value("\${spring.activemq.password}")
    lateinit var password: String

    @Bean
    fun jacksonJmsMessageConverter(): MappingJackson2MessageConverter {
        return MappingJackson2MessageConverter().apply {
            this.setTargetType(MessageType.TEXT)
            this.setTypeIdPropertyName("_type")
        }
    }

    @Bean
    fun connectionFactory(): SingleConnectionFactory {
        return SingleConnectionFactory().apply {
            this.targetConnectionFactory = ActiveMQConnectionFactory(user, password, brokerUrl)
            this.setReconnectOnException(true)
            this.setClientId("myClientId")
        }
    }

    @Bean
    fun jmsListenerContainerFactory(): DefaultJmsListenerContainerFactory {
        return DefaultJmsListenerContainerFactory().apply {
            this.setConnectionFactory(connectionFactory())
            this.setMessageConverter(jacksonJmsMessageConverter())
        }
    }
}
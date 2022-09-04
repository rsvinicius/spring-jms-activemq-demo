package com.example.springjms.config

import mu.KotlinLogging
import org.apache.activemq.ActiveMQConnectionFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.jms.annotation.EnableJms
import org.springframework.jms.config.DefaultJmsListenerContainerFactory
import org.springframework.jms.connection.CachingConnectionFactory
import org.springframework.jms.connection.JmsTransactionManager
import org.springframework.jms.core.JmsTemplate
import org.springframework.jms.support.converter.MappingJackson2MessageConverter
import org.springframework.jms.support.converter.MessageType
import org.springframework.transaction.annotation.EnableTransactionManagement


@EnableJms
@EnableTransactionManagement
@Configuration
class JmsConfigCachedConnection {
    private val logger = KotlinLogging.logger {}

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
            this.setTypeIdPropertyName("_jsonType")
        }
    }

    @Bean
    fun connectionFactory(): CachingConnectionFactory {
        return CachingConnectionFactory().apply {
            this.targetConnectionFactory = ActiveMQConnectionFactory(user, password, brokerUrl)
            this.setClientId("ActiveMQ")
            this.sessionCacheSize = 100
        }
    }

    @Bean("jmsTM")
    @DependsOn("connectionFactory")
    fun jmsTransactionManager(): JmsTransactionManager {
        return JmsTransactionManager(connectionFactory())
    }

    @Bean
    @DependsOn("connectionFactory", "jacksonJmsMessageConverter", "jmsTM")
    fun jmsListenerContainerFactory(): DefaultJmsListenerContainerFactory {
        return DefaultJmsListenerContainerFactory().apply {
            this.setConnectionFactory(connectionFactory())
            this.setMessageConverter(jacksonJmsMessageConverter())
            this.setTransactionManager(jmsTransactionManager())
            this.setErrorHandler { t ->
                logger.info { "Handling error in listener for messages, error=${t.message}" }
            }
        }
    }

    @Bean
    @DependsOn("connectionFactory", "jacksonJmsMessageConverter")
    fun jmsTemplate(): JmsTemplate {
        return JmsTemplate().apply {
            this.connectionFactory = connectionFactory()
            this.messageConverter = jacksonJmsMessageConverter()
            this.setDeliveryPersistent(true)
            this.isSessionTransacted = true
        }
    }
}
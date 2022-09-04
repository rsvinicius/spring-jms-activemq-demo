//package com.example.springjms.config
//
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.jms.annotation.EnableJms
//import org.springframework.jms.config.DefaultJmsListenerContainerFactory
//import org.springframework.jms.support.converter.MappingJackson2MessageConverter
//import org.springframework.jms.support.converter.MessageType
//import javax.jms.ConnectionFactory
//
//@Configuration
//@EnableJms
//class JmsConfigApplicationYaml(@Autowired private val connectionFactory: ConnectionFactory) {
//    @Bean
//    fun jacksonJmsMessageConverter(): MappingJackson2MessageConverter {
//        return MappingJackson2MessageConverter().apply {
//            this.setTargetType(MessageType.TEXT)
//            this.setTypeIdPropertyName("ActiveMQ")
//        }
//    }
//
//    @Bean
//    fun jmsListenerContainerFactory(): DefaultJmsListenerContainerFactory {
//        return DefaultJmsListenerContainerFactory().apply {
//            this.setConnectionFactory(connectionFactory)
//            this.setMessageConverter(jacksonJmsMessageConverter())
//        }
//    }
//}
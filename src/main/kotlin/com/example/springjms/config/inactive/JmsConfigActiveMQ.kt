//package com.example.springjms.config
//
//import com.example.springjms.listener.ExampleListener
//import org.apache.activemq.ActiveMQConnectionFactory
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.jms.annotation.EnableJms
//import org.springframework.jms.annotation.JmsListenerConfigurer
//import org.springframework.jms.config.DefaultJmsListenerContainerFactory
//import org.springframework.jms.config.JmsListenerEndpointRegistrar
//import org.springframework.jms.config.SimpleJmsListenerEndpoint
//import org.springframework.jms.support.converter.MappingJackson2MessageConverter
//import org.springframework.jms.support.converter.MessageType
//
//@Configuration
//@EnableJms
//class JmsConfigActiveMQ : JmsListenerConfigurer {
//    @Value("\${spring.activemq.broker-url}")
//    lateinit var brokerUrl: String
//
//    @Value("\${spring.activemq.user}")
//    lateinit var user: String
//
//    @Value("\${spring.activemq.password}")
//    lateinit var password: String
//
//    @Bean
//    fun jacksonJmsMessageConverter(): MappingJackson2MessageConverter {
//        return MappingJackson2MessageConverter().apply {
//            this.setTargetType(MessageType.TEXT)
//            this.setTypeIdPropertyName("ActiveMQ")
//        }
//    }
//
//    @Bean
//    fun connectionFactory(): ActiveMQConnectionFactory {
//        return ActiveMQConnectionFactory(user, password, brokerUrl)
//    }
//
//    @Bean
//    fun jmsListenerContainerFactory(): DefaultJmsListenerContainerFactory {
//        return DefaultJmsListenerContainerFactory().apply {
//            this.setConnectionFactory(connectionFactory())
//            this.setMessageConverter(jacksonJmsMessageConverter())
//        }
//    }
//
//    @Bean
//    fun jmsMessageListener(): ExampleListener {
//        return ExampleListener()
//    }
//
//    override fun configureJmsListeners(registrar: JmsListenerEndpointRegistrar) {
//        val endpoint = SimpleJmsListenerEndpoint().apply {
//            messageListener = jmsMessageListener()
//            destination = "example.queue"
//            id = "example-queue"
//            subscription = "my-subscription"
//            concurrency = "1"
//        }
//        registrar.setContainerFactory(jmsListenerContainerFactory())
//        registrar.registerEndpoint(endpoint, jmsListenerContainerFactory())
//    }
//}
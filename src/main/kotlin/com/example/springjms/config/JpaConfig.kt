package com.example.springjms.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.persistence.EntityManagerFactory


@Configuration
@EnableTransactionManagement
class JpaConfig(@Autowired private val entityManagerFactory: EntityManagerFactory) {
    @Bean("jpaTM", "transactionManager")
    fun jpaTransactionManager(): JpaTransactionManager {
        return JpaTransactionManager(entityManagerFactory)
    }
}
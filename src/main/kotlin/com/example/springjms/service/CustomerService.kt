package com.example.springjms.service

import com.example.springjms.model.entity.Customer
import com.example.springjms.repository.CustomerRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CustomerService(private val customerRepository: CustomerRepository) {

    @Transactional(transactionManager = "jpaTM")
    fun createCustomer(name: String): Customer {
        val customerToSave = Customer().apply { this.name = name }
        return customerRepository.save(customerToSave)
    }
}
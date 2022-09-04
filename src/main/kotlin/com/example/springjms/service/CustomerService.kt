package com.example.springjms.service

import org.springframework.stereotype.Service

@Service
class CustomerRepository(private val customerRepository: CustomerRepository) {
}
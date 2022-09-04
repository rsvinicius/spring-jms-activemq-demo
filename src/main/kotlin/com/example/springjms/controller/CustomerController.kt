package com.example.springjms.controller

import com.example.springjms.service.CustomerService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/customers")
@Tag(name = "CustomerController", description = "Customer")
class CustomerController(private val customerService: CustomerService) {
    private val logger = KotlinLogging.logger {}

    @PostMapping("/{name}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create customer")
    fun createCustomer(@PathVariable name: String) {
        logger.info {
            "createCustomer: creating a new customer, name=$name"
        }
        customerService.createCustomer(name).also {
            logger.info {
                "createCustomer: customer created id=${it.id}, name=${it.name}"
            }
        }
    }
}
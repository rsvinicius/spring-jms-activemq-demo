package com.example.springjms.controller

import io.swagger.v3.oas.annotations.tags.Tag
import mu.KotlinLogging
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "UserController", description = "User")
class UserController {
    private val logger = KotlinLogging.logger {}
}
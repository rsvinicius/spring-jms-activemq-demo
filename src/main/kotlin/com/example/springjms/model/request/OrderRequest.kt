package com.example.springjms.model.request

import java.math.BigDecimal

class OrderRequest(
    val orderId: Long? = null,
    val customerId: Long? = null,
    val value: BigDecimal? = null
)
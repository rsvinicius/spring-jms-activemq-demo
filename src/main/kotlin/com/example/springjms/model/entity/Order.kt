package com.example.springjms.model.entity

import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "_order") // ORDER is a reserved keyword in SQL
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var customerId: Long? = null,

    @Column(name = "_value") // VALUE is a reserved keyword in SQL
    var value: BigDecimal? = null
)
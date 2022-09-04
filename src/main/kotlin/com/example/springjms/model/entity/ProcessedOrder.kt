package com.example.springjms.model.entity

import org.hibernate.annotations.CreationTimestamp
import java.util.Date
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.Temporal
import javax.persistence.TemporalType

@Entity
@Table
data class ProcessedOrder(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var orderId: Long? = null,

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    var processingDateTime: Date? = null
)
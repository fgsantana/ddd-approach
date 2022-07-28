package com.fgsantana.dddapproach.domain.event.product

import com.fgsantana.dddapproach.domain.entity.Product
import com.fgsantana.dddapproach.domain.event.Event
import com.fgsantana.dddapproach.domain.event.PRODUCT_CREATED_EVENT
import java.time.LocalDateTime

class ProductCreatedEvent(override val eventData: Product) : Event {
    override val eventTime: LocalDateTime= LocalDateTime.now()
    override val eventName: String = PRODUCT_CREATED_EVENT
}


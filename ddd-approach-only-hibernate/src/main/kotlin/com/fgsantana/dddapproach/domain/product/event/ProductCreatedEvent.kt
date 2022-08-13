package com.fgsantana.dddapproach.domain.product.event

import com.fgsantana.dddapproach.domain.product.entity.Product
import com.fgsantana.dddapproach.domain.shared.event.Event
import com.fgsantana.dddapproach.domain.shared.event.PRODUCT_CREATED_EVENT
import java.time.LocalDateTime

class ProductCreatedEvent(override val eventData: Product) : Event {
    override val eventTime: LocalDateTime= LocalDateTime.now()
    override val eventName: String = PRODUCT_CREATED_EVENT
}


package com.fgsantana.dddapproach.domain.product.event.handler

import com.fgsantana.dddapproach.domain.shared.event.EventHandler
import com.fgsantana.dddapproach.domain.product.event.ProductCreatedEvent

class SendEmailWhenProductIsCreatedHandler : EventHandler<ProductCreatedEvent> {
    override fun handle(event: ProductCreatedEvent) {
        println("ProductCreatedEvent: sending email")
    }
}
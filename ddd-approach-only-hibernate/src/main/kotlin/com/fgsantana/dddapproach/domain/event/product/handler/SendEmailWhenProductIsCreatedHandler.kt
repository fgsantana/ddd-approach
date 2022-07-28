package com.fgsantana.dddapproach.domain.event.product.handler

import com.fgsantana.dddapproach.domain.event.EventHandler
import com.fgsantana.dddapproach.domain.event.product.ProductCreatedEvent

class SendEmailWhenProductIsCreatedHandler : EventHandler<ProductCreatedEvent> {
    override fun handle(event: ProductCreatedEvent) {
        println("ProductCreatedEvent: sending email")
    }
}
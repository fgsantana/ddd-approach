package com.fgsantana.dddapproach.domain.customer.event.handler

import com.fgsantana.dddapproach.domain.shared.event.EventHandler
import com.fgsantana.dddapproach.domain.customer.event.CustomerCreatedEvent

open class PrintWhenCustomerIsCreatedHandler1 : EventHandler<CustomerCreatedEvent> {
    override fun handle(event: CustomerCreatedEvent) {
        println("Handler 2 - PrintWhenCustomerIsCreatedHandler2")
    }
}
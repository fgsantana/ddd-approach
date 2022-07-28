package com.fgsantana.dddapproach.domain.event.customer.handler

import com.fgsantana.dddapproach.domain.event.EventHandler
import com.fgsantana.dddapproach.domain.event.customer.event.CustomerCreatedEvent

open class PrintWhenCustomerIsCreatedHandler1 : EventHandler<CustomerCreatedEvent> {
    override fun handle(event: CustomerCreatedEvent) {
        println("Handler 2 - PrintWhenCustomerIsCreatedHandler2")
    }
}
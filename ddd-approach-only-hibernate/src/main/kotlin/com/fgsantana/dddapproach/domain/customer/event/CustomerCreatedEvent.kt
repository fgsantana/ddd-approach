package com.fgsantana.dddapproach.domain.customer.event

import com.fgsantana.dddapproach.domain.entity.Customer
import com.fgsantana.dddapproach.domain.shared.event.CUSTOMER_CREATED_EVENT
import com.fgsantana.dddapproach.domain.shared.event.Event
import java.time.LocalDateTime

class CustomerCreatedEvent(override val eventData: Customer) : Event {
    override val eventTime: LocalDateTime = LocalDateTime.now()
    override val eventName = CUSTOMER_CREATED_EVENT
}


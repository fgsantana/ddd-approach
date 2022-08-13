package com.fgsantana.dddapproach.domain.customer.event

import com.fgsantana.dddapproach.domain.entity.Address
import com.fgsantana.dddapproach.domain.entity.Customer
import com.fgsantana.dddapproach.domain.shared.event.CUSTOMER_ADDRESS_CHANGED_EVENT
import com.fgsantana.dddapproach.domain.shared.event.Event
import java.time.LocalDateTime

class CustomerAddressChangedEvent(override val eventData: Pair<Customer,Address>) : Event {
    override val eventTime: LocalDateTime = LocalDateTime.now()
    override val eventName: String = CUSTOMER_ADDRESS_CHANGED_EVENT
}
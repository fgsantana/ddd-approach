package com.fgsantana.dddapproach.domain.event.customer

import com.fgsantana.dddapproach.domain.entity.Address
import com.fgsantana.dddapproach.domain.entity.Customer
import com.fgsantana.dddapproach.domain.event.EventDispatcher
import com.fgsantana.dddapproach.domain.event.CUSTOMER_ADDRESS_CHANGED_EVENT
import com.fgsantana.dddapproach.domain.event.CUSTOMER_CREATED_EVENT
import com.fgsantana.dddapproach.domain.event.customer.event.CustomerAddressChangedEvent
import com.fgsantana.dddapproach.domain.event.customer.event.CustomerCreatedEvent
import com.fgsantana.dddapproach.domain.event.customer.handler.PrintWhenCustomerAddressIsChangedHandler
import com.fgsantana.dddapproach.domain.event.customer.handler.PrintWhenCustomerIsCreatedHandler1
import com.fgsantana.dddapproach.domain.service.CustomerService
import io.mockk.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


class CustomerEventsTests {

    @Test
    fun `should handle (handler1 and handler2) when customer creation event is sent`() {
        val creationEventHandler1 = spyk<PrintWhenCustomerIsCreatedHandler1>()
        val creationEventHandler2 = spyk<PrintWhenCustomerIsCreatedHandler1>()

        EventDispatcher.register(CUSTOMER_CREATED_EVENT, creationEventHandler1)
        EventDispatcher.register(CUSTOMER_CREATED_EVENT, creationEventHandler2)

        val event = CustomerCreatedEvent(Customer(132L, "Name"))
        EventDispatcher.notify(event)

        verify { creationEventHandler1.handle(event) }
        verify { creationEventHandler2.handle(event) }

    }

    @Test
    fun `should handle when customer address is changed`() {
        val handlerEventParameterCaptor = slot<CustomerAddressChangedEvent>()

        val handler = spyk<PrintWhenCustomerAddressIsChangedHandler>()
        every { handler.handle(capture(handlerEventParameterCaptor)) } returns Unit

        EventDispatcher.register(CUSTOMER_ADDRESS_CHANGED_EVENT, handler)

        val service = CustomerService()
        val customerBeforeChanges = Customer(321L, "Name")
        val customer = Customer(customerBeforeChanges.id, customerBeforeChanges.name)

        val address = Address("Street", 1245, "231983219", "City")

        service.changeCustomerAddress(customer, address)

        verify { handler.handle(any()) }
        Assertions.assertTrue(handlerEventParameterCaptor.captured.eventName == CUSTOMER_ADDRESS_CHANGED_EVENT)
        Assertions.assertTrue(handlerEventParameterCaptor.captured.eventData.first == customerBeforeChanges)
        Assertions.assertTrue(handlerEventParameterCaptor.captured.eventData.second == address)

    }
}
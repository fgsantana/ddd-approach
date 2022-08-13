package com.fgsantana.dddapproach.domain.customer.service

import com.fgsantana.dddapproach.domain.entity.Address
import com.fgsantana.dddapproach.domain.entity.Customer
import com.fgsantana.dddapproach.domain.shared.event.EventDispatcher
import com.fgsantana.dddapproach.domain.customer.event.CustomerAddressChangedEvent


class CustomerService {

    fun changeCustomerAddress(customer: Customer, address: Address){
        val eventPair = Pair(Customer(customer.id,customer.name, customer.address),address)
        customer.changeAddress(address)
        val event = CustomerAddressChangedEvent(eventPair)
        EventDispatcher.notify(event)

    }
}
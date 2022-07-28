package com.fgsantana.dddapproach.domain.event.customer.handler

import com.fgsantana.dddapproach.domain.entity.Address
import com.fgsantana.dddapproach.domain.entity.Customer
import com.fgsantana.dddapproach.domain.event.EventHandler
import com.fgsantana.dddapproach.domain.event.customer.event.CustomerAddressChangedEvent

class PrintWhenCustomerAddressIsChangedHandler : EventHandler<CustomerAddressChangedEvent>{
    override fun handle(event: CustomerAddressChangedEvent) {
        val customerAddressPair = event.eventData as Pair<Customer, Address>
        val newAddress = customerAddressPair.first
        val customer = customerAddressPair.second
        println("Customer $customer changed address to $newAddress" )
    }

}

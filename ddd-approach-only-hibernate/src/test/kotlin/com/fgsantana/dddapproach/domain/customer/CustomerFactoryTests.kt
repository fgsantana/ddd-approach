package com.fgsantana.dddapproach.domain.customer

import com.fgsantana.dddapproach.domain.customer.factory.CustomerFactory
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CustomerFactoryTests {

    @Test
    fun testIfCustomerIsCreated(){
        val customer = CustomerFactory.create(213,"Name")
        assertEquals(customer.id,213)
        assertEquals(customer.name,"Name")
        assertEquals(customer.address,null)
    }

    @Test
    fun testIfCustomerIsCreatedWithAnAddress(){
        val customer = CustomerFactory.create(213,"Name", "Street", 3145, "321321",  "City")
        assertEquals(customer.id,213)
        assertEquals(customer.name,"Name")
        Assertions.assertAll( { -> assertEquals(customer.address!!.street,"Street") }, { -> assertEquals(customer.address!!.number,3145) },
                              { -> assertEquals(customer.address!!.zip,"321321") }, { -> assertEquals(customer.address!!.city,"City") })
    }
}
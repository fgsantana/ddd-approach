package com.fgsantana.dddapproach.domain.customer.factory

import com.fgsantana.dddapproach.domain.entity.Address
import com.fgsantana.dddapproach.domain.entity.Customer

object CustomerFactory {
    fun create(id: Long, name: String) = Customer(id,name)

    fun create(id: Long, name: String, street: String, number: Int?, zip: String, city: String) =  Customer(id,name,Address(street,number,zip,city))

}
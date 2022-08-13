package com.fgsantana.dddapproach.domain.checkout

import com.fgsantana.dddapproach.domain.checkout.entity.Order
import com.fgsantana.dddapproach.domain.checkout.entity.OrderItem
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class OrderTests {

    @Test
    fun testIfThrowAnErrorIfIdIsNotPresent(){
        val items = arrayListOf(OrderItem(132,"Item1", 29.99,13,1), OrderItem(2,"134", 39.99,14,1))
        Assertions.assertThrows(RuntimeException::class.java, { -> Order(null, 1, items) })
    }
    @Test
    fun testIfThrowAnErrorIfCustomerIdIsNotPresent(){
        val items = arrayListOf(OrderItem(132,"Item1", 29.99,13,1), OrderItem(2,"134", 39.99, 14,1))
        Assertions.assertThrows(RuntimeException::class.java, { -> Order(1, null, items) })
    }

    @Test
    fun testIfThrowAnErrorIfItemsListAreEmpty(){
        Assertions.assertThrows(RuntimeException::class.java, { -> Order(1, 1, arrayListOf()) })
    }

    @Test
    fun testIfThrowAnErrorIfPriceIsIsNotGreaterThanZero(){
        Assertions.assertThrows(RuntimeException::class.java, { ->
            val item = OrderItem(132,"Item1", 0.0,13,1)
            Order(1, 1, arrayListOf(item)) })
    }

    @Test
    fun testIfThrowAnErrorIfQuantityIsNotGreaterThanZero(){
        Assertions.assertThrows(RuntimeException::class.java, { ->
            val item = OrderItem(132,"Item1", 0.0,13,1)

            Order(1, 1, arrayListOf(item))
        })
    }



    @Test
    fun testOrderTotalPrice(){
        val items = arrayListOf(OrderItem(132,"Item1", 29.99, 13,2), OrderItem(2,"134", 39.99, 14,3))
        val order = Order(1, 1, items)
        val total = order.total()


        Assertions.assertEquals(total, 179.95)

    }
}
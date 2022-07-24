package com.fgsantana.dddapproach.domain.service

import com.fgsantana.dddapproach.domain.entity.Customer
import com.fgsantana.dddapproach.domain.entity.Order
import com.fgsantana.dddapproach.domain.entity.OrderItem
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class OrderServiceTests {

    @Autowired
    lateinit var service : OrderService

    @Test
    fun contextLoads() {
    }

    @Test
    fun testOrdersTotalPrice(){
        val orderItems1 = arrayListOf(OrderItem(1,"132", 29.99,13,1), OrderItem(2,"134", 39.99, 14,1))
        val orderItems2 = arrayListOf(OrderItem(1,"132", 59.99,17,2))

        val order1 = Order(1, 1, orderItems1)
        val order2 = Order(2, 1, orderItems2)

        val total  = service.allOrdersTotal(arrayOf(order1,order2))

        Assertions.assertEquals(total,189.96)

    }


    @Test
    fun testRewardPointsOnOrder(){
        val customer = Customer(5,"Name")


        val orderItems = arrayListOf(OrderItem(1,"132", 350.00,17,3))

        val order =  service.placeOrder(customer, orderItems );

        Assertions.assertEquals(order.total(), 1050.0)
        Assertions.assertEquals(customer.rewardPoints, 525.0)
    }

    @Test
    fun testIfThrowsAnErrorWhenTryingToCreateAnOrderWithEmptyOrderItemsList(){
        val customer = Customer(5,"Name")


        val orderItems: ArrayList<OrderItem> = arrayListOf()

        Assertions.assertThrows(RuntimeException::class.java, {-> service.placeOrder(customer,orderItems)} );
    }
}
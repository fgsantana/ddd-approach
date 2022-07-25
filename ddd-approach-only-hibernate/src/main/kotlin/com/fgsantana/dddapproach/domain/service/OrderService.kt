package com.fgsantana.dddapproach.domain.service

import com.fgsantana.dddapproach.domain.entity.Customer
import com.fgsantana.dddapproach.domain.entity.Order
import com.fgsantana.dddapproach.domain.entity.OrderItem

class OrderService {

    fun allOrdersTotal(orders: Array<Order>) : Double{
        var total: Double = 0.0;
         orders.forEach { order ->
             total += order.total()
         }
        return total;

    }

    fun placeOrder(customer: Customer, orderItems: ArrayList<OrderItem>): Order {
        val order = Order (23583917, customer.id, orderItems)
        rewardPoints(customer, order.total());
        return order;
    }

    private fun rewardPoints(customer: Customer, total: Double) {
        val points = total / 2;
            customer.addRewardPoints(points);
    }

}
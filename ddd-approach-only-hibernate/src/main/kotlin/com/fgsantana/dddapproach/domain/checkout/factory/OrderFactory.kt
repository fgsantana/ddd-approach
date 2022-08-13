package com.fgsantana.dddapproach.domain.checkout.factory

import com.fgsantana.dddapproach.domain.checkout.entity.Order
import com.fgsantana.dddapproach.domain.checkout.entity.OrderItem

object OrderFactory {
    fun create(orderProps: OrderFactoryProps): Order {
        val orderItems = orderProps.items.map {
                facPropItem ->
                            OrderItem(facPropItem.id,facPropItem.name,facPropItem.price,facPropItem.productId,facPropItem.quantity) } as MutableList<OrderItem>

        return Order(orderProps.id, orderProps.customerId,orderItems)
    }
}


data class OrderFactoryProps(val id: Long, val customerId: Long, val items: List<OrderFactoryItemProps>)

data class OrderFactoryItemProps(val id: Long, val name: String, val productId: Long, val quantity: Int, val price: Double)
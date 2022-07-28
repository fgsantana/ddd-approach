package com.fgsantana.dddapproach.domain.repository

import com.fgsantana.dddapproach.domain.entity.Order
import com.fgsantana.dddapproach.domain.entity.OrderItem

interface OrderRepositoryInterface : RepositoryInterface<Order> {
    fun addItemsToOrder(items: List<OrderItem>, order: Order)
}
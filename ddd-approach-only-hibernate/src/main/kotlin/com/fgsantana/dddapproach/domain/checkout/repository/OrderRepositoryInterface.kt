package com.fgsantana.dddapproach.domain.checkout.repository

import com.fgsantana.dddapproach.domain.checkout.entity.Order
import com.fgsantana.dddapproach.domain.checkout.entity.OrderItem
import com.fgsantana.dddapproach.domain.shared.repository.RepositoryInterface

interface OrderRepositoryInterface : RepositoryInterface<Order> {
    fun addItemsToOrder(items: List<OrderItem>, order: Order)
}
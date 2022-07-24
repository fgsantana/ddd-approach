package com.fgsantana.dddapproach.infrastructure.db.hibernate.model


import javax.persistence.Id
import javax.persistence.Entity
import javax.persistence.Column
import javax.persistence.ManyToOne
import javax.persistence.FetchType
@Entity(name = "customer_order")
data class OrderModel(
    @Id
    var id: Long? = null,

    @Column
    var customerId: Long?,

) {}


@Entity(name = "customer_order_item")
data class OrderItemModel(
    @Id
    var id: Long? = null,

    @Column
    var name: String,

    @Column
    var price: Double,

    var product: Long,

    @Column
    var quantity: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    var order: OrderModel

){}
package com.fgsantana.dddapproach.infrastructure.db.hibernate.model


import javax.persistence.*

@Entity(name = "customer_order")
data class OrderModel(
    @Id
    var id: Long? = null,

    @Column
    var customerId: Long?,

    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER)
    var orders: List<OrderItemModel> = ArrayList()
)

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

){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OrderItemModel

        if (id != other.id) return false
        if (name != other.name) return false
        if (price != other.price) return false
        if (product != other.product) return false
        if (quantity != other.quantity) return false
        if (order.id != other.order.id) return false

        return true
    }

}
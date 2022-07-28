package com.fgsantana.dddapproach.infrastructure.db.hibernate.model


import javax.persistence.*

@Entity(name = "customer_order")
data class OrderModel(
    @Id
    var id: Long? = null,

    @Column
    var customerId: Long?,

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    var items: List<OrderItemModel> = ArrayList()

) {
    override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as OrderModel

    if (id != other.id) return false
    if (customerId != other.customerId) return false
    if(other.items.size != items.size) return false
    if (!items.containsAll(other.items)) return false

    return true
}}


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
    var order: OrderModel? = null

){}
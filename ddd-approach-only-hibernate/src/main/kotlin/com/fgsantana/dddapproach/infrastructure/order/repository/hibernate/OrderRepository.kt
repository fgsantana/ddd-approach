package com.fgsantana.dddapproach.infrastructure.order.repository.hibernate

import com.fgsantana.dddapproach.domain.checkout.entity.Order
import com.fgsantana.dddapproach.domain.checkout.entity.OrderItem
import com.fgsantana.dddapproach.domain.checkout.repository.OrderRepositoryInterface
import com.fgsantana.dddapproach.exception.NotFoundException
import javax.persistence.EntityManager
import javax.persistence.Persistence

class OrderRepository : OrderRepositoryInterface {
    val em: EntityManager =
        Persistence.createEntityManagerFactory("com.fgsantana.dddapproach").createEntityManager()

    override fun save(t: Order) {
        val orderToSave = OrderModel(t.id, t.customerId)
        val itemsToSave: List<OrderItemModel> =
            if(t.items.isNotEmpty())
                t.items.map { item -> OrderItemModel(item.id, item.name, item.price, item.productId, item.quantity, orderToSave) }
            else
                arrayListOf()

        em.transaction.begin()
        em.persist(orderToSave)
        itemsToSave.forEach { item -> em.persist(item)}
        em.transaction.commit()

    }

    override fun addItemsToOrder(items: List<OrderItem>, order: Order) {
        val orderToSave = OrderModel(order.id, order.customerId)
        val itemsToSave = items.map{ item -> OrderItemModel(item.id, item.name, item.price, item.productId, item.quantity, orderToSave) }
        em.transaction.begin()
        itemsToSave.forEach { item-> em.persist(item)}
        em.transaction.commit()

    }

    override fun findById(id: Long): Order {
        val orderFromDb = em.find(OrderModel::class.java, id)
        ?: throw NotFoundException("Order with id $id not found!")
        return  modelToEntity(orderFromDb)
    }

    override fun findAll(): List<Order> {
        val ordersFromDb = em.createQuery("select co from customer_order co", OrderModel::class.java).resultList
        return ordersFromDb.map{ order -> modelToEntity(order) }
    }

    override fun update(t: Order) {
        val orderToUpdate = OrderModel(t.id,t.customerId)
        em.transaction.begin()
        em.merge(orderToUpdate)
        t.items.forEach { item -> em.merge(OrderItemModel(item.id,item.name,item.price,item.productId,item.quantity, orderToUpdate )) }
        em.transaction.commit()
    }

    override fun deleteById(id: Long) {
        em.transaction.begin()
        em.createQuery("delete from customer_order_item where order.id=:id").setParameter("id",id).executeUpdate()
        em.createQuery("delete from customer_order where id=:id").setParameter("id",id).executeUpdate()
        em.transaction.commit()

    }

    private fun modelToEntity(orderFromDb: OrderModel) : Order = Order(orderFromDb.id,orderFromDb.customerId,
        orderFromDb.items.map { item -> OrderItem(item.id!!,item.name,item.price,item.product,item.quantity) } as MutableList)

}


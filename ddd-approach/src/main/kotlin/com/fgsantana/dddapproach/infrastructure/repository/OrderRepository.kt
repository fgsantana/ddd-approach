package com.fgsantana.dddapproach.infrastructure.repository

import com.fgsantana.dddapproach.domain.entity.Order
import com.fgsantana.dddapproach.domain.entity.OrderItem
import com.fgsantana.dddapproach.domain.repository.OrderRepositoryInterface
import com.fgsantana.dddapproach.infrastructure.db.hibernate.model.OrderItemModel
import com.fgsantana.dddapproach.infrastructure.db.hibernate.model.OrderModel
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Component
class OrderRepository(val orderRepo: OrderORMRepository, val itemRepo: OrderItemORMRepository) : OrderRepositoryInterface {
    override fun save(t: Order) {
        val orderToSave = OrderModel(t.id, t.customerId);
        orderRepo.save(orderToSave);


        if(t.items.isNotEmpty()){
            val itemsToSave = t.items.map { item -> OrderItemModel(item.id, item.name, item.price, item.productId, item.quantity, orderToSave) }
            itemRepo.saveAll(itemsToSave)
            }

    }

    override fun addItemsToOrder(items: List<OrderItem>, order: Order) {
        itemRepo.saveAll(items.map { item -> OrderItemModel(item.id,item.name,item.price,item.productId,item.quantity, OrderModel(order.id,order.customerId)) })
    }

    override fun findById(id: Long): Order {
        val orderFromDb = orderRepo.findById(id).orElseThrow { RuntimeException("Order with this id not found!") }
        val orderItems = orderItemsToReturn(id)

        return Order(orderFromDb.id,orderFromDb.customerId,orderItems)
    }

    override fun findAll(): List<Order> =  orderRepo.findAll().map {
            orderFromDb -> Order(orderFromDb.id,orderFromDb.customerId,orderItemsToReturn(orderFromDb.id!!))}



    override fun update(t: Order) {
        val orderToSave = OrderModel(t.id, t.customerId);
        orderRepo.save(orderToSave)
        val itemsToSave = t.items.map {
                item -> OrderItemModel(item.id, item.name, item.price, item.productId, item.quantity, orderToSave) }
        itemRepo.saveAll(itemsToSave)
    }

    override fun deleteById(id: Long) {
        itemRepo.deleteAllItemsFromOrder(id)
        orderRepo.deleteById(id)
    }

    private fun orderItemsToReturn(id: Long) =
        itemRepo.getAllItemsFromOrder(id).map { orderItem -> OrderItem(orderItem.id!!,orderItem.name,orderItem.price,orderItem.product, orderItem.quantity)}
            .toMutableList() }


@Repository
interface OrderORMRepository : CrudRepository<OrderModel, Long> {}

@Repository
interface OrderItemORMRepository : CrudRepository<OrderItemModel, Long> {

    @Query("select o from customer_order_item o where o.order.id=?1")
    fun getAllItemsFromOrder(orderId: Long) : List<OrderItemModel>

    @Transactional
    @Modifying
    @Query("delete from customer_order_item o where o.order.id=?1")
    fun deleteAllItemsFromOrder(orderId: Long)
}


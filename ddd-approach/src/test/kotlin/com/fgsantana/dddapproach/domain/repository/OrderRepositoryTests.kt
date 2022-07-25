package com.fgsantana.dddapproach.domain.repository

import com.fgsantana.dddapproach.domain.entity.Order
import com.fgsantana.dddapproach.domain.entity.OrderItem
import com.fgsantana.dddapproach.infrastructure.db.hibernate.model.OrderItemModel
import com.fgsantana.dddapproach.infrastructure.db.hibernate.model.OrderModel
import com.fgsantana.dddapproach.infrastructure.repository.OrderItemORMRepository
import com.fgsantana.dddapproach.infrastructure.repository.OrderORMRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
class OrderRepositoryTests {
    @Autowired
    lateinit var repo: OrderRepositoryInterface

    @Autowired
    lateinit var orderRepoORM: OrderORMRepository

    @Autowired
    lateinit var itemRepoORM: OrderItemORMRepository

    @BeforeEach
    fun beforeEach() {
        itemRepoORM.deleteAll()
        orderRepoORM.deleteAll()
    }

    @Test
    fun contextLoads() {}

    @Test
    fun testIfOrderIsCreated() {
        val orderItems = arrayListOf(
            OrderItem(256, "Order item", 89.85,251,1),
            OrderItem(321, "Order item1", 99.85,312,2));

        val order = Order(12, 314, orderItems)
        repo.save(order)

        val orderFromDbOp: Optional<OrderModel> = orderRepoORM.findById(12)
        Assertions.assertTrue(orderFromDbOp.isPresent)

        val orderFromdb = orderFromDbOp.get();
        Assertions.assertTrue(assertModelEqualsToEntity(orderFromdb,order))

        val orderItemsFromDb = itemRepoORM.getAllItemsFromOrder(12)
        Assertions.assertTrue(orderItemsFromDb.isNotEmpty())

        Assertions.assertTrue(assertModelItemsEqualsToEntity(orderItemsFromDb,order.items))

    }

    @Test
    fun testIfOrderIsUpdated() {
        val orderItems = mutableListOf(
            OrderItem(256, "Order item", 89.85,251,1),
            OrderItem(321, "Order item1", 99.85,312,2));
        val order = Order(12, 321, orderItems)
        repo.save(order)

        val orderFromDbOp: Optional<OrderModel> = orderRepoORM.findById(12)
        Assertions.assertTrue(orderFromDbOp.isPresent)
        val orderFromDb = orderFromDbOp.get();
        Assertions.assertTrue(assertModelEqualsToEntity(orderFromDb,order))

        val orderItemsFromDb = itemRepoORM.getAllItemsFromOrder(12)
        Assertions.assertTrue(orderItemsFromDb.isNotEmpty())

        val orderItemsUpdated = orderItemsFromDb.map { itemFromDb ->
            if (itemFromDb.id==256L) OrderItem(itemFromDb.id!!, "Order item updated", 95.99, 232, 2)
            else OrderItem(itemFromDb.id!!,itemFromDb.name,itemFromDb.price,itemFromDb.product,itemFromDb.quantity)}.toMutableList()
        order.items = orderItemsUpdated
        order.customerId=432
        repo.update(order)

        val orderFromDbUpdatedOp: Optional<OrderModel> = orderRepoORM.findById(12)
        Assertions.assertTrue(orderFromDbUpdatedOp.isPresent)
        val orderFromDbUpdated = orderFromDbUpdatedOp.get();

        Assertions.assertTrue(assertModelEqualsToEntity(orderFromDbUpdated,order))

        val orderItemsFromDbUpdated = itemRepoORM.getAllItemsFromOrder(12)
        Assertions.assertTrue(orderItemsFromDbUpdated.isNotEmpty())

        Assertions.assertTrue(assertModelItemsEqualsToEntity(orderItemsFromDbUpdated,order.items))

    }

    @Test
    fun testIfItemsAreAddedToOrder(){
        val orderItem = OrderItem(256, "Order item", 89.85,251,1)
        val order = Order(12, 321, mutableListOf(orderItem))
        repo.save(order)

        val orderFromDbOp: Optional<OrderModel> = orderRepoORM.findById(12)

        Assertions.assertTrue(orderFromDbOp.isPresent)
        val orderFromDb = orderFromDbOp.get();
        Assertions.assertTrue(assertModelEqualsToEntity(orderFromDb,order))

        val orderItems = mutableListOf(
            OrderItem(258, "Order item1", 84.85,675,4),
            OrderItem(321, "Order item2", 99.85,312,2));

        repo.addItemsToOrder(orderItems, order)

        val orderItemsFromDbAfterAdd = itemRepoORM.getAllItemsFromOrder(12)
        Assertions.assertTrue(orderItemsFromDbAfterAdd.containsAll(orderItems.map
        { orderItem ->
            OrderItemModel(orderItem.id,orderItem.name,orderItem.price,orderItem.productId,orderItem.quantity,OrderModel(12,321)) }))

    }

    @Test
    fun testIfOrderIsFound() {
        val orderItems = mutableListOf(
            OrderItem(256, "Order item", 89.85,251,1),
            OrderItem(321, "Order item1", 99.85,312,2));
        val order = Order(12, 312, orderItems)
        repo.save(order)

        Assertions.assertDoesNotThrow { -> repo.findById(12) }

        val orderFromRepo = repo.findById(12)
        Assertions.assertTrue(orderFromRepo == order)

    }

    @Test
    fun testIfThrowsAnErrorWhenorderIsNotFound() {
        Assertions.assertThrows(RuntimeException::class.java, {repo.findById(432)})
    }

    @Test
    fun testIfAllordersAreFound() {
        val orderItems = mutableListOf(
            OrderItem(256, "Order item", 89.85,251,1),
            OrderItem(321, "Order item1", 99.85,312,2));
        val order = Order(12, 321, orderItems)

        val orderItems2 = mutableListOf(
            OrderItem(254, "Order1 item", 85.45,585,3),
            OrderItem(258, "Order1 item1", 102.85,154,1));
        val order2 = Order(15, 354, orderItems2)

        val orders = listOf(order,order2)
        repo.save(order)
        repo.save(order2)


        val ordersFromRepo = repo.findAll();
        Assertions.assertTrue(ordersFromRepo.size == 2 )
        Assertions.assertTrue(orders.all{ order -> ordersFromRepo.contains(order)})

    }

    @Test
    fun testIfOrderIsDeleted() {
        val orderItems = arrayListOf(
            OrderItem(256, "Order item", 89.85,251,1),
            OrderItem(321, "Order item1", 99.85,312,2));

        val order = Order(12, 314, orderItems)
        repo.save(order)

        val orderFromDbOp: Optional<OrderModel> = orderRepoORM.findById(12)
        Assertions.assertTrue(orderFromDbOp.isPresent)

        val orderFromdb = orderFromDbOp.get();
        Assertions.assertTrue(assertModelEqualsToEntity(orderFromdb,order))

        repo.deleteById(12)

        val opAfterDeleted: Optional<OrderModel> = orderRepoORM.findById(12)

        Assertions.assertTrue(opAfterDeleted.isEmpty)


    }

    private fun assertModelItemsEqualsToEntity(orderItemsFromDb: List<OrderItemModel>, orderItems: List<OrderItem>): Boolean =
        orderItems.all { orderItem ->
            orderItemsFromDb.any { orderItemModel -> assertModelItemEqualsToEntity(orderItem, orderItemModel) } }


    private fun assertModelItemEqualsToEntity(orderItem: OrderItem, orderItemModel: OrderItemModel) : Boolean{
            return orderItem.id == orderItemModel.id &&
                    orderItem.name == orderItemModel.name &&
                    orderItem.price== orderItemModel.price &&
                    orderItem.productId == orderItemModel.product &&
                    orderItem.quantity == orderItemModel.quantity
    }

    private fun assertModelEqualsToEntity(orderFromdb: OrderModel, order: Order) : Boolean{
        return orderFromdb.id == order.id && orderFromdb.customerId == order.customerId


    }

}
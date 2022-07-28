package com.fgsantana.dddapproach.domain.repository

import com.fgsantana.dddapproach.domain.entity.Order
import com.fgsantana.dddapproach.domain.entity.OrderItem
import com.fgsantana.dddapproach.exception.NotFoundException
import com.fgsantana.dddapproach.infrastructure.db.hibernate.model.OrderItemModel
import com.fgsantana.dddapproach.infrastructure.db.hibernate.model.OrderModel
import com.fgsantana.dddapproach.infrastructure.repository.OrderRepository
import org.junit.jupiter.api.*
import javax.persistence.EntityManager
import javax.persistence.Persistence
import javax.persistence.PersistenceContext


class OrderRepositoryTests {

    @PersistenceContext
    private val em: EntityManager =
        Persistence.createEntityManagerFactory("com.fgsantana.dddapproach").createEntityManager()

    private val repo =  OrderRepository()

    @BeforeEach
    fun beforeEach() {
        em.transaction.begin()
        em.createQuery("delete from customer_order_item").executeUpdate()
        em.createQuery("delete from customer_order").executeUpdate()
        em.transaction.commit()
        em.clear();
    }

    @Test
    fun testIfOrderIsCreated() {
        val orderItems = arrayListOf(
            OrderItem(256, "Order item", 89.85,251,1),
            OrderItem(321, "Order item1", 99.85,312,2));

        val order = Order(12, 314, orderItems)
        repo.save(order)

        val orderFromDb = em.find(OrderModel::class.java,12L)
        Assertions.assertNotNull(orderFromDb)

        Assertions.assertTrue(assertModelEqualsToEntity(orderFromDb,order))

        val orderItemsFromDb = orderFromDb.items
        Assertions.assertTrue(orderItemsFromDb.isNotEmpty())
        Assertions.assertTrue(assertModelItemsEqualsToEntity(orderItemsFromDb,order.items))
    }

    @Test
    fun testIfOrderIsUpdated() {
        val orderItems = arrayListOf(
            OrderItem(256, "Order item", 89.85,251,1),
            OrderItem(321, "Order item1", 99.85,312,2));
        val order = Order(12, 321, orderItems)

        givenOrderIsInDatabase(order)
        em.clear()


        val orderItemsUpdated = orderItems.map { itemFromDb ->
            OrderItem(itemFromDb.id!!,itemFromDb.name,itemFromDb.price,itemFromDb.productId,itemFromDb.quantity)}.toMutableList()
        order.items = orderItemsUpdated
        order.customerId=432
        repo.update(order)

        val orderFromDbUpdated = em.find(OrderModel::class.java,12L)
        Assertions.assertTrue(orderFromDbUpdated != null)

        Assertions.assertTrue(assertModelEqualsToEntity(orderFromDbUpdated,order))

        val orderItemsFromDbUpdated = orderFromDbUpdated.items
        Assertions.assertTrue(orderItemsFromDbUpdated.isNotEmpty())

        Assertions.assertTrue(assertModelItemsEqualsToEntity(orderItemsFromDbUpdated,order.items))

    }

    @Test
    fun testIfItemsAreAddedToOrder(){
        val orderItems = arrayListOf(
            OrderItem(321, "Order item1", 99.85,312,2))
        val order = Order(12, 321, orderItems)

        givenOrderIsInDatabase(order)
        em.clear()

        val orderItemsToAdd = mutableListOf(
            OrderItem(258, "Order item2", 84.85,675,4),
            OrderItem(353, "Order item3", 99.85,543,5));

        repo.addItemsToOrder(orderItemsToAdd, order)

        val orderItemFromDbAfterAdd = em.find(OrderModel::class.java, 12L)
        val orderItemsFromDbAfterAdd = orderItemFromDbAfterAdd.items
        Assertions.assertTrue(orderItemsToAdd.all { item -> orderItemsFromDbAfterAdd.contains(
            OrderItemModel(item.id,item.name,item.price,item.productId,item.quantity,orderItemFromDbAfterAdd))})

    }

    @Test
    fun testIfOrderIsFound() {
        val orderItems = arrayListOf(
            OrderItem(321, "Order item1", 99.85,312,2))
        val order = Order(12, 321, orderItems)

        givenOrderIsInDatabase(order)
        em.clear()


        Assertions.assertDoesNotThrow { -> repo.findById(12) }


        val orderFromRepo = repo.findById(12)
        val orderFromDb   = em.find(OrderModel::class.java, 12L)
        Assertions.assertTrue(assertModelEqualsToEntity(orderFromDb,orderFromRepo))

    }

    @Test
    fun testIfThrowsAnErrorWhenorderIsNotFound() {
        Assertions.assertThrows(NotFoundException::class.java, { -> repo.findById(432) })

    }

    @Test
    fun testIfAllOrdersAreFound() {
        val orderItems = mutableListOf(
            OrderItem(256, "Order item", 89.85,251,1),
            OrderItem(321, "Order item1", 99.85,312,2));
        val order = Order(12, 321, orderItems)

        val orderItems2 = mutableListOf(
            OrderItem(254, "Order1 item", 85.45,585,3),
            OrderItem(258, "Order1 item1", 102.85,154,1));
        val order2 = Order(15, 354, orderItems2)


        givenOrderIsInDatabase(order)
        givenOrderIsInDatabase(order2)
        em.clear()

        val ordersFromRepo = repo.findAll();
        val ordersFromDb = em.createQuery("select co from customer_order co", OrderModel::class.java).resultList
        Assertions.assertTrue(ordersFromRepo.size == 2 )
        Assertions.assertTrue(ordersFromRepo.containsAll(ordersFromDb.map(this::modelToEntity)))

    }

    @Test
    fun testIfOrderIsDeleted() {
        val orderItems = arrayListOf(
            OrderItem(256, "Order item", 89.85,251,1),
            OrderItem(321, "Order item1", 99.85,312,2));

        val order = Order(12, 314, orderItems)
        givenOrderIsInDatabase(order)
        em.clear()

        repo.deleteById(12)

        val afterDelete= em.find(OrderModel::class.java,12L)

        Assertions.assertNull(afterDelete)

    }


    private fun givenOrderIsInDatabase(order: Order) {
        val orderToSave = OrderModel(order.id, order.customerId)
        val orderItems = order.items
        em.transaction.begin()
        em.persist(orderToSave)
        orderItems.forEach { item -> em.persist(OrderItemModel(item.id, item.name,item.price,item.productId,item.quantity,orderToSave)) }
        em.transaction.commit()
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
        return orderFromdb.id == order.id && orderFromdb.customerId == order.customerId &&
                assertModelItemsEqualsToEntity(orderFromdb.items,order.items)


    }

    private fun modelToEntity(orderFromDb: OrderModel) : Order = Order(orderFromDb.id,orderFromDb.customerId,
        orderFromDb.items.map { item -> OrderItem(item.id!!,item.name,item.price,item.product,item.quantity) } as MutableList)
}
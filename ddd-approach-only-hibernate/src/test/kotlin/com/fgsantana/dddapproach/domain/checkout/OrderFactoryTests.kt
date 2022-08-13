package com.fgsantana.dddapproach.domain.checkout

import com.fgsantana.dddapproach.domain.checkout.factory.OrderFactory
import com.fgsantana.dddapproach.domain.checkout.factory.OrderFactoryItemProps
import com.fgsantana.dddapproach.domain.checkout.factory.OrderFactoryProps
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class OrderFactoryTests {


    @Test
    fun testIfOrderIsCreated() {
        val orderProps = OrderFactoryProps(
            12321, 321,
            listOf(
                OrderFactoryItemProps(321, "Order item1", 3421, 2, 98.0),
                OrderFactoryItemProps(3435, "Order item2", 3541, 1, 79.0)
            )
        )
        val order = OrderFactory.create(orderProps)

        Assertions.assertAll(
            { -> assertEquals(order.id,12321) },
            { -> assertEquals(order.customerId,321) },

            { -> assertEquals(order.items[0].id,321)},
            { -> assertEquals(order.items[0].name,"Order item1")},
            { -> assertEquals(order.items[0].productId,3421)},
            { -> assertEquals(order.items[0].quantity,2)},
            { -> assertEquals(order.items[0].price,98.0)},

            { -> assertEquals(order.items[1].id,3435)},
            { -> assertEquals(order.items[1].name,"Order item2")},
            { -> assertEquals(order.items[1].productId,3541)},
            { -> assertEquals(order.items[1].quantity,1)},
            { -> assertEquals(order.items[1].price,79.0)}
            )

    }
}
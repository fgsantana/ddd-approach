package com.fgsantana.dddapproach.domain.shared.event

import com.fgsantana.dddapproach.domain.product.entity.Product
import com.fgsantana.dddapproach.domain.product.event.ProductCreatedEvent
import com.fgsantana.dddapproach.domain.product.event.handler.SendEmailWhenProductIsCreatedHandler
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class EventDispatcherTests {

    @BeforeEach
    fun beforeEach(){
        EventDispatcher.unregisterAll()
    }


    @Test
    fun `should register an event handler`() {
        val eventHandler = SendEmailWhenProductIsCreatedHandler()
        EventDispatcher.register(PRODUCT_CREATED_EVENT, eventHandler)

        assertNotNull(EventDispatcher.getEventHandlers()[PRODUCT_CREATED_EVENT])
        assertTrue(EventDispatcher.getEventHandlers().size == 1)
        assertEquals(EventDispatcher.getEventHandlers()[PRODUCT_CREATED_EVENT]!![0] , eventHandler)


    }

    @Test
    fun `should unregister an event handler`() {
        val eventHandler = SendEmailWhenProductIsCreatedHandler()
        EventDispatcher.register(PRODUCT_CREATED_EVENT, eventHandler)

        assertNotNull(EventDispatcher.getEventHandlers()[PRODUCT_CREATED_EVENT])
        assertTrue(EventDispatcher.getEventHandlers().size == 1)
        assertEquals(EventDispatcher.getEventHandlers()[PRODUCT_CREATED_EVENT]!![0] , eventHandler)

        EventDispatcher.unregister(PRODUCT_CREATED_EVENT,eventHandler)

        assertFalse(EventDispatcher.getEventHandlers()[PRODUCT_CREATED_EVENT]!!.contains(eventHandler))


    }

    @Test
    fun `should unregisters all event handlers`() {
        val eventHandler = SendEmailWhenProductIsCreatedHandler()
        EventDispatcher.register(PRODUCT_CREATED_EVENT, eventHandler)

        assertNotNull(EventDispatcher.getEventHandlers()[PRODUCT_CREATED_EVENT])
        assertTrue(EventDispatcher.getEventHandlers().size == 1)
        assertEquals(EventDispatcher.getEventHandlers()[PRODUCT_CREATED_EVENT]!![0] , eventHandler)

        EventDispatcher.unregisterAll()

        assertFalse(EventDispatcher.getEventHandlers()[PRODUCT_CREATED_EVENT]!!.contains(eventHandler))

    }

    @Test
    fun `should notify all event handlers`() {
        val eventHandler = spyk<SendEmailWhenProductIsCreatedHandler>()
        val event = ProductCreatedEvent( Product(12L,"Product name",99.99))
        EventDispatcher.register(PRODUCT_CREATED_EVENT, eventHandler)

        assertNotNull(EventDispatcher.getEventHandlers()[PRODUCT_CREATED_EVENT])
        assertTrue(EventDispatcher.getEventHandlers().size == 1)
        assertEquals(EventDispatcher.getEventHandlers()[PRODUCT_CREATED_EVENT]!![0] , eventHandler)

        EventDispatcher.notify(event)

        verify { eventHandler.handle(event)}

    }
}
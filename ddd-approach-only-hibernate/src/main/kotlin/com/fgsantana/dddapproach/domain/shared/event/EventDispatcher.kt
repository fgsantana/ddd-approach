package com.fgsantana.dddapproach.domain.shared.event

object EventDispatcher : EventDispatcherInterface {

    private val eventHandlers : MutableMap<String, MutableList<EventHandler<Event>>> = mutableMapOf()
    fun getEventHandlers() = eventHandlers

    override fun notify(event: Event) {
        eventHandlers[event.eventName]!!.forEach{ h->h.handle(event)}
    }

    override fun register(eventName: String, handler: EventHandler<Event>) {
        if (!eventHandlers.containsKey(eventName)) eventHandlers[eventName] = mutableListOf(handler)
            else eventHandlers[eventName]?.add(handler)

    }

    override fun unregister(eventName: String, handler: EventHandler<Event>) {
        eventHandlers[eventName]!!.remove(handler)
    }

    override fun unregisterAll() {
        eventHandlers.map { m -> m.value.clear() }
    }
}
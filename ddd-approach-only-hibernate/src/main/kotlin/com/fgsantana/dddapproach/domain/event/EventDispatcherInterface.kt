package com.fgsantana.dddapproach.domain.event

 interface EventDispatcherInterface{

    fun notify(event: Event)
    fun register(eventName: String, handler: EventHandler<Event>)
    fun unregister(eventName: String, handler: EventHandler<Event>)
    fun unregisterAll()
}
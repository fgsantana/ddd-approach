package com.fgsantana.dddapproach.domain.event

interface EventHandler<out Event> {
     fun handle(event: @UnsafeVariance Event)
}



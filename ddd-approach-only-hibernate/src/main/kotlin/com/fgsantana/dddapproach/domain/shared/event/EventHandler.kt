package com.fgsantana.dddapproach.domain.shared.event

interface EventHandler<out Event> {
     fun handle(event: @UnsafeVariance Event)
}



package com.fgsantana.dddapproach.domain.shared.event

import java.time.LocalDateTime

interface Event {
    val eventName : String;
    val eventTime: LocalDateTime
    val eventData: Any
}
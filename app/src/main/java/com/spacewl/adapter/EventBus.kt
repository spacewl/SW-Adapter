package com.spacewl.adapter

import kotlinx.coroutines.flow.*

class EventBus {
    private val flow = MutableSharedFlow<Any>()

    val bus = flow.asSharedFlow()

    fun send(event: Event) {
        flow.tryEmit(event)
    }

    fun send(click: Click) {
        flow.tryEmit(click)
    }

    inline fun <reified T : Event> events(): Flow<T> =
        bus.filter { it is Event && it is T }
            .map { it as T }

    inline fun <reified T : Click> clicks(): Flow<T> =
        bus.filter { it is Click && it is T }
            .map { it as T }
}
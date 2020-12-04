package com.spacewl.adapter

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

class EventBus {
    private val flow = MutableSharedFlow<Click>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )

    val bus = flow.asSharedFlow()

    fun send(click: Click) {
        flow.tryEmit(click)
    }

    inline fun <reified T : Click> clicks(): Flow<T> = bus.filter { it is T }
        .map { it as T }
}
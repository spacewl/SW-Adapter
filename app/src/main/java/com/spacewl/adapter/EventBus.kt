package com.spacewl.adapter

import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

class EventBus {
    val bus = BroadcastChannel<Any>(Channel.BUFFERED)

    fun send(event: Any) {
        bus.offer(event)
    }

    inline fun <reified T : Event> events(): Flow<T> =
        bus.openSubscription()
            .consumeAsFlow()
            .filter { it is Event && it is T }
            .map { it as T }

    inline fun <reified T : Click> clicks(): Flow<T> =
        bus.openSubscription()
            .consumeAsFlow()
            .filter { it is Click && it is T }
            .map { it as T }
}
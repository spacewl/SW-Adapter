package com.spacewl.adapter

import android.view.ViewGroup
import androidx.annotation.LayoutRes

abstract class AdapterDelegate {
    @get:LayoutRes
    abstract val layoutResource: Int

    abstract val itemType: Any

    fun isForViewType(items: List<ListItem>, position: Int): Boolean = items[position]::class == itemType

    open fun onCreateViewHolder(parent: ViewGroup): ViewHolder = BaseViewHolder(parent, layoutResource)

    open fun onBindViewHolder(items: List<ListItem>, position: Int, holder: ViewHolder) {}

    open fun onBindViewHolder(
        items: List<ListItem>,
        position: Int,
        holder: ViewHolder,
        payloads: List<Any>
    ) {
        payloads.forEach { payload ->
            when (payload) {
                is Collection<*> -> payload.forEach { nestedPayload ->
                    nestedPayload?.let {
                        onBindViewHolder(items, position, holder, nestedPayload)
                    }
                }
                else -> onBindViewHolder(items, position, holder, payload)
            }
        }
    }

    open fun onBindViewHolder(items: List<ListItem>, position: Int, holder: ViewHolder, payload: Any) {}

    open fun onViewRecycled(holder: ViewHolder) {}

    open fun onFailedToRecycleView(holder: ViewHolder): Boolean = false

    open fun onViewAttachedToWindow(holder: ViewHolder) {}

    open fun onViewDetachedFromWindow(holder: ViewHolder) {}
}
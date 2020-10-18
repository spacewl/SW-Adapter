package com.spacewl.adapter

import androidx.recyclerview.widget.RecyclerView

inline fun <reified T : ListItem> RecyclerView.ViewHolder.withAdapterPosition(block: (item: T) -> Unit) {
    if (adapterPosition != RecyclerView.NO_POSITION) {
        val items = items
        if (items != null && adapterPosition >= 0 && adapterPosition < items.size) {
            val item = items[adapterPosition]
            if (item !is T) throw IllegalArgumentException("item is not type of ${T::class.simpleName}")
            block.invoke(item)
        }
    }
}

val RecyclerView.ViewHolder.items: List<ListItem>?
    get() {
        val parent = itemView.parent as? RecyclerView ?: return null
        val adapter = parent.adapter as? DynamicAdapter ?: return null
        return adapter.items
    }
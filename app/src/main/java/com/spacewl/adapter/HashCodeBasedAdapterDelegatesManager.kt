package com.spacewl.adapter

import android.view.ViewGroup

class HashCodeBasedAdapterDelegatesManager(
    private val delegatesFactory: AdapterDelegatesFactory
) : AdapterDelegatesManager {
    private var delegates = hashMapOf<Int, AdapterDelegate>()

    override fun setDelegates(items: List<ListItem>) {
        items.forEach { item ->
            val viewType = viewTypeFrom(item)
            val delegate = delegates[viewType]
            if (delegate == null) {
                delegates[viewType] = delegatesFactory.createDelegate(item.javaClass)
            }
        }
    }

    override fun getItemViewType(items: List<ListItem>, position: Int): Int = viewTypeFrom(items[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        getDelegateOrThrowException(viewType).onCreateViewHolder(parent)

    override fun onBindViewHolder(items: List<ListItem>, position: Int, vh: ViewHolder) {
        getDelegateOrThrowException(vh.itemViewType).onBindViewHolder(items, position, vh)
    }

    override fun onBindViewHolder(items: List<ListItem>, position: Int, vh: ViewHolder, payloads: List<Any>) {
        getDelegateOrThrowException(vh.itemViewType).onBindViewHolder(items, position, vh, payloads)
    }

    override fun onViewRecycled(vh: ViewHolder) {
        getDelegateOrThrowException(vh.itemViewType).onViewRecycled(vh)
    }

    override fun onFailedToRecycleView(vh: ViewHolder): Boolean =
        getDelegateOrThrowException(vh.itemViewType).onFailedToRecycleView(vh)

    override fun onViewAttachedToWindow(vh: ViewHolder) {
        getDelegateOrThrowException(vh.itemViewType).onViewAttachedToWindow(vh)
    }

    override fun onViewDetachedFromWindow(vh: ViewHolder) {
        getDelegateOrThrowException(vh.itemViewType).onViewDetachedFromWindow(vh)
    }

    private fun getDelegateOrThrowException(viewType: Int): AdapterDelegate =
        delegates[viewType] ?: throw DelegateNotFoundException()

    private fun viewTypeFrom(item: ListItem): Int = item::class.java.name.hashCode()
}
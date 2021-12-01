package com.spacewl.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

open class DynamicAdapter constructor(
    private val manager: AdapterDelegatesManager,
    private val diffCalculator: DiffCalculator? = null
) : RecyclerView.Adapter<ViewHolder>() {

    constructor(
        factory: AdapterDelegatesFactory,
        calculator: DiffCalculator? = null
    ) : this(HashCodeBasedAdapterDelegatesManager(factory), calculator)

    val items = arrayListOf<ListItem>()

    override fun getItemViewType(position: Int): Int = manager.getItemViewType(items, position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return manager.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        manager.onBindViewHolder(items, position, holder)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: List<Any>) {
        if (payloads.isEmpty()) {
            manager.onBindViewHolder(items, position, holder)
        } else {
            manager.onBindViewHolder(items, position, holder, payloads)
        }
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        manager.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        manager.onViewDetachedFromWindow(holder)
    }

    override fun onViewRecycled(holder: ViewHolder) {
        manager.onViewRecycled(holder)
    }

    override fun getItemCount(): Int = items.size

    fun getItemPosition(listItem: ListItem): Int = items.indexOf(listItem)

    fun updateData(
        newItems: List<ListItem>,
        enableDiffUtils: Boolean = true,
        detectMoves: Boolean = true
    ) {
        when (enableDiffUtils) {
            true -> diffCalculator?.calculateDiff(
                this,
                ArrayList(items),
                ArrayList(newItems),
                detectMoves
            )
            else -> {
                setData(newItems)
                notifyDataSetChanged()
            }
        }
    }

    fun setData(newItems: List<ListItem>) {
        this.items.clear()
        this.items.addAll(newItems)
        manager.setDelegates(this.items)
    }

    fun getItem(position: Int): ListItem? {
        return items[position].takeIf { items.isNotEmpty() && position >= 0 }
    }
}
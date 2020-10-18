package com.spacewl.adapter

import android.view.ViewGroup

interface AdapterDelegatesManager {
    fun setDelegates(items: List<ListItem>)

    fun getItemViewType(items: List<ListItem>, position: Int): Int

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder

    fun onBindViewHolder(items: List<ListItem>, position: Int, vh: ViewHolder)

    fun onBindViewHolder(items: List<ListItem>, position: Int, vh: ViewHolder, payloads: List<Any>)

    fun onViewRecycled(vh: ViewHolder)

    fun onFailedToRecycleView(vh: ViewHolder): Boolean

    fun onViewAttachedToWindow(vh: ViewHolder)

    fun onViewDetachedFromWindow(vh: ViewHolder)
}
package com.spacewl.adapter

import androidx.recyclerview.widget.DiffUtil

internal class Callback(
    private val before: List<ListItem>,
    private val after: List<ListItem>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = before.size

    override fun getNewListSize(): Int = after.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return before[oldItemPosition].areItemsTheSame(after[newItemPosition])
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return before[oldItemPosition].areContentsTheSame(after[newItemPosition])
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any {
        return before[oldItemPosition].getChangePayload(after[newItemPosition])
    }
}
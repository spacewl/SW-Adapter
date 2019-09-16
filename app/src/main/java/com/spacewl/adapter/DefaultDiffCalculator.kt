package com.spacewl.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback

class DefaultDiffCalculator : DiffCalculator {

    override fun calculateDiff(
        adapter: DynamicAdapter,
        before: List<ListItem>,
        after: List<ListItem>,
        detectMoves: Boolean
    ) {
        val callback = Callback(before, after)
        val diffResult = DiffUtil.calculateDiff(callback, detectMoves)
        adapter.setData(after)
        diffResult.dispatchUpdatesTo(adapter)
    }

    override fun calculateDiff(
        updateCallback: ListUpdateCallback,
        before: List<ListItem>,
        after: List<ListItem>,
        detectMoves: Boolean
    ) {
        val callback = Callback(before, after)
        val diffResult = DiffUtil.calculateDiff(callback, detectMoves)
        diffResult.dispatchUpdatesTo(updateCallback)
    }
}
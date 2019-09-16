package com.spacewl.adapter

import androidx.recyclerview.widget.ListUpdateCallback

interface DiffCalculator {

    fun calculateDiff(
        adapter: DynamicAdapter,
        before: List<ListItem>,
        after: List<ListItem>,
        detectMoves: Boolean
    )

    fun calculateDiff(
        updateCallback: ListUpdateCallback,
        before: List<ListItem>,
        after: List<ListItem>,
        detectMoves: Boolean
    )
}
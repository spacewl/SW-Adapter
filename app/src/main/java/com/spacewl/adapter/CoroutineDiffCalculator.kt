package com.spacewl.adapter

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import kotlinx.coroutines.*

class CoroutineDiffCalculator : DiffCalculator, CoroutineScope {

    private var calculatorJob: Job? = null

    override val coroutineContext =
        Dispatchers.Default + SupervisorJob() + CoroutineExceptionHandler { cc, e ->
            Log.e(this::class.java.simpleName, "CoroutineContext --> $cc", e)
        }

    override fun calculateDiff(
        adapter: DynamicAdapter,
        before: List<ListItem>,
        after: List<ListItem>,
        detectMoves: Boolean
    ) {
        calculatorJob?.cancel()
        calculatorJob = launch {
            val callback = Callback(before, after)
            val diffResult = DiffUtil.calculateDiff(callback, detectMoves)
            withContext(Dispatchers.Main.immediate) {
                adapter.setData(after)
                diffResult.dispatchUpdatesTo(adapter)
            }
        }
    }

    override fun calculateDiff(
        updateCallback: ListUpdateCallback,
        before: List<ListItem>,
        after: List<ListItem>,
        detectMoves: Boolean
    ) {
        calculatorJob?.cancel()
        calculatorJob = launch {
            val callback = Callback(before, after)
            val diffResult = DiffUtil.calculateDiff(callback, detectMoves)
            withContext(Dispatchers.Main.immediate) {
                diffResult.dispatchUpdatesTo(updateCallback)
            }
        }
    }
}
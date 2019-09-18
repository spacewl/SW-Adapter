package com.spacewl.adapter

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import kotlinx.coroutines.*

class CoroutineDiffCalculator : DiffCalculator, CoroutineScope {

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
        val name = CoroutineName(adapter::class.java.simpleName)
        coroutineContext[name.key]?.cancel()
        launch(name) {
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
        val name = CoroutineName(updateCallback::class.java.simpleName)
        coroutineContext[name.key]?.cancel()
        launch(name) {
            val callback = Callback(before, after)
            val diffResult = DiffUtil.calculateDiff(callback, detectMoves)
            withContext(Dispatchers.Main.immediate) {
                diffResult.dispatchUpdatesTo(updateCallback)
            }
        }
    }
}
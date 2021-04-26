package com.spacewl.adapter

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import kotlin.reflect.KClass

class DividerDecoration(
    @ColorInt
    color: Int,
    @Px
    heightPx: Int,
    @Px
    private val paddingPx: Int,
    private val withLast: Boolean,
    private vararg val items: KClass<out ListItem>
) : ItemDecoration() {
    private val paint: Paint = Paint()
    private val offset: Float

    init {
        paint.color = color
        paint.strokeWidth = heightPx.toFloat()
        offset = paint.strokeWidth / 2
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.bottom = offset.toInt()
        outRect.top = offset.toInt()
    }

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val adapter = parent.adapter
        val lastPosition = state.itemCount - 1
        parent.children.forEach { view ->
            val position = parent.getChildAdapterPosition(view)
            val item = (adapter as? DynamicAdapter)
                ?.items
                ?.getOrNull(position)
            val isDividedClass =
                items.isEmpty() || items.firstOrNull { it.java == item?.javaClass } != null
            if (position == RecyclerView.NO_POSITION) return@forEach
            if ((position == lastPosition && !withLast) || !isDividedClass) return@forEach
            val hasNext = position < lastPosition
            val nextItem = if (hasNext) {
                (adapter as? DynamicAdapter)
                    ?.items
                    ?.getOrNull(position + 1)
            } else {
                null
            }
            if (items.isEmpty() || withLast || (hasNext && items.firstOrNull { it.java == nextItem?.javaClass } != null)) {
                canvas.drawLine(
                    (view.left + paddingPx).toFloat(),
                    view.bottom + offset,
                    (view.right - paddingPx).toFloat(),
                    view.bottom + offset,
                    paint
                )
            }
        }
    }
}
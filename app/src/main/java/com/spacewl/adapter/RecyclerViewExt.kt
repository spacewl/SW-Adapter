package com.spacewl.adapter

import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.recyclerview.widget.RecyclerView
import kotlin.reflect.KClass

fun RecyclerView.withDivider(
    @ColorInt
    color: Int,
    @Px
    heightPx: Int,
    @Px
    paddingPx: Int,
    withLast: Boolean = true,
    vararg items: KClass<out ListItem>
) {
    addItemDecoration(
        DividerDecoration(
            color = color,
            heightPx = heightPx,
            paddingPx = paddingPx,
            withLast = withLast,
            items = items
        )
    )
}
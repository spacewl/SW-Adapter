package com.spacewl.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes

open class BaseViewHolder(parent: ViewGroup, @LayoutRes layoutId: Int) : ViewHolder(
    LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
)
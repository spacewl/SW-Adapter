package com.spacewl.adapter

import android.view.LayoutInflater
import android.view.ViewGroup

open class BaseViewHolder(parent: ViewGroup, layoutId: Int) : ViewHolder(
    LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
)
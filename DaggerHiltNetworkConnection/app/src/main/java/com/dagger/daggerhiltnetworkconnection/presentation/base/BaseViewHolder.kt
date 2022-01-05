package com.dagger.daggerhiltnetworkconnection.presentation.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding


abstract class BaseViewHolder<T: Any?>(itemView: ViewBinding) : RecyclerView.ViewHolder(itemView.root) {
    abstract fun bind(item: T, position: Int)
}
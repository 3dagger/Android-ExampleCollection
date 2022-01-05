package com.dagger.daggerhiltnetworkconnection.presentation.base

import androidx.recyclerview.widget.RecyclerView

interface AdapterItemBinder<A: RecyclerView.Adapter<RecyclerView.ViewHolder>, T> {
    fun addItem(adapter: A, item: T)
}
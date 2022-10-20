package com.Doctoor.app.ui.interfaces

import android.view.View

interface OnItemClickListener<T> {
    fun onItemClick(v: View, item: T)
}

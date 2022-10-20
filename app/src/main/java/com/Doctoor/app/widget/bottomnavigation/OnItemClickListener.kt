package com.Doctoor.app.widget.bottomnavigation

import android.view.View


interface OnItemClickListener {
    fun onItemClick(parent: ItemsLayoutContainer, view: View, index: Int, animate: Boolean)

    fun onItemDown(
        parent: ItemsLayoutContainer, view: View,
        pressed: Boolean, x: Float, y: Float
    )
}

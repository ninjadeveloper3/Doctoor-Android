package com.Doctoor.app.widget.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class SpacesItemDecorationHome : RecyclerView.ItemDecoration {
    private val spaceLeft: Int
    private val spaceRight: Int
    private val spaceTop: Int
    private val spaceBottom: Int
    private var spaceFirstItem = true

    constructor(space: Int) {
        this.spaceLeft = space
        this.spaceBottom = 0
        this.spaceRight = space
        this.spaceTop = 0
    }

    constructor(space: Int, spaceFirstItem: Boolean) {
        this.spaceLeft = space
        this.spaceBottom = space
        this.spaceRight = space
        this.spaceTop = 0
        this.spaceFirstItem = spaceFirstItem
    }

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.left = spaceLeft
        outRect.right = spaceRight
        outRect.bottom = spaceBottom
        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildLayoutPosition(view) == 0 && spaceFirstItem) {
            outRect.top = spaceTop
        } else {
            outRect.top = 0
        }
    }
}
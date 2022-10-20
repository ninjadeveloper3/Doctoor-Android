package com.Doctoor.app.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.Doctoor.app.R

class SquaredLinearLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val useHeight: Boolean

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.SquaredImage, defStyleAttr, 0)
        useHeight = a.getBoolean(R.styleable.SquaredImage_useHeight, false)
        a.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val w = measuredWidth
        val h = measuredHeight
        if (useHeight) {
            setMeasuredDimension(h, h)
        } else {
            setMeasuredDimension(w, w)
        }


    }
}

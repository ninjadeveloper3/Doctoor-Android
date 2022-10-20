package com.Doctoor.app.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.google.android.material.card.MaterialCardView
import com.Doctoor.app.R



class AspectRatioCardView : MaterialCardView {
    private var mRatio = 1.0f

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        extractAttrs(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        extractAttrs(context, attrs)
    }

    private fun extractAttrs(context: Context, attrs: AttributeSet) {
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.AspectRatioImageView,
            0, 0
        )

        try {
            mRatio = a.getFloat(R.styleable.AspectRatioImageView_aspectRatio, 1.0f)
        } finally {
            a.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var width = measuredWidth
        var height = measuredHeight

        val widthWithoutPadding = width - paddingLeft - paddingRight
        val heightWithoutPadding = height - paddingTop - paddingBottom

        val maxWidth = (heightWithoutPadding * mRatio).toInt()
        val maxHeight = (widthWithoutPadding / mRatio).toInt()

        if (widthWithoutPadding > maxWidth) {
            width = maxWidth + paddingLeft + paddingRight
        } else {
            height = maxHeight + paddingTop + paddingBottom
        }

        super.onMeasure(
            View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
        )
    }
}
package com.Doctoor.app.widget

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.Doctoor.app.R
import com.Doctoor.app.utils.font.TypefaceUtil
import com.Doctoor.app.utils.font.TypefacesFont


class LineThroughTextView : View {
    private var text: String? = null
    private var textColor: Int = 0
    private var textSize: Float = 0.toFloat()
    private var textPadding: Int = 0
    private var lineHeight: Int = 0
    private var lineColor: Int = 0

    private var mTextPaint: Paint? = null

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs) {
        bootstrap(context, attrs, 0, 0)
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        bootstrap(context, attrs, defStyleAttr, 0)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        bootstrap(context, attrs, defStyleAttr, defStyleRes)
    }

    private fun bootstrap(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) {
        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.LineThroughTextView,
            defStyleAttr,
            defStyleRes
        )
        text = a.getString(R.styleable.LineThroughTextView_lt_text)
        textColor = a.getColor(R.styleable.LineThroughTextView_lt_textColor, Color.BLACK)
        val metrics = resources.displayMetrics
        textSize = a.getDimension(
            R.styleable.LineThroughTextView_lt_textSize,
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16f, metrics) / metrics.density
        )
        textPadding = a.getDimensionPixelSize(R.styleable.LineThroughTextView_lt_textPadding, 0)
        lineHeight = a.getDimensionPixelSize(
            R.styleable.LineThroughTextView_lt_lineHeight,
            2
        ) // default to one pixel
        lineColor = a.getColor(R.styleable.LineThroughTextView_lt_lineColor, Color.DKGRAY)
        a.recycle()

        mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mTextPaint?.typeface = TypefaceUtil.getTypeface(TypefacesFont.MARK_PRO_LIGHT)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (!TextUtils.isEmpty(text)) {
            canvas.save()

            mTextPaint!!.textSize = textSize
            mTextPaint!!.color = textColor
            val textWidth = mTextPaint!!.measureText(text)

            val x = ((width - textWidth) / 2).toInt()
            var y = ((height - (mTextPaint!!.descent() + mTextPaint!!.ascent())) / 2).toInt()

            // draw text
            canvas.drawText(text!!, x.toFloat(), y.toFloat(), mTextPaint!!)

            // line y
            mTextPaint!!.color = lineColor
            y = height / 2

            // draw lines
            canvas.drawRect(
                paddingLeft.toFloat(),
                (y - lineHeight / 2).toFloat(),
                (x - textPadding).toFloat(),
                (y + lineHeight / 2).toFloat(),
                mTextPaint!!
            )
            canvas.drawRect(
                x.toFloat() + textWidth + textPadding.toFloat(),
                (y - lineHeight / 2).toFloat(),
                (width - paddingRight).toFloat(),
                (y + lineHeight / 2).toFloat(),
                mTextPaint!!
            )

            canvas.restore()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // if text is empty, skip it
        if (TextUtils.isEmpty(text)) {
            setMeasuredDimension(0, 0)
            return
        }

        // we only need to measure the text height
        val height = (textSize + paddingTop.toFloat() + paddingBottom.toFloat()).toInt()

        setMeasuredDimension(
            MeasureSpec.getSize(widthMeasureSpec),
            resolveSize(height, heightMeasureSpec)
        )
    }

    fun getLineColor() = lineColor

    fun setLineColor(lineColor: Int) {
        this.lineColor = lineColor
        invalidate()
    }

    fun getLineHeight() = lineHeight

    fun setLineHeight(lineHeight: Int) {
        this.lineHeight = lineHeight
        invalidate()
    }

    fun getText() = text

    fun setText(text: String) {
        this.text = text
        invalidate()
    }

    fun getTextColor() = textColor

    fun setTextColor(textColor: Int) {
        this.textColor = textColor
        invalidate()
    }

    fun getTextPadding() = textPadding

    fun setTextPadding(textPadding: Int) {
        this.textPadding = textPadding
        invalidate()
    }

}

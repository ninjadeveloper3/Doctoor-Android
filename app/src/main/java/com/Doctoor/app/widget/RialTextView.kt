package com.Doctoor.app.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols


class RialTextView : AppCompatTextView {

    internal var rawText: String = ""

    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }

    override fun setText(text: CharSequence, type: TextView.BufferType) {
        rawText = text.toString()
        var prezzo = text.toString()
        try {

            val symbols = DecimalFormatSymbols()
            symbols.decimalSeparator = ','
            val decimalFormat = DecimalFormat("###,###,###,###", symbols)
            prezzo = decimalFormat.format(Integer.parseInt(text.toString()).toLong())
        } catch (ignored: Exception) {
        }

        super.setText(prezzo, type)
    }

    override fun getText(): CharSequence {

        return rawText
    }
}
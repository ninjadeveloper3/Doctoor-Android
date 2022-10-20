package com.Doctoor.app.widget

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.Doctoor.app.R
import com.Doctoor.app.model.response.BaseModel
import com.Doctoor.app.utils.font.TypefaceUtil
import com.Doctoor.app.utils.font.TypefacesFont


class QuantityView : LinearLayoutCompat, View.OnClickListener {

    private var quantityBackground: Drawable? = null
    private var addButtonBackground: Drawable? = null
    private var removeButtonBackground: Drawable? = null

    private var addButtonText: String? = null
    private var removeButtonText: String? = null

    private var quantity: Int = 0
    var isQuantityDialog: Boolean = false
    var maxQuantity = Integer.MAX_VALUE
    var minQuantity = Integer.MAX_VALUE
    private var quantityPadding: Int = 0

    private var quantityTextColor: Int = 0
    private var addButtonTextColor: Int = 0
    private var removeButtonTextColor: Int = 0

    private var mButtonAdd: AppCompatTextView? = null
    private var mButtonRemove: AppCompatTextView? = null
    private var mTextViewQuantity: TextView? = null

    var onQuantityChangeListener: OnQuantityChangeListener? = null
    var onQuantityChangeListenerWithData: OnQuantityChangeListenerWithData? = null
    private var mTextViewClickListener: View.OnClickListener? = null
    private var item: BaseModel? = null

    interface OnQuantityChangeListener {
        fun onQuantityChanged(oldQuantity: Int, newQuantity: Int, programmatically: Boolean)

        fun onLimitReached()
    }

    interface OnQuantityChangeListenerWithData {
        fun onQuantityChanged(
            oldQuantity: Int,
            newQuantity: Int,
            programmatically: Boolean,
            item: BaseModel
        )

        fun onLimitReached()
    }

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.QuantityView, defStyle, 0)

        addButtonText = resources.getString(R.string.qv_add)
        if (a.hasValue(R.styleable.QuantityView_qv_addButtonText)) {
            addButtonText = a.getString(R.styleable.QuantityView_qv_addButtonText)!!
        }
        addButtonBackground = ContextCompat.getDrawable(context, R.drawable.qv_btn_selector)
        if (a.hasValue(R.styleable.QuantityView_qv_addButtonBackground)) {
            addButtonBackground = a.getDrawable(R.styleable.QuantityView_qv_addButtonBackground)
        }
        addButtonTextColor = a.getColor(R.styleable.QuantityView_qv_addButtonTextColor, Color.BLACK)

        removeButtonText = resources.getString(R.string.qv_remove)
        if (a.hasValue(R.styleable.QuantityView_qv_removeButtonText)) {
            removeButtonText = a.getString(R.styleable.QuantityView_qv_removeButtonText)
        }
        removeButtonBackground = ContextCompat.getDrawable(context, R.drawable.qv_btn_selector)
        if (a.hasValue(R.styleable.QuantityView_qv_removeButtonBackground)) {
            removeButtonBackground =
                a.getDrawable(R.styleable.QuantityView_qv_removeButtonBackground)
        }
        removeButtonTextColor =
            a.getColor(R.styleable.QuantityView_qv_removeButtonTextColor, Color.BLACK)

        quantity = a.getInt(R.styleable.QuantityView_qv_quantity, 0)
        maxQuantity = a.getInt(R.styleable.QuantityView_qv_maxQuantity, Integer.MAX_VALUE)
        minQuantity = a.getInt(R.styleable.QuantityView_qv_minQuantity, 0)

        quantityPadding =
            a.getDimension(R.styleable.QuantityView_qv_quantityPadding, pxFromDp(24f).toFloat())
                .toInt()
        quantityTextColor = a.getColor(R.styleable.QuantityView_qv_quantityTextColor, Color.BLACK)
        quantityBackground = ContextCompat.getDrawable(context, R.drawable.qv_bg_selector)
        if (a.hasValue(R.styleable.QuantityView_qv_quantityBackground)) {
            quantityBackground = a.getDrawable(R.styleable.QuantityView_qv_quantityBackground)
        }

        isQuantityDialog = a.getBoolean(R.styleable.QuantityView_qv_quantityDialog, true)

        a.recycle()
        val dp10 = pxFromDp(10f)

        mButtonAdd = AppCompatTextView(context)
        mButtonAdd!!.gravity = Gravity.CENTER
        mButtonAdd!!.setPadding(dp10, dp10, dp10, dp10)
        mButtonAdd!!.minimumHeight = 0
        mButtonAdd!!.minimumWidth = 0
        mButtonAdd!!.minHeight = 0
        mButtonAdd!!.minWidth = 0
        mButtonAdd?.typeface = TypefaceUtil.getTypeface(TypefacesFont.MARK_PRO_RAGULAR)
        mButtonAdd?.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        setAddButtonBackground(addButtonBackground)
        setAddButtonText(addButtonText!!)
        setAddButtonTextColor(addButtonTextColor)

        mButtonRemove = AppCompatTextView(context)
        mButtonRemove!!.gravity = Gravity.CENTER
        mButtonRemove!!.setPadding(dp10, dp10, dp10, dp10)
        mButtonRemove!!.minimumHeight = 0
        mButtonRemove!!.minimumWidth = 0
        mButtonRemove!!.minHeight = 0
        mButtonRemove!!.minWidth = 0
        mButtonRemove?.typeface = TypefaceUtil.getTypeface(TypefacesFont.MARK_PRO_RAGULAR)
        mButtonRemove?.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        setRemoveButtonBackground(removeButtonBackground)
        setRemoveButtonText(removeButtonText!!)
        setRemoveButtonTextColor(removeButtonTextColor)

        mTextViewQuantity = AppCompatTextView(context)
        mTextViewQuantity?.gravity = Gravity.CENTER
        mTextViewQuantity?.typeface = TypefaceUtil.getTypeface(TypefacesFont.MARK_PRO_RAGULAR)
        setQuantityTextColor(quantityTextColor)
        setQuantity(quantity)
        setQuantityBackground(quantityBackground)
        setQuantityPadding(quantityPadding)

        orientation = LinearLayoutCompat.HORIZONTAL

        addView(
            mButtonAdd,
            LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
            LinearLayoutCompat.LayoutParams.WRAP_CONTENT
        )

        /*Height and width of quantity box*/

        addView(
            mTextViewQuantity,
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )

        addView(
            mButtonRemove,
            LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
            LinearLayoutCompat.LayoutParams.WRAP_CONTENT
        )

        mButtonAdd!!.setOnClickListener(this)
        mButtonRemove!!.setOnClickListener(this)
        mTextViewQuantity!!.setOnClickListener(this)
    }


    fun setQuantityClickListener(ocl: View.OnClickListener) {
        mTextViewClickListener = ocl
    }

    override fun onClick(v: View) {
        if (v === mButtonAdd) {
            if (quantity + 1 > maxQuantity) {
                if (onQuantityChangeListener != null) onQuantityChangeListener!!.onLimitReached()
                if (onQuantityChangeListenerWithData != null) onQuantityChangeListenerWithData!!.onLimitReached()
            } else {
                val oldQty = quantity
                quantity += 1
                mTextViewQuantity!!.text = quantity.toString()
                if (onQuantityChangeListener != null)
                    onQuantityChangeListener!!.onQuantityChanged(oldQty, quantity, false)

                if (onQuantityChangeListenerWithData != null)
                    onQuantityChangeListenerWithData!!.onQuantityChanged(
                        oldQty,
                        quantity,
                        false,
                        item!!
                    )
            }
        } else if (v === mButtonRemove) {
            if (quantity - 1 < minQuantity) {
                if (onQuantityChangeListener != null) onQuantityChangeListener!!.onLimitReached()
                if (onQuantityChangeListenerWithData != null) onQuantityChangeListenerWithData!!.onLimitReached()
            } else {
                val oldQty = quantity
                quantity -= 1
                mTextViewQuantity!!.text = quantity.toString()
                if (onQuantityChangeListener != null)
                    onQuantityChangeListener!!.onQuantityChanged(oldQty, quantity, false)
                if (onQuantityChangeListenerWithData != null)
                    onQuantityChangeListenerWithData!!.onQuantityChanged(
                        oldQty,
                        quantity,
                        false,
                        item!!
                    )
            }
        }
    }

    fun getQuantityBackground(): Drawable? {
        return quantityBackground
    }

    fun setQuantityBackground(quantityBackground: Drawable?) {
        this.quantityBackground = quantityBackground

        mTextViewQuantity!!.background = quantityBackground

    }

    fun getAddButtonBackground(): Drawable? {
        return addButtonBackground
    }

    fun setAddButtonBackground(addButtonBackground: Drawable?) {
        this.addButtonBackground = addButtonBackground

        mButtonAdd!!.background = addButtonBackground

    }

    fun getRemoveButtonBackground(): Drawable? {
        return removeButtonBackground
    }

    fun setRemoveButtonBackground(removeButtonBackground: Drawable?) {
        this.removeButtonBackground = removeButtonBackground

        mButtonRemove!!.background = removeButtonBackground

    }

    fun getAddButtonText(): String? {
        return addButtonText
    }

    fun setAddButtonText(addButtonText: String) {
        this.addButtonText = addButtonText
        mButtonAdd!!.text = addButtonText
    }

    fun getRemoveButtonText(): String? {
        return removeButtonText
    }

    fun setRemoveButtonText(removeButtonText: String) {
        this.removeButtonText = removeButtonText
        mButtonRemove!!.text = removeButtonText
    }

    fun getQuantity(): Int {
        return quantity
    }

    fun setQuantity(quantity: Int) {
        var newQuantity = quantity
        var limitReached = false

        if (newQuantity > maxQuantity) {
            newQuantity = maxQuantity
            limitReached = true
        }
        if (newQuantity < minQuantity) {
            newQuantity = minQuantity
            limitReached = true
        }
        if (!limitReached) {
            //            if (onQuantityChangeListener != null) {
            //                onQuantityChangeListener.onQuantityChanged(quantity, newQuantity, true);
            //            }
            this.quantity = newQuantity
            mTextViewQuantity!!.text = this.quantity.toString()
        } else {
            if (onQuantityChangeListener != null) onQuantityChangeListener!!.onLimitReached()
            if (onQuantityChangeListenerWithData != null) onQuantityChangeListenerWithData!!.onLimitReached()
        }
    }

    fun getQuantityPadding(): Int {
        return quantityPadding
    }

    fun setQuantityPadding(quantityPadding: Int) {
        this.quantityPadding = quantityPadding
        mTextViewQuantity!!.setPadding(quantityPadding, 0, quantityPadding, 0)
    }

    fun getQuantityTextColor(): Int {
        return quantityTextColor
    }

    fun setQuantityTextColor(quantityTextColor: Int) {
        this.quantityTextColor = quantityTextColor
        mTextViewQuantity!!.setTextColor(quantityTextColor)
    }

    fun setQuantityTextColorRes(quantityTextColorRes: Int) {
        this.quantityTextColor = ContextCompat.getColor(context, quantityTextColorRes)
        mTextViewQuantity!!.setTextColor(quantityTextColor)
    }

    fun getAddButtonTextColor(): Int {
        return addButtonTextColor
    }

    fun setAddButtonTextColor(addButtonTextColor: Int) {
        this.addButtonTextColor = addButtonTextColor
        mButtonAdd!!.setTextColor(addButtonTextColor)
    }

    fun setAddButtonTextColorRes(addButtonTextColorRes: Int) {
        this.addButtonTextColor = ContextCompat.getColor(context, addButtonTextColorRes)
        mButtonAdd!!.setTextColor(addButtonTextColor)
    }

    fun getRemoveButtonTextColor(): Int {
        return removeButtonTextColor
    }

    fun setRemoveButtonTextColor(removeButtonTextColor: Int) {
        this.removeButtonTextColor = removeButtonTextColor
        mButtonRemove!!.setTextColor(removeButtonTextColor)
    }

    fun setRemoveButtonTextColorRes(removeButtonTextColorRes: Int) {
        this.removeButtonTextColor = ContextCompat.getColor(context, removeButtonTextColorRes)
        mButtonRemove!!.setTextColor(removeButtonTextColor)
    }

    private fun dpFromPx(px: Float): Int {
        return (px / resources.displayMetrics.density).toInt()
    }

    private fun pxFromDp(dp: Float): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }

    private fun setMaximumQuantity(quantity: Int) {
        this.maxQuantity = quantity
    }

    private fun setItem(item: BaseModel) {
        this.item = item
    }

    companion object {


        fun isValidNumber(string: String): Boolean {
            try {
                return Integer.parseInt(string) <= Integer.MAX_VALUE
            } catch (e: Exception) {
                return false
            }

        }

        @JvmStatic
        @BindingAdapter("qv_quantity")
        fun setQuantity(quantityView: QuantityView, quantity: Int) {
            if (quantity > 0) quantityView.setQuantity(quantity) else quantityView.setQuantity(1)
        }

        @JvmStatic
        @BindingAdapter("qv_maxQuantity")
        fun setMaxQuantity(quantityView: QuantityView, quantity: Int) {
            if (quantity > 0) {
                quantityView.setMaximumQuantity(quantity)
            } else {
                quantityView.setMaximumQuantity(1)
            }
        }

        @JvmStatic
        @BindingAdapter("item")
        fun setItem(quantityView: QuantityView, item: BaseModel) {
            quantityView.setItem(item)
        }
    }
}
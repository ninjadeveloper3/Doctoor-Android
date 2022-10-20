package com.Doctoor.app.binding

import android.graphics.Color
import android.net.Uri
import android.text.method.PasswordTransformationMethod
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.setPadding
import androidx.databinding.BindingAdapter
import com.Doctoor.app.DoctoorApp
import com.Doctoor.app.R
import com.Doctoor.app.glide.setBlurImage
import com.Doctoor.app.glide.setCircleProfileImage
import com.Doctoor.app.glide.setImage
import com.Doctoor.app.model.response.Medicines
import com.Doctoor.app.ui.modules.myorder.PaymentStatusEnum
import com.Doctoor.app.utils.Constants
import com.Doctoor.app.utils.debug
import com.Doctoor.app.widget.EditTextRichDrawable
import com.Doctoor.app.widget.MaterialSpinner
import com.Doctoor.app.widget.SquareImageView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object ImageBinding {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun setImageUrl(imageView: AppCompatImageView, url: String?) {
        url?.let { setImage(imageView, it) }
    }

    @JvmStatic
    @BindingAdapter("circularImageUrl")
    fun setCircularImageUrl(imageView: AppCompatImageView, url: String?) {
        url?.let { setImage(imageView, it) }
    }

    @JvmStatic
    @BindingAdapter("circularImageUrl")
    fun setCircularImageUrl(imageView: AppCompatImageView, uri: Uri?) {
        uri?.let { setImage(imageView, it) }
    }

    @JvmStatic
    @BindingAdapter("loadAvatar")
    fun loadAvatar(imageView: AppCompatImageView, url: String?) {
        url?.let { setCircleProfileImage(imageView, it, R.drawable.ic_user_profile) }
    }

    @JvmStatic
    @BindingAdapter("navViewimageUrl", "app:srcCompat")
    fun setNavigationViewImageUrl(imageView: AppCompatImageView, url: String?, resource: Int) {
        if (resource > 0) imageView.setImageResource(resource) else url?.let {
            setImage(
                imageView,
                it
            )
        }
    }

    @JvmStatic
    @BindingAdapter("app:srcCompat")
    fun setImageViewResource(imageView: AppCompatImageView, resource: Int) {
        imageView.setImageResource(resource)
    }

    @JvmStatic
    @BindingAdapter("isInCart")
    fun setCartResource(imageView: AppCompatImageView, isInCart: Boolean = false) {
        if (isInCart) imageView.setImageResource(R.drawable.ic_cart_selected) else imageView.setImageResource(
            R.drawable.ic_cart
        )
    }

    @JvmStatic
    @BindingAdapter(value = ["imageUri", "isBlur"], requireAll = true)
    fun setImageUri(imageView: AppCompatImageView, uri: Uri, isBlur: Boolean) {
        if (isBlur) setBlurImage(imageView, uri) else setImage(imageView, uri)
    }

    /* @JvmStatic
     @BindingAdapter("isReport","isService")
     fun setNotificationItemImage(imageView: AppCompatImageView, isReport: Boolean = false, isService: Boolean = false) {
         if (isReport) imageView.setImageResource(R.drawable.ic_lab_test) else if (isService) imageView.setImageResource(R.drawable.ic_services) else imageView.setImageResource(R.drawable.ic_order)
     }*/

    @JvmStatic
    @BindingAdapter("notificationType")
    fun setNotificationItemImage(imageView: AppCompatImageView, notificationType: Int) {
        when (notificationType) {
            Constants.REPORT -> {
                imageView.setImageResource(R.drawable.ic_lab_test)
                imageView.setBackgroundResource(R.drawable.lab_test_rounded)
            }
            Constants.ORDER -> {
                imageView.setImageResource(R.drawable.ic_order)
                imageView.setBackgroundResource(R.drawable.blue_rounded)
            }
            Constants.SERVICE_CASH_ON_DELIVERY, Constants.SERVICE -> {
                imageView.setImageResource(R.drawable.ic_services)
                imageView.setBackgroundResource(R.drawable.light_green_rounded)
            }
            Constants.RENTAL_EQUIPMENT_CASH_ON_DELIVERY, Constants.RENTAL_EQUIPMENT -> {
                imageView.setImageResource(R.drawable.ic_services)
                imageView.setBackgroundResource(R.drawable.light_purple_rounded)
            }
            else -> {
                imageView.setImageResource(R.drawable.ic_lab_test)
                imageView.setBackgroundResource(R.drawable.lab_test_rounded)
            }
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["imageUrl", "isTest"])
    fun setInDemandItemImage(imageView: SquareImageView, url: String?, isTest: Boolean = false) {
        url?.let {
            setImage(imageView, it)
            if (isTest) {
                imageView.setBackgroundResource(R.drawable.lab_test_rounded)
                imageView.setPadding(15)
            } else {
                imageView.setBackgroundResource(0)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("selectedPosition")
    fun setPosition(spinner: MaterialSpinner, position: String) {
//        spinner.setSelection(position)
        spinner.setSelection(position.toInt(), true)
    }


    @JvmStatic
    @BindingAdapter("selectedPosition")
    fun setPosition(spinner: MaterialSpinner, position: Int) {
        spinner.setSelection(position, true)
    }


    @JvmStatic
    @BindingAdapter("warnings")
    fun setWarnings(textView: TextView, warnings: ArrayList<Medicines.Warning>?) {
        var warningText = "No contradiction"
        if (warnings != null) {
            if (warnings.isNotEmpty()) {
                warningText = ""
                for (item in warnings) {
                    warningText = warningText + item.title + ":\n" + item.detail + "\n"
                }
            }
        }
        textView.text = warningText

    }

    @JvmStatic
    @BindingAdapter("unit")
    fun setUnit(textView: TextView, unit: String?) {
        if (unit !== null) {
            textView.text = "($unit)"
        }
    }

    @JvmStatic
    @BindingAdapter("isHidden")
    fun setPasswordTypeFace(input: EditTextRichDrawable, isHidden: Boolean) {
        input.transformationMethod = PasswordTransformationMethod();
    }

    @JvmStatic
    @BindingAdapter("date")
    fun setDate(textview: TextView, createdAt: String?) {

        if (!createdAt.isNullOrBlank()) {
//            val string = createdAt

//            val instant: Instant = Instant.parse("2018-02-02T06:54:57.744Z")
//            val d = Date.from(instant)
//            debug(d)
//            2019-11-13 13:34:58
            val input = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val output = SimpleDateFormat("dd MMMM yyyy")

            var d: Date? = null
            try {
                d = input.parse(createdAt)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            val formatted = output.format(d!!)

            debug("DATE $formatted")

            textview.text = formatted

//            val formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy", Locale.ENGLISH)
//            val date = LocalDate.parse(string, formatter)
        }

    }

    @JvmStatic
    @BindingAdapter("time")
    fun setTime(textview: TextView, createdAt: String?) {

        if (!createdAt.isNullOrBlank()) {
//            val string = createdAt

//            val instant: Instant = Instant.parse("2018-02-02T06:54:57.744Z")
//            val d = Date.from(instant)
//            debug(d)
//            2019-11-13 13:34:58
            val input = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val output = SimpleDateFormat("HH:mm")

            var d: Date? = null
            try {
                d = input.parse(createdAt)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            val formatted = output.format(d!!)

            debug("DATE $formatted")

            textview.text = formatted

//            val formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy", Locale.ENGLISH)
//            val date = LocalDate.parse(string, formatter)
        }

    }

    @JvmStatic
    @BindingAdapter("itemPrice")
    fun setPrice(textview: TextView, price: Double?) {
        price?.let {
            val rounded: Double = Math.round(price * 100.0) / 100.0
            textview.text = rounded.toString()
        }
    }

    @JvmStatic
    @BindingAdapter("orderAmount")
    fun setOrderAmount(textview: TextView, price: Double?) {
        price?.let {
            val rounded: Double = Math.round(price * 100.0) / 100.0
            textview.text = "Rs. $rounded"
        }
    }

    @JvmStatic
    @BindingAdapter("orderStatus")
    fun setOrderStatus(view: AppCompatTextView, status: Int) {
        val paymentEnum = PaymentStatusEnum.getPaymentStatus(status)
        view.text = paymentEnum.status
        view.setTextColor(paymentEnum.textColor)
        view.setBackgroundColor(paymentEnum.color)
    }

    @JvmStatic
    @BindingAdapter("orderStatusTopLine")
    fun setOrderItemTopLine(linearLayout: LinearLayout, status: Int) {
        val paymentEnum = PaymentStatusEnum.getPaymentStatus(status)
        linearLayout.background =
            DoctoorApp.drawable(paymentEnum.drawable)
    }


//    @JvmStatic
//    @BindingAdapter("bind:url")
//    fun loadImage(view: ImageView, url:String){
//        RequestOptions.placeholderOf(placeHolder).error(errorPlaceHolder)
//
//        Glide.with(view)
//            .setDefaultRequestOptions(defaultRequest(R.drawable.placeholder,R.drawable.noimageplaceholder))
//            .load(url)
//            .thumbnail(0.2F)
//            .into(view)
//    }
}
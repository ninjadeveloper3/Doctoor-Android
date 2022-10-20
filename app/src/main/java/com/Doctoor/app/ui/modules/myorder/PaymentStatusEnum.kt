package com.Doctoor.app.ui.modules.myorder

import android.graphics.drawable.Drawable
import com.Doctoor.app.DoctoorApp
import com.Doctoor.app.R

enum class PaymentStatusEnum(val status: String, val color: Int,val textColor: Int, val drawable: Int) {
    CANCELLED("Cancelled", DoctoorApp.color(R.color.cancelled),DoctoorApp.color(R.color.white), R.drawable.cancelled_top_line),
    PAID("In-Progress", DoctoorApp.color(R.color.paid),DoctoorApp.color(R.color.black), R.drawable.paid_top_line),
    NOT_PAID("Pending", DoctoorApp.color(R.color.not_paid),DoctoorApp.color(R.color.black), R.drawable.not_paid_top_line),
    DELIVERED("Completed", DoctoorApp.color(R.color.delivered),DoctoorApp.color(R.color.white), R.drawable.delivered_top_line);

    companion object {
        fun getPaymentStatus(status: Int): PaymentStatusEnum {
            return when (status) {
                1 -> CANCELLED
                2 -> PAID
                3 -> NOT_PAID
                4 -> DELIVERED
                else -> CANCELLED
            }
        }
    }
}
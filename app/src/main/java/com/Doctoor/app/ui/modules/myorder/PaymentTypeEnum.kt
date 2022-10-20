package com.Doctoor.app.ui.modules.myorder

import com.Doctoor.app.utils.Constants

enum class PaymentTypeEnum(val paymentType: String) {
    COD("Cash On Delivery"),
    JAZZ("JazzCash"),
    EASYPAISA("Easy Paisa"),
    PENDING("Pending");

    companion object {
        fun getPaymentType(paymentMethodId: Int?): PaymentTypeEnum {
            return when (paymentMethodId) {
                Constants.COD_PAYMENT_METHOD -> COD
                Constants.JAZZ_CASH_PAYMENT_METHOD, Constants.JAZ_CASH_CC_METHOD -> JAZZ
                Constants.EASY_PAISA_MA_METHOD, Constants.EASY_PAISA_CC_METHOD -> EASYPAISA
                else -> PENDING
            }
        }
    }
}
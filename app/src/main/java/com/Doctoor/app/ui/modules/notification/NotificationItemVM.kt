package com.Doctoor.app.ui.modules.notification

import android.os.Bundle
import android.view.View
import com.google.gson.Gson
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseListDataViewModel
import com.Doctoor.app.model.response.Medicines
import com.Doctoor.app.ui.modules.checkout.payment.PaymentFragment
import com.Doctoor.app.ui.modules.lab_reports.LabReportsFragment
import com.Doctoor.app.utils.AlertUtils
import com.Doctoor.app.utils.Constants
import com.Doctoor.app.utils.Constants.AMOUNT
import com.Doctoor.app.utils.Constants.IS_EXCEEDED
import com.Doctoor.app.utils.Constants.IS_RENTAL_PAYMENT
import com.Doctoor.app.utils.Constants.IS_SERVICE_PAYMENT
import com.Doctoor.app.utils.Constants.ORDER
import com.Doctoor.app.utils.Constants.ORDER_NUMBER
import com.Doctoor.app.utils.Constants.RENTAL_EQUIPMENT
import com.Doctoor.app.utils.Constants.RENTAL_EQUIPMENT_CASH_ON_DELIVERY
import com.Doctoor.app.utils.Constants.REPORT
import com.Doctoor.app.utils.Constants.SERVICE
import com.Doctoor.app.utils.Constants.SERVICE_CASH_ON_DELIVERY
import com.Doctoor.app.utils.debug

class NotificationItemVM : BaseListDataViewModel<Medicines.Notification>() {
    private lateinit var item: Medicines.Notification
    var title = ""
    var amount = ""
    var orderNumber = ""
    var description = ""
    override fun setItem(item: Medicines.Notification) {
        this.item = item

        when (item.noteType) {
            REPORT -> {
                this.title = item.noteHeading
                this.description = item.noteBody
            }
            SERVICE_CASH_ON_DELIVERY, SERVICE -> {
//                this.title = "#${(item.noteBody)} | Rs. ${(item.totalAmount)} "
                this.title = item.noteBody
                this.description = item.noteTitle
            }
            RENTAL_EQUIPMENT_CASH_ON_DELIVERY, RENTAL_EQUIPMENT -> {
                this.title = "#${(item.noteHeading)} | Rs. ${(item.totalAmount)} "
                this.description = item.noteTitle
            }
            ORDER -> {
                this.title = "#${(item.noteHeading)} | Rs. ${(item.totalAmount)} "
                this.description = item.noteBody
            }
            else -> {
                this.title = "#${(item.noteHeading)}"
                this.description = item.noteBody
            }
        }
        notifyChange()
    }

    override fun getItem() = item

    override fun layoutRes() = R.layout.item_notification
    override fun onItemClick(v: View, item: Medicines.Notification) {
        debug(Gson().toJson(item))
        when (item.noteType) {
            REPORT -> {
                val bundle = Bundle()
                bundle.putBoolean(Constants.SHOW_TOOLBAR, false)
                bundle.putBoolean(Constants.SHOW_CART_MENU, false)
                navigatorHelper?.startFragmentWithBottomNavigation<LabReportsFragment>(
                    LabReportsFragment::class.java.name,
                    bundle = bundle
                )
            }
            ORDER -> {

            }
            else -> {
                if (item.isPaid == 0) {
                    val isExceeded =
                        item.noteType == SERVICE_CASH_ON_DELIVERY || item.noteType == RENTAL_EQUIPMENT_CASH_ON_DELIVERY
                    amount =
                        item.totalAmount.toString()   //// actually backend team use description for holding amount
                    orderNumber =
                        item.noteHeading    //// actually backend team use heading for order number for order and test category for test report

                    val bundle = Bundle()
                    if (item.noteType == RENTAL_EQUIPMENT) {
                        bundle.putBoolean(IS_SERVICE_PAYMENT, false)
                        bundle.putBoolean(IS_RENTAL_PAYMENT, true)
                    } else {
                        bundle.putBoolean(IS_SERVICE_PAYMENT, true)
                        bundle.putBoolean(IS_RENTAL_PAYMENT, false)
                    }
                    bundle.putBoolean(IS_EXCEEDED, isExceeded)
                    bundle.putString(ORDER_NUMBER, orderNumber)
                    bundle.putString(AMOUNT, amount)
                    navigatorHelper?.startFragmentWithToolbar<PaymentFragment>(
                        PaymentFragment::class.java.name,
                        bundle = bundle
                    )
                } else {
                    AlertUtils.showSnackBarLongMessage(
                        v.rootView, "The payment of this order/service has been already done"
                    )
                }
            }
        }
    }
}
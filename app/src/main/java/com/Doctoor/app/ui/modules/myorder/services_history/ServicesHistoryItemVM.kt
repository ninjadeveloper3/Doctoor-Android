package com.Doctoor.app.ui.modules.myorder.services_history

import android.os.Bundle
import android.view.View
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseListDataViewModel
import com.Doctoor.app.model.response.MedicalServices
import com.Doctoor.app.ui.modules.myorder.PaymentStatusEnum
import com.Doctoor.app.ui.modules.myorder.PaymentTypeEnum
import com.Doctoor.app.ui.modules.service_type.MedicalServicesFragment
import com.Doctoor.app.utils.Constants
import com.Doctoor.app.utils.Constants.COD_PAYMENT_METHOD
import com.Doctoor.app.utils.Constants.COMMENTS
import com.Doctoor.app.utils.Constants.ID
import com.Doctoor.app.utils.Constants.JAZZ_CASH_PAYMENT_METHOD
import com.Doctoor.app.utils.Constants.SERVICE_ID

/*Accept bundle from Adapter to assure the item is related to cart order or service*/
class ServicesHistoryItemVM : BaseListDataViewModel<MedicalServices.MyOrder>() {
    private lateinit var simple: MedicalServices.MyOrder
    lateinit var paymentType: String
    override fun setItem(item: MedicalServices.MyOrder) {
        this.simple = item
        paymentType = PaymentTypeEnum.getPaymentType(item.paymentMethodId).paymentType
        notifyChange()
    }

    override fun getItem() = simple

    override fun layoutRes() = R.layout.item_services_history
    override fun onItemClick(v: View, item: MedicalServices.MyOrder) {
    }

    fun onReorderClick() {
        val bundle = Bundle()
        bundle.putInt(ID, getItem().cityId)
        bundle.putInt(SERVICE_ID, getItem().serviceId)
        bundle.putString(COMMENTS, getItem().comments)
        navigatorHelper?.startFragmentWithBottomNavigation<MedicalServicesFragment>(
            MedicalServicesFragment::class.java.name, bundle = bundle
        )
    }
}
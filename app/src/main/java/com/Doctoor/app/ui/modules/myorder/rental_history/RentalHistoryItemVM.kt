package com.Doctoor.app.ui.modules.myorder.rental_history

import android.os.Bundle
import android.view.View
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseListDataViewModel
import com.Doctoor.app.model.response.MedicalServices
import com.Doctoor.app.ui.modules.myorder.PaymentStatusEnum
import com.Doctoor.app.ui.modules.myorder.PaymentTypeEnum
import com.Doctoor.app.ui.modules.select_equipment.rental_equipments.RentalEquipmentsFragment
import com.Doctoor.app.utils.Constants
import com.Doctoor.app.utils.Constants.COD_PAYMENT_METHOD
import com.Doctoor.app.utils.Constants.COMMENTS
import com.Doctoor.app.utils.Constants.EQUIPMENT_ID
import com.Doctoor.app.utils.Constants.ID
import com.Doctoor.app.utils.Constants.JAZZ_CASH_PAYMENT_METHOD

/*Accept bundle from Adapter to assure the item is related to cart order or service*/
class RentalHistoryItemVM : BaseListDataViewModel<MedicalServices.RentalHistory>() {
    private lateinit var simple: MedicalServices.RentalHistory
    lateinit var paymentType: String
    override fun setItem(item: MedicalServices.RentalHistory) {
        this.simple = item

        paymentType = PaymentTypeEnum.getPaymentType(item.paymentMethodId).paymentType

        notifyChange()
    }

    override fun getItem() = simple

    override fun layoutRes() = R.layout.item_my_rental_history
    override fun onItemClick(v: View, item: MedicalServices.RentalHistory) {
    }

    fun onReorderClick() {
        val bundle = Bundle()
        bundle.putInt(EQUIPMENT_ID, getItem().equipmentId)
        bundle.putString(COMMENTS, getItem().comment)
        bundle.putInt(ID, getItem().cityId)
        navigatorHelper?.startFragmentWithBottomNavigation<RentalEquipmentsFragment>(
            RentalEquipmentsFragment::class.java.name, bundle = bundle
        )
    }
}
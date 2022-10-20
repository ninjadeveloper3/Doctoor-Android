package com.Doctoor.app.ui.modules.service_type

import android.view.View
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseListDataViewModel
import com.Doctoor.app.model.Simple
import com.Doctoor.app.ui.modules.doctor_profile.DoctorProfileFragment

class MedicalServicesItemVM : BaseListDataViewModel<Simple>() {
    private lateinit var simple: Simple

    override fun setItem(item: Simple) {
        this.simple = item
        notifyChange()
    }

    override fun getItem() = simple

    override fun layoutRes() = R.layout.item_select_services

    override fun onItemClick(v: View, item: Simple) {
        navigatorHelper!!.startFragmentWithBottomNavigation<MedicalServicesFragment>(
            DoctorProfileFragment::class.java.name
        )
    }
}
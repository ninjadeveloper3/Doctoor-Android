package com.Doctoor.app.ui.modules.popular_services

import android.os.Bundle
import android.view.View
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseListDataViewModel
import com.Doctoor.app.model.response.Tests
import com.Doctoor.app.ui.modules.service_type.MedicalServicesFragment
import com.Doctoor.app.utils.Constants.IS_POPULAR_SERVICES
import com.Doctoor.app.utils.Constants.SERVICE_ID

class PopularServiceItemVM : BaseListDataViewModel<Tests.PopularService>() {
    private lateinit var item: Tests.PopularService
    override fun setItem(item: Tests.PopularService) {
        this.item = item
        notifyChange()
    }

    override fun getItem() = item

    override fun layoutRes() = R.layout.item_popular_service
    override fun onItemClick(v: View, item: Tests.PopularService) {
        /*do not pass city id and it will treat like popular home medical services*/

        val bundle = Bundle()
        bundle.putInt(SERVICE_ID, item.serviceId)
        bundle.putBoolean(IS_POPULAR_SERVICES, true)
        navigatorHelper?.startFragmentWithBottomNavigation<MedicalServicesFragment>(
            MedicalServicesFragment::class.java.name,
            bundle = bundle
        )
    }
}
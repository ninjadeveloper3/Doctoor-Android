package com.Doctoor.app.ui.modules.medicine.category

import android.os.Bundle
import android.view.View
import com.Doctoor.app.base.BaseRecyclerAdapterVM
import com.Doctoor.app.base.BaseViewModel
import com.Doctoor.app.data.remote.MedicineRestService
import com.Doctoor.app.model.response.Medicines
import com.Doctoor.app.rx.functions.PlainConsumer
import com.Doctoor.app.ui.interfaces.OnCallApiDone
import com.Doctoor.app.ui.modules.landing.LandingFragment
import com.Doctoor.app.ui.modules.medicine.products.SelectMedicineFragment
import com.Doctoor.app.ui.modules.upload_prescription.UploadPrescriptionFragment
import com.Doctoor.app.utils.Constants
import com.Doctoor.app.utils.Constants.ALL_CATEGORY
import com.Doctoor.app.utils.Constants.ID
import com.Doctoor.app.utils.Constants.IS_SEARCHE_ABLE
import javax.inject.Inject

class MedicineCategoryFragmentVM @Inject constructor(private val apiServices: MedicineRestService) :
    BaseRecyclerAdapterVM<Medicines.Category>() {

    fun onPrescription(view: View) {
        if (BaseViewModel.isLogin.value!!) {
            navigatorHelper?.startFragmentWithToolbar<UploadPrescriptionFragment>(
                UploadPrescriptionFragment::class.java.name
            )
        } else {
            navigateToLogin(view)
        }
    }

    public fun onOtherCategories() {
        val bundle = Bundle()
        bundle.putBoolean(ALL_CATEGORY, true)
        navigatorHelper?.startFragmentWithBottomNavigation<SelectMedicineFragment>(
            SelectMedicineFragment::class.java.name, bundle = bundle
        )
    }

    public fun onSearch() {
        val bundle = Bundle()
        bundle.putBoolean(IS_SEARCHE_ABLE, true)
        navigatorHelper?.startFragmentWithBottomNavigation<SelectMedicineFragment>(
            SelectMedicineFragment::class.java.name, bundle = bundle
        )
    }

    override fun callApi(page: Int, onCallApiDone: OnCallApiDone<Medicines.Category>) {

        execute(true, apiServices.medicinesCategories(),
            PlainConsumer { t ->
                if (t.data is ArrayList)
                /*if page is 1 it means the data is loading first time or it's loading from pull to refresh*/
                    setData(t.data, page == 1)
            }
        )
    }
}
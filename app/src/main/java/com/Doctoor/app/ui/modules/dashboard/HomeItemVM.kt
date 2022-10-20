package com.Doctoor.app.ui.modules.dashboard

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseListDataViewModel
import com.Doctoor.app.model.response.BaseModel
import com.Doctoor.app.model.response.Equipments
import com.Doctoor.app.model.response.Medicines
import com.Doctoor.app.model.response.Tests
import com.Doctoor.app.ui.modules.medicine.details.MedicineDetailsFragment
import com.Doctoor.app.ui.modules.product_details.ProductDetailsFragment
import com.Doctoor.app.ui.modules.test_details.TestDetailsFragment
import com.Doctoor.app.utils.Constants
import com.Doctoor.app.utils.Constants.DATA

class HomeItemVM : BaseListDataViewModel<BaseModel>() {
    var title = MutableLiveData<String>()
    var subtitle = MutableLiveData<String>()
    var url = MutableLiveData<String>()
    var isTest = MutableLiveData<Boolean>().apply { value = false }

    private lateinit var simple: BaseModel
    override fun setItem(item: BaseModel) {
        this.simple = item
        if (item is Equipments.Equipment) {
            title.value = item.equipment_name
            url.value = item.image
            subtitle.value = "Equipment"
            isTest.value = false
        }
        if (item is Medicines.Product) {
            title.value = item.medicineName
            url.value = item.medicineImage
            subtitle.value = "Medicine"
            isTest.value = false
        }
        if (item is Tests.Test) {
            title.value = item.test_name
            url.value = item.logo
            isTest.value = true
            subtitle.value = "Lab Test"
        }
        notifyChange()
    }

    override fun getItem() = simple
    override fun layoutRes() = R.layout.item_home
    override fun onItemClick(v: View, item: BaseModel) {
        val bundle = Bundle()

        if (item is Equipments.Equipment) {
            bundle.putParcelable(DATA, item)
            navigatorHelper?.startFragmentWithBottomNavigation<ProductDetailsFragment>(
                ProductDetailsFragment::class.java.name,
                bundle = bundle
            )
        }
        if (item is Medicines.Product) {
            bundle.putParcelable(DATA, item)
            navigatorHelper?.startFragmentWithBottomNavigation<MedicineDetailsFragment>(
                MedicineDetailsFragment::class.java.name,
                bundle = bundle
            )
        }
        if (item is Tests.Test) {
            bundle.putParcelable(DATA, item)
            navigatorHelper?.startFragmentWithBottomNavigation<TestDetailsFragment>(
                TestDetailsFragment::class.java.name,
                bundle = bundle
            )
        }
    }
}
package com.Doctoor.app.ui.modules.main

import android.os.Bundle
import android.view.View
import com.Doctoor.app.DoctoorApp
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseListDataViewModel
import com.Doctoor.app.base.BaseViewModel
import com.Doctoor.app.model.response.Medicines
import com.Doctoor.app.preference.DataStoreManager
import com.Doctoor.app.ui.modules.about_us.AboutUsFragment
import com.Doctoor.app.ui.modules.lab_reports.LabReportsFragment
import com.Doctoor.app.ui.modules.medicine.products.SelectMedicineFragment
import com.Doctoor.app.ui.modules.my_prescriptions.MyPrescriptionsFragment
import com.Doctoor.app.ui.modules.others.OtherCategoriesFragment
import com.Doctoor.app.utils.Constants.ID


class NavigationItemVM constructor(
    var dataStoreManager: DataStoreManager,
    var activity: MainActivity
) :
    BaseListDataViewModel<Medicines.Category>() {
    private lateinit var mItem: Medicines.Category
    override fun setItem(item: Medicines.Category) {
        this.mItem = item
        notifyChange()
    }

    override fun getItem() = mItem

    override fun layoutRes() = R.layout.item_navigation_view

    override fun onItemClick(v: View, item: Medicines.Category) {
        when (item.categoryName) {

            DoctoorApp.string(R.string.about_us) ->
                navigatorHelper?.startFragmentWithToolbar<AboutUsFragment>(
                    AboutUsFragment::class.java.name
                )
            DoctoorApp.string(R.string.my_lab_reports) -> {

                if (isLogin()) {
                    navigatorHelper?.startFragmentWithBottomNavigation<LabReportsFragment>(
                        LabReportsFragment::class.java.name
                    )
                } else {
                    activity.drawerHolder.closeDrawer()
                    navigateToLogin(view = v)
//                    navigatorHelper?.startFragment<LandingFragment>(LandingFragment::class.java.name, false)
                }
            }

            DoctoorApp.string(R.string.my_prescriptions) -> {

                if (isLogin()) {
                    navigatorHelper?.startFragmentWithBottomNavigation<MyPrescriptionsFragment>(
                        MyPrescriptionsFragment::class.java.name
                    )
                } else {
                    activity.drawerHolder.closeDrawer()
                    navigateToLogin(view = v)
//                    navigatorHelper?.startFragment<LandingFragment>(LandingFragment::class.java.name, false)
                }
            }
            DoctoorApp.string(R.string.other_categories) -> {
                navigatorHelper?.startFragmentWithBottomNavigation<OtherCategoriesFragment>(
                    OtherCategoriesFragment::class.java.name
                )
            }
            DoctoorApp.string(R.string.shop_by_categories) -> {

            }
            else -> {
                val bundle = Bundle()
                bundle.putInt(ID, item.id)
                navigatorHelper?.startFragmentWithBottomNavigation<SelectMedicineFragment>(
                    SelectMedicineFragment::class.java.name, bundle = bundle
                )
            }
        }

    }

    private fun isLogin(): Boolean {
        return BaseViewModel.isLogin.value!!
    }

}
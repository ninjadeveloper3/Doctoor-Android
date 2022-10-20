package com.Doctoor.app.ui.modules.medicine.category

import android.os.Bundle
import android.view.View
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseListDataViewModel
import com.Doctoor.app.model.response.Medicines
import com.Doctoor.app.ui.modules.medicine.products.SelectMedicineFragment
import com.Doctoor.app.utils.Constants.ID

class MedicineCategoryItemVM : BaseListDataViewModel<Medicines.Category>() {
    private lateinit var mItem: Medicines.Category
    override fun setItem(item: Medicines.Category) {
        this.mItem = item
        val categorySplit = item.categoryName.split(" ")
        if (categorySplit.size > 1) {
            if (categorySplit[1] == "&" && categorySplit.size >= 2) {
                categoryName = categorySplit[0] + " "+categorySplit[1] + "\n" + categorySplit[2]
            } else {
                categoryName = categorySplit[0] + "\n" + categorySplit[1]
            }
        } else {
            categoryName = item.categoryName
        }
        notifyChange()
    }

    override fun getItem() = mItem
    var categoryName = ""

    override fun layoutRes() = R.layout.item_medicines_category

    override fun onItemClick(v: View, item: Medicines.Category) {
        val bundle = Bundle()
        bundle.putInt(ID, item.id)
        navigatorHelper?.startFragmentWithBottomNavigation<SelectMedicineFragment>(
            SelectMedicineFragment::class.java.name, bundle = bundle
        )
    }
}
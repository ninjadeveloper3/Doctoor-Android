package com.Doctoor.app.ui.modules.select_lab

import android.os.Bundle
import android.view.View
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseListDataViewModel
import com.Doctoor.app.model.response.Tests
import com.Doctoor.app.ui.modules.select_lab.test.SelectTestFragment
import com.Doctoor.app.utils.Constants.CAT_ID
import com.Doctoor.app.utils.Constants.LAB_ID

class SelectLabCategoryItemVM : BaseListDataViewModel<Tests.TestCategory>() {
    private lateinit var item: Tests.TestCategory
    override fun setItem(item: Tests.TestCategory) {
        this.item = item
        notifyChange()
    }

    override fun getItem() = item

    override fun layoutRes() = R.layout.item_lab_categoty
    override fun onItemClick(v: View, item: Tests.TestCategory) {

        val bundle = Bundle()
        bundle.putInt(CAT_ID, item.testcategory_id)
        bundle.putInt(LAB_ID, item.lab_id)

        navigatorHelper?.startFragmentWithBottomNavigation<SelectTestFragment>(
            SelectTestFragment::class.java.name,
            bundle = bundle
        )
    }
}
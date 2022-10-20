package com.Doctoor.app.ui.modules.select_lab.test.popular_tests

import android.os.Bundle
import android.view.View
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseListDataViewModel
import com.Doctoor.app.model.response.Tests
import com.Doctoor.app.ui.modules.select_lab.test.SelectTestFragment
import com.Doctoor.app.utils.Constants.CAT_ID
import com.Doctoor.app.utils.Constants.LAB_ID

class TestCategoryItemVM : BaseListDataViewModel<Tests.TestPopularCategory>() {
    private lateinit var item: Tests.TestPopularCategory
    override fun setItem(item: Tests.TestPopularCategory) {
        this.item = item
        notifyChange()
    }

    override fun getItem() = item

    override fun layoutRes() = R.layout.item_popular_test_category
    override fun onItemClick(v: View, item: Tests.TestPopularCategory) {

        val bundle = Bundle()
        bundle.putInt(CAT_ID, item.testcategoryId)
        bundle.putInt(LAB_ID, item.labId)

        navigatorHelper?.startFragmentWithBottomNavigation<SelectTestFragment>(
            SelectTestFragment::class.java.name,
            bundle = bundle
        )
    }
}
package com.Doctoor.app.ui.modules.select_lab

import android.os.Bundle
import android.view.View
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseListDataViewModel
import com.Doctoor.app.model.response.Tests
import com.Doctoor.app.utils.Constants

class SelectLabItemVM : BaseListDataViewModel<Tests.Lab>() {
    private lateinit var item: Tests.Lab
    override fun setItem(item: Tests.Lab) {
        this.item = item
        notifyChange()
    }

    override fun getItem() = item

    override fun layoutRes() = R.layout.item_select_lab
    override fun onItemClick(v: View, item: Tests.Lab) {
        val bundle = Bundle()
        bundle.putInt(Constants.ID, item.id)
        navigatorHelper?.startFragmentWithBottomNavigation<SelectLabCategoryFragment>(
            SelectLabCategoryFragment::class.java.name,
            bundle = bundle
        )
    }
}
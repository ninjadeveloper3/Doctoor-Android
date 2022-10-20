package com.Doctoor.app.ui.modules.select_lab.test

import android.os.Bundle
import android.view.View
import com.Doctoor.app.DoctoorApp
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseListDataViewModel
import com.Doctoor.app.data.database.CartManager
import com.Doctoor.app.data.database.LabTestDao
import com.Doctoor.app.model.response.Medicines
import com.Doctoor.app.model.response.Tests
import com.Doctoor.app.rx.Task
import com.Doctoor.app.ui.modules.test_details.TestDetailsFragment
import com.Doctoor.app.utils.Constants.DATA
import com.Doctoor.app.utils.toastNow

class SelectTestItemVM(private val dao: LabTestDao, private var cartManager: CartManager) : BaseListDataViewModel<Tests.Test>() {
    private lateinit var item: Tests.Test

    override fun setItem(item: Tests.Test) {
        this.item = item
        Task.runSafely {
            isInCart = cartManager.isInCart(labTestDao = dao, item = item)
            notifyChange()
        }
    }

    override fun getItem() = item

    override fun layoutRes() = R.layout.item_select_test
    override fun onItemClick(v: View, item: Tests.Test) {
        val bundle = Bundle()
        bundle.putParcelable(DATA, item)
        navigatorHelper?.startFragmentWithBottomNavigation<TestDetailsFragment>(TestDetailsFragment::class.java.name, bundle = bundle)
    }

    fun onCartClick(item: Tests.Test) {
        isInCart = cartManager.insertOrDeleteLabTest(dao, item, !isInCart)
        notifyChange()
    }
}
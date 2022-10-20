package com.Doctoor.app.ui.modules.myorder

import android.os.Bundle
import android.view.View
import com.Doctoor.app.BR
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseViewModelFragment
import com.Doctoor.app.databinding.FragmentMyOrderPagerBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.ui.adapters.SectionsPagerAdapter
import com.Doctoor.app.ui.modules.myorder.rental_history.RentalEquipmentsHistoryFragment
import com.Doctoor.app.ui.modules.myorder.services_history.ServicesHistoryFragment
import com.Doctoor.app.utils.Constants.IS_PRODUCTS
import javax.inject.Inject

class MyOrderPagerFragment :
    BaseViewModelFragment<FragmentMyOrderPagerBinding, MyOrderPagerFragmentVM>() {
    @Inject
    lateinit var adapter: SectionsPagerAdapter

    override fun layoutRes() = R.layout.fragment_my_order_pager

    companion object {
        fun newInstance(): MyOrderPagerFragment {
            return MyOrderPagerFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<MyOrderPagerFragmentVM>

    override fun getToolBarTile() = getString(R.string.my_orders)

    override fun toolbarColor() = 0

    override fun getViewModel() = viewModel.get()

    override fun getBindingVariable() = BR.myOrderPagerFragmentVM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val orderBundle = Bundle()
        orderBundle.putBoolean(IS_PRODUCTS, true)

        val serviceBundle = Bundle()
        serviceBundle.putBoolean(IS_PRODUCTS, false)

        adapter.addFragmentInfo(MyOrderFragment::class.java.name, "Products", orderBundle)

        adapter.addFragmentInfo(ServicesHistoryFragment::class.java.name, "Services", serviceBundle)
        adapter.addFragmentInfo(
            RentalEquipmentsHistoryFragment::class.java.name,
            "Rental",
            serviceBundle
        )

        getViewModel().adapter.set(adapter)

    }
}
package com.Doctoor.app.ui.modules.product_details

import com.Doctoor.app.BR
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseViewModelFragment
import com.Doctoor.app.databinding.FragmentProductDetailsBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import javax.inject.Inject

class ProductDetailsFragment :
    BaseViewModelFragment<FragmentProductDetailsBinding, ProductDetailsVM>() {

    override fun layoutRes() = R.layout.fragment_product_details

    companion object {
        fun newInstance(): ProductDetailsFragment {
            return ProductDetailsFragment()
        }
    }

    override fun onResume() {
        super.onResume()
        getmViewModel().populateDetails()
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<ProductDetailsVM>

    override fun getToolBarTile() = getString(R.string.details)

    override fun toolbarColor() = 0

    override fun getViewModel() = viewModel.get()

    override fun getBindingVariable() = BR.productDetailsVM

}
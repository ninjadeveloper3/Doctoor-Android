package com.Doctoor.app.ui.modules.landing

import com.Doctoor.app.BR
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseViewModelFragment
import com.Doctoor.app.databinding.FragmentLandingBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import javax.inject.Inject

class LandingFragment : BaseViewModelFragment<FragmentLandingBinding, LandingFragmentVM>() {

    override fun layoutRes() = R.layout.fragment_landing

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<LandingFragmentVM>

    override fun getToolBarTile() = ""

    override fun toolbarColor() = 0

    override fun getViewModel() = viewModel.get()

    override fun getBindingVariable() = BR.landingFragmentVM
}
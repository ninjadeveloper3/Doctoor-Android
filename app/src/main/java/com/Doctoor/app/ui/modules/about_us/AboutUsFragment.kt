package com.Doctoor.app.ui.modules.about_us

import com.Doctoor.app.BR
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseViewModelFragment
import com.Doctoor.app.databinding.FragmentAboutUsBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import javax.inject.Inject

class AboutUsFragment : BaseViewModelFragment<FragmentAboutUsBinding, AboutUsVM>() {

    override fun layoutRes() = R.layout.fragment_about_us

    companion object {
        fun newInstance(): AboutUsFragment {
            return AboutUsFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<AboutUsVM>

    override fun getToolBarTile() = getString(R.string.about_us)

    override fun toolbarColor() = 0

    override fun getViewModel() = viewModel.get();

    override fun getBindingVariable() = BR.aboutUsVM
}
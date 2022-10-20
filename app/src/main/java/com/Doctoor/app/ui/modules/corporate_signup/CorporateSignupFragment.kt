package com.Doctoor.app.ui.modules.corporate_signup

import com.Doctoor.app.BR
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseViewModelFragment
import com.Doctoor.app.databinding.FragmentCorporateSignupBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import javax.inject.Inject

class CorporateSignupFragment : BaseViewModelFragment<FragmentCorporateSignupBinding, CorporateSignupVM>() {

    override fun getToolBarTile()=""

    override fun toolbarColor() = 0

    override fun layoutRes() = R.layout.fragment_corporate_signup

    companion object {
        fun newInstance(): CorporateSignupFragment {
            return CorporateSignupFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<CorporateSignupVM>

    override fun getViewModel() = viewModel.get()

    override fun getBindingVariable() = BR.corporateSignupFragmentVM

}
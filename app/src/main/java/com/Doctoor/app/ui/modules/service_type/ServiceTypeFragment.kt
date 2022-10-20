package com.Doctoor.app.ui.modules.service_type

import com.Doctoor.app.BR
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseViewModelFragment
import com.Doctoor.app.databinding.FragmentServiceTypeBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import javax.inject.Inject

class ServiceTypeFragment : BaseViewModelFragment<FragmentServiceTypeBinding, ServiceTypeVM>() {

    override fun layoutRes() = R.layout.fragment_service_type

    companion object {
        fun newInstance(): ServiceTypeFragment {
            return ServiceTypeFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<ServiceTypeVM>

    override fun getToolBarTile() = getString(R.string.services_type)

    override fun toolbarColor() = 0

    override fun getViewModel() = viewModel.get()

    override fun getBindingVariable() = BR.serviceTypeFragmentVM

}
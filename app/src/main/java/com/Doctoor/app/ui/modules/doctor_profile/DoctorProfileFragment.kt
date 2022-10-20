package com.Doctoor.app.ui.modules.doctor_profile

import com.Doctoor.app.BR
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseViewModelFragment
import com.Doctoor.app.databinding.FragmentDoctorProfileBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import javax.inject.Inject

class DoctorProfileFragment : BaseViewModelFragment<FragmentDoctorProfileBinding, DoctorProfileVM>() {

    override fun layoutRes() = R.layout.fragment_doctor_profile

    companion object {
        fun newInstance(): DoctorProfileFragment {
            return DoctorProfileFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<DoctorProfileVM>

    override fun getToolBarTile() = getString(R.string.details)

    override fun toolbarColor() = 0

    override fun getViewModel() = viewModel.get()

    override fun getBindingVariable() = BR.doctorProfileVM
}
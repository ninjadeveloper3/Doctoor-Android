package com.Doctoor.app.ui.modules.medicine.details

import android.os.Bundle
import android.view.View
import com.Doctoor.app.BR
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseViewModelFragment
import com.Doctoor.app.databinding.FragmentMedicineDetailsBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import javax.inject.Inject

class MedicineDetailsFragment :
    BaseViewModelFragment<FragmentMedicineDetailsBinding, MedicineDetailsVM>() {

    override fun layoutRes() = R.layout.fragment_medicine_details

    companion object {
        fun newInstance(): MedicineDetailsFragment {
            return MedicineDetailsFragment()
        }
    }

    override fun onResume() {
        super.onResume()
        getmViewModel().populateDetails()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<MedicineDetailsVM>

    override fun getToolBarTile() = getString(R.string.medicine_details)

    override fun toolbarColor() = 0

    override fun getViewModel() = viewModel.get()

    override fun getBindingVariable() = BR.medicineDetailsVM
}
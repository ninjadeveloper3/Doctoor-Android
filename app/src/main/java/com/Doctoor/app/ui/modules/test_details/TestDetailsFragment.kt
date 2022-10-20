package com.Doctoor.app.ui.modules.test_details

import com.Doctoor.app.BR
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseViewModelFragment
import com.Doctoor.app.databinding.FragmentTestDetailsBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import javax.inject.Inject

class TestDetailsFragment : BaseViewModelFragment<FragmentTestDetailsBinding, TestDetailsVM>() {

    override fun layoutRes() = R.layout.fragment_test_details

    companion object {
        fun newInstance(): TestDetailsFragment {
            return TestDetailsFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<TestDetailsVM>

    override fun onResume() {
        super.onResume()
        getmViewModel().populateDetails()
    }

    override fun getToolBarTile() = getString(R.string.test_details)

    override fun toolbarColor() = 0

    override fun getViewModel() = viewModel.get()

    override fun getBindingVariable() = BR.testDetailsVM

}
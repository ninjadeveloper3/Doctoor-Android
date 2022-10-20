package com.Doctoor.app.ui.modules.select_city

import android.os.Bundle
import android.view.View
import com.Doctoor.app.BR
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseViewModelFragment
import com.Doctoor.app.databinding.FragmentSelectCityBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.utils.validation.Validator
import kotlinx.android.synthetic.main.fragment_select_city.*
import javax.inject.Inject

class SelectCityFragment :
    BaseViewModelFragment<FragmentSelectCityBinding, SelectCityFragmentVM>() {

    override fun layoutRes() = R.layout.fragment_select_city

    companion object {
        fun newInstance(): SelectCityFragment {
            return SelectCityFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getmViewModel().validator = Validator(getViewDataBinding())

        changeCity.setOnClickListener {
            city_spinner.performClick()
        }
        keepCity.setOnClickListener {
            getViewModel().onSubmit()
        }

    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<SelectCityFragmentVM>

    override fun getToolBarTile() = getString(R.string.city)

    override fun toolbarColor() = 0
    override fun getViewModel() = viewModel.get()

    override fun getBindingVariable() = BR.selectCityFragmentVM
}
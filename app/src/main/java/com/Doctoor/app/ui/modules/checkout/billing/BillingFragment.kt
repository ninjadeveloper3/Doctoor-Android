package com.Doctoor.app.ui.modules.checkout.billing

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import com.Doctoor.app.BR
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseViewModelFragment
import com.Doctoor.app.databinding.FragmentShippingBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.utils.toast
import com.Doctoor.app.utils.validation.Validator
import kotlinx.android.synthetic.main.fragment_billing.*
import javax.inject.Inject

class BillingFragment : BaseViewModelFragment<FragmentShippingBinding, BillingFragmentVM>(),
    Validator.ValidationListener {

    override fun onValidationError() {
    }

    override fun onValidationSuccess() {
        toast("BillingFragment validated")
    }

    override fun layoutRes() = R.layout.fragment_billing

    companion object {
        fun newInstance(): BillingFragment {
            return BillingFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewModel().validator = Validator(getViewDataBinding())

        getViewModel().getIsProvinceError().observe(this, Observer { isError ->
            provinceSpinner.isEnableErrorLabel = isError
            if (isError) {
                provinceSpinner.error = "Please select a province"
            } else {
                provinceSpinner.error = ""
            }
        })
        getViewModel().getIsCityError().observe(this, Observer { isError ->
            citySpinner.isEnableErrorLabel = isError
            if (isError) {
                citySpinner.error = "Please select a city"
            } else {
                citySpinner.error = ""
            }
        })

        val cityAdapter =
            ArrayAdapter(context!!, R.layout.spinner_selected_item, getViewModel().cityNames)
        cityAdapter.setDropDownViewResource(R.layout.spinner_item)
        citySpinner.adapter = cityAdapter


        val provinceAdapter = ArrayAdapter<String>(
            context!!,
            R.layout.spinner_selected_item,
            resources.getStringArray(R.array.province_array)
        )
        provinceAdapter.setDropDownViewResource(R.layout.spinner_item)
        provinceSpinner.adapter = provinceAdapter
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<BillingFragmentVM>

    override fun getToolBarTile() = getString(R.string.checkout)

    override fun toolbarColor() = 0

    override fun getViewModel() = viewModel.get()

    override fun getBindingVariable() = BR.billingFragmentVM
}
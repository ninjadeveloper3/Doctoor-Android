package com.Doctoor.app.ui.modules.service_type

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.annotation.Nullable
import androidx.lifecycle.Observer
import com.Doctoor.app.BR
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseViewModelFragment
import com.Doctoor.app.data.source.State
import com.Doctoor.app.data.source.Status
import com.Doctoor.app.databinding.FragmentMedicalServicesBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.ui.interfaces.SpinnerHandler
import com.Doctoor.app.utils.AlertUtils
import com.Doctoor.app.utils.validation.Validator
import com.Doctoor.app.widget.progressbutton.OnAnimationEndListener
import kotlinx.android.synthetic.main.fragment_medical_services.*
import javax.inject.Inject

class MedicalServicesFragment :
    BaseViewModelFragment<FragmentMedicalServicesBinding, MedicalServicesFragmentVM>(),
    SpinnerHandler {

    override fun layoutRes() = R.layout.fragment_medical_services


    companion object {
        fun newInstance(): MedicalServicesFragment {
            return MedicalServicesFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<MedicalServicesFragmentVM>

    override fun toolbarColor() = 0

    override fun getViewModel() = viewModel.get()

    override fun getBindingVariable() = BR.medicalServicesFragmentVM

    override fun getToolBarTile() = getString(R.string.select_services)
    lateinit var servicesAdapter: ArrayAdapter<String>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getViewModel().validator = Validator(getViewDataBinding())

        getViewModel().spinnerHandler = this

        getViewModel().getIsServiceError().observe(this, Observer { isError ->
            servicesSpinner.isEnableErrorLabel = isError
            if (isError) {
                servicesSpinner.error = "Please select a service"
            } else {
                servicesSpinner.error = ""
            }
        })

        servicesAdapter = ArrayAdapter<String>(
            context!!,
            R.layout.spinner_selected_item,
            getViewModel().serviceNames
        )
        servicesAdapter.setDropDownViewResource(R.layout.spinner_item)
        servicesSpinner.adapter = servicesAdapter
    }

    override fun handleState(@Nullable state: State?) = when {
        state?.status == Status.LOADING -> {
            submit.revertAnimation()
            submit.startAnimation()
        }
        state?.status == Status.ERROR -> submit.revertAnimation(object : OnAnimationEndListener {
            override fun onAnimationEnd() {
                AlertUtils.showAlertDialog(getBaseActivity(), state.message)
            }
        })
        state?.status == Status.SUCCESS -> submit.revertAnimation(object : OnAnimationEndListener {
            override fun onAnimationEnd() {
                AlertUtils.showAlertDialog(getBaseActivity(), state.message) { _, i ->
                    navigatorHelper.finishActivity()
                }
            }

        })
        else -> submit.revertAnimation(object : OnAnimationEndListener {
            override fun onAnimationEnd() {
            }
        })
    }

    override fun onResponse(position: Int) {
        servicesSpinner.setSelection(position, true)
    }
}
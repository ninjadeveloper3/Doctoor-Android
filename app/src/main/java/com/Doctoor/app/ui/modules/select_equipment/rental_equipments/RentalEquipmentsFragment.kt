package com.Doctoor.app.ui.modules.select_equipment.rental_equipments

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
import com.Doctoor.app.databinding.FragmentRentalEquipmentsBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.ui.interfaces.SpinnerHandler
import com.Doctoor.app.utils.AlertUtils
import com.Doctoor.app.utils.validation.Validator
import com.Doctoor.app.widget.progressbutton.OnAnimationEndListener
import kotlinx.android.synthetic.main.fragment_rental_equipments.*
import javax.inject.Inject

class RentalEquipmentsFragment :
        BaseViewModelFragment<FragmentRentalEquipmentsBinding, RentalEquipmentsVM>(),
        SpinnerHandler {

    override fun layoutRes() = R.layout.fragment_rental_equipments

    companion object {
        fun newInstance(): RentalEquipmentsFragment {
            return RentalEquipmentsFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<RentalEquipmentsVM>

    override fun toolbarColor() = 0

    override fun getViewModel(): RentalEquipmentsVM = viewModel.get()

    override fun getBindingVariable() = BR.rentalEquipmentsVM

    override fun getToolBarTile() = getString(R.string.select_equipments)


    lateinit var servicesAdapter: ArrayAdapter<String>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getViewModel().validator = Validator(getViewDataBinding())

        getViewModel().spinnerHandler = this

        getViewModel().getisRentaError().observe(this, Observer { isError ->
            equipmentSpinner.isEnableErrorLabel = isError
            if (isError) {
                equipmentSpinner.error = "Please select an equipment"
            } else {
                equipmentSpinner.error = ""
            }
        })

        servicesAdapter = ArrayAdapter<String>(
                context!!,
                R.layout.spinner_selected_item,
                getViewModel().serviceNames
        )
        servicesAdapter.setDropDownViewResource(R.layout.spinner_item)
        equipmentSpinner.adapter = servicesAdapter
    }

    //  @CallSuper
    override fun handleState(@Nullable state: State?) = when {
        state?.status == Status.LOADING -> {
            rentalSubmit.revertAnimation()
            rentalSubmit.startAnimation()
        }
        state?.status == Status.ERROR -> rentalSubmit.revertAnimation(object :
                OnAnimationEndListener {
            override fun onAnimationEnd() {
                AlertUtils.showAlertDialog(getBaseActivity(), state.message)
            }
        })
        state?.status == Status.SUCCESS -> rentalSubmit.revertAnimation(object :
                OnAnimationEndListener {
            override fun onAnimationEnd() {
                AlertUtils.showAlertDialog(getBaseActivity(), state.message) { _, i ->
                    navigatorHelper.finishActivity()
                }
            }

        })
        else -> rentalSubmit.revertAnimation(object : OnAnimationEndListener {
            override fun onAnimationEnd() {
            }
        })
    }

    override fun onResponse(position: Int) {
        equipmentSpinner.setSelection(position, true)
    }
}
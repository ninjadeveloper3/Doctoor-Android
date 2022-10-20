package com.Doctoor.app.ui.modules.register_phone

import android.os.Bundle
import android.view.View
import androidx.annotation.Nullable
import com.Doctoor.app.BR
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseViewModelFragment
import com.Doctoor.app.data.source.State
import com.Doctoor.app.data.source.Status
import com.Doctoor.app.databinding.FragmentRegisterPhoneBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.ui.modules.code_verification.CodeVerificationFragment
import com.Doctoor.app.utils.AlertUtils
import com.Doctoor.app.utils.Constants
import com.Doctoor.app.utils.toast
import com.Doctoor.app.utils.validation.Validator
import com.Doctoor.app.widget.progressbutton.OnAnimationEndListener
import kotlinx.android.synthetic.main.fragment_code_verification.*
import javax.inject.Inject

class RegisterPhoneFragment :
    BaseViewModelFragment<FragmentRegisterPhoneBinding, RegisterPhoneFragmentVM>(),
    Validator.ValidationListener {

    override fun onValidationSuccess() {
        toast("RegisterPhoneFragment validated")
    }

    override fun onValidationError() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewModel().validator = Validator(getViewDataBinding())
    }

    override fun getToolBarTile() = ""

    override fun toolbarColor() = 0


    override fun layoutRes() = R.layout.fragment_register_phone

    companion object {
        fun newInstance(): RegisterPhoneFragment {
            return RegisterPhoneFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<RegisterPhoneFragmentVM>

    override fun getViewModel() = viewModel.get()

    override fun getBindingVariable() = BR.registerPhoneFragmentVM

    //  @CallSuper
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
                    val bundle = Bundle()
                    bundle.putInt(Constants.ID, getmViewModel().id.value!!)

                    navigatorHelper.startFragment<CodeVerificationFragment>(
                        CodeVerificationFragment::class.java.name,
                        false, bundle
                    )
                }
            }
        })

        else -> submit.revertAnimation(object : OnAnimationEndListener {
            override fun onAnimationEnd() {

            }
        })
    }

}
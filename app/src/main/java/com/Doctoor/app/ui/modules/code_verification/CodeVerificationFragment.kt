package com.Doctoor.app.ui.modules.code_verification

import android.os.Bundle
import android.view.View
import androidx.annotation.Nullable
import com.Doctoor.app.BR
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseViewModelFragment
import com.Doctoor.app.data.source.State
import com.Doctoor.app.data.source.Status
import com.Doctoor.app.databinding.FragmentCodeVerificationBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.utils.AlertUtils
import com.Doctoor.app.utils.toast
import com.Doctoor.app.utils.validation.Validator
import com.Doctoor.app.widget.progressbutton.OnAnimationEndListener
import kotlinx.android.synthetic.main.fragment_code_verification.*
import javax.inject.Inject

class CodeVerificationFragment :
    BaseViewModelFragment<FragmentCodeVerificationBinding, CodeVerificationVM>(),
    Validator.ValidationListener {
    override fun onValidationSuccess() {
        toast("CodeVerificationFragment validated")
    }

    override fun onValidationError() {
    }

    override fun layoutRes() = R.layout.fragment_code_verification

    companion object {
        fun newInstance(): CodeVerificationFragment {
            return CodeVerificationFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewModel().validator = Validator(getViewDataBinding())
    }

    override fun getToolBarTile() = ""

    override fun toolbarColor() = 0


    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<CodeVerificationVM>

    override fun getViewModel() = viewModel.get()

    override fun getBindingVariable() = BR.codeVerificationFragmentVM

    //  @CallSuper
    override fun handleState(@Nullable state: State?) = when {
        state?.status == Status.LOADING -> {
            submit.revertAnimation()
            submit.startAnimation()
        }
        state?.status == Status.SUCCESS -> submit.revertAnimation(object : OnAnimationEndListener {
            override fun onAnimationEnd() {
                AlertUtils.showAlertDialog(getBaseActivity(), state.message)
            }
        })
        state?.status == Status.ERROR -> submit.revertAnimation(object : OnAnimationEndListener {
            override fun onAnimationEnd() {
                AlertUtils.showAlertDialog(getBaseActivity(), state.message)
            }
        })
        else -> submit.revertAnimation(object : OnAnimationEndListener {
            override fun onAnimationEnd() {

                /*It will redirect user to the new password screen based on an argument*/
//                navigatorHelper.startFragment<NewPasswordFragment>(NewPasswordFragment::class.java.name, false)
            }
        })
    }
}
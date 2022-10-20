package com.Doctoor.app.ui.modules.new_password

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.annotation.Nullable
import androidx.lifecycle.Observer
import com.Doctoor.app.BR
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseViewModelFragment
import com.Doctoor.app.data.source.State
import com.Doctoor.app.data.source.Status
import com.Doctoor.app.databinding.FragmentNewPasswordBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.utils.AlertUtils
import com.Doctoor.app.utils.Constants
import com.Doctoor.app.utils.Constants.ID
import com.Doctoor.app.utils.toast
import com.Doctoor.app.utils.validation.Validator
import com.Doctoor.app.widget.progressbutton.OnAnimationEndListener
import kotlinx.android.synthetic.main.fragment_code_verification.*
import javax.inject.Inject

class NewPasswordFragment : BaseViewModelFragment<FragmentNewPasswordBinding, NewPasswordVM>(), Validator.ValidationListener {
    override fun onValidationError() {
    }

    override fun onValidationSuccess() {
        toast("NewPasswordFragment validated")
    }

    override fun getToolBarTile() = ""

    override fun toolbarColor() = 0


    override fun layoutRes() = R.layout.fragment_new_password
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewModel().validator = Validator(getViewDataBinding())

        val id = arguments?.getInt(ID, 0)

        getViewModel().id.postValue(id)

        getmViewModel().onSuccessMsg.observe(this, Observer { toast(getmViewModel().onSuccessMsg.value.toString()) })

    }

    companion object {
        fun newInstance(): NewPasswordFragment {
            return NewPasswordFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<NewPasswordVM>

    override fun getViewModel() = viewModel.get()

    override fun getBindingVariable() = BR.newPasswordFragmentVM

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
                AlertUtils.showAlertDialog(getBaseActivity(), state.message)
            }
        })
        else -> submit.revertAnimation(object : OnAnimationEndListener {
            override fun onAnimationEnd() {
            }
        })
    }
}
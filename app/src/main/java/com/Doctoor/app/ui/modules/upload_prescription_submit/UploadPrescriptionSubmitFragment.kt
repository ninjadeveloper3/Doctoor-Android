package com.Doctoor.app.ui.modules.upload_prescription_submit

import android.os.Bundle
import android.view.View
import androidx.annotation.Nullable
import com.Doctoor.app.BR
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseViewModelFragment
import com.Doctoor.app.data.source.State
import com.Doctoor.app.data.source.Status
import com.Doctoor.app.databinding.FragmentUploadPrescriptionSubmitBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.ui.modules.cart.CartFragment
import com.Doctoor.app.utils.AlertUtils
import com.Doctoor.app.utils.Constants.ID
import com.Doctoor.app.utils.Constants.IS_UPLOADED
import com.Doctoor.app.utils.Constants.PRESCRIPTION_ID
import com.Doctoor.app.utils.validation.Validator
import com.Doctoor.app.widget.progressbutton.OnAnimationEndListener
import kotlinx.android.synthetic.main.fragment_upload_prescription_submit.*
import javax.inject.Inject

class UploadPrescriptionSubmitFragment :
        BaseViewModelFragment<FragmentUploadPrescriptionSubmitBinding, UploadPrescriptionSubmitVM>() {

    override fun layoutRes() = R.layout.fragment_upload_prescription_submit

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<UploadPrescriptionSubmitVM>

    override fun getToolBarTile() = getString(R.string.upload_prescription)

    override fun toolbarColor() = 0

    override fun getViewModel() = viewModel.get();

    override fun getBindingVariable() = BR.uploadPrescriptionSubmitVM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewModel().validator = Validator(getViewDataBinding())

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
                AlertUtils.showAlertDialog(
                        getBaseActivity(),
                        state.message
                ) { _, i ->
                    if (getmViewModel().isFromCart) {
                        val bundle = Bundle()
                        bundle.putBoolean(IS_UPLOADED, true)
                        bundle.putInt(PRESCRIPTION_ID, getmViewModel().prescriptionId)
                        navigatorHelper.startFragmentWithToolbar<CartFragment>(
                                CartFragment::class.java.name, bundle = bundle
                        )
                    }
                    navigatorHelper.finishActivity()
                }
            }
        })
        else -> {
        }
    }

}
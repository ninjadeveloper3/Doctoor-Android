package com.Doctoor.app.ui.modules.easypaisa

import android.os.Bundle
import android.view.View
import androidx.annotation.Nullable
import com.Doctoor.app.BR
import com.Doctoor.app.BuildConfig.BASE_URL
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseViewModelFragment
import com.Doctoor.app.data.source.State
import com.Doctoor.app.data.source.Status
import com.Doctoor.app.databinding.FragmentPaymentBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.ui.interfaces.ConfirmOrder
import com.Doctoor.app.utils.AlertUtils
import com.Doctoor.app.utils.Constants
import com.Doctoor.app.utils.debug
import com.Doctoor.app.utils.validation.Validator
import com.Doctoor.app.widget.progressbutton.OnAnimationEndListener
import kotlinx.android.synthetic.main.fragment_easypaisa.*
import javax.inject.Inject

class EasypaisaPaymentFragment :
    BaseViewModelFragment<FragmentPaymentBinding, EasypaisaPaymentVM>(), ConfirmOrder {
    override fun onConfirm(
        message: String, isRental: Boolean,
        isService: Boolean
    ) {
        AlertUtils.showAlertDialog(
            getBaseActivity(),
            message
        ) { _, i ->
            if (isRental || isService) {
                getViewModel().gotoMainActivity()
            } else {
                getViewModel().emptyCart()
                getViewModel().gotoMainActivity()
            }
        }
    }


    override fun layoutRes() = R.layout.fragment_easypaisa

    companion object {
        fun newInstance(): EasypaisaPaymentFragment {
            return EasypaisaPaymentFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<EasypaisaPaymentVM>


    override fun toolbarColor() = 0

    override fun getViewModel() = viewModel.get()

    override fun getBindingVariable() = BR.easypaisaPaymentVM

    override fun getToolBarTile() = "EasyPaisa Account"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewModel().validator = Validator(getViewDataBinding())
        getViewModel().confirmOrder = this

        if (getmViewModel().isCCPayment.value!!) {

            getViewModel().confirmOrder = this

            paymentWebView.loadUrl(BASE_URL + "easyPasia?orderRefNum=${getmViewModel().orderNumber}&amount=${getmViewModel().amount}")
        }
//          paymentWebView.loadUrl(BASE_URL + "easyPasia?orderRefNum=${getmViewModel().orderNumber}&amount=${getmViewModel().amount}")
    }

    override fun handleState(@Nullable state: State?) = when {
        state?.status == Status.LOADING -> {
            continueBtn.revertAnimation()
            continueBtn.startAnimation()
        }
        state?.status == Status.ERROR -> continueBtn.revertAnimation(object :
            OnAnimationEndListener {
            override fun onAnimationEnd() {
                cancelBtn.visibility = View.VISIBLE
                AlertUtils.showAlertDialog(getBaseActivity(), state.message)
            }
        })
        state?.status == Status.SUCCESS -> continueBtn.revertAnimation(object :
            OnAnimationEndListener {
            override fun onAnimationEnd() {
            }

        })
        else -> continueBtn.revertAnimation(object : OnAnimationEndListener {
            override fun onAnimationEnd() {
            }
        })
    }

    override fun onResume() {
        super.onResume()

        if (getmViewModel().easypaisaResponse?.data != null) {
            val responseMessage = getmViewModel().easypaisaResponse?.responseHeader?.responseMessage
            when (getmViewModel().easypaisaResponse?.data!!.responseCode) {
                Constants.EASYPAISA_SUCCESS -> {
                    getmViewModel().successPayment(responseMessage.toString())
                }
                else -> {
                    getmViewModel().easypaisaResponse = null
                    AlertUtils.showAlertDialog(getBaseActivity(), responseMessage)
                }
            }
        }
    }
}
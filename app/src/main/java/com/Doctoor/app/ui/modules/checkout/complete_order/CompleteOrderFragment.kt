package com.Doctoor.app.ui.modules.checkout.complete_order

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.*
import androidx.annotation.Nullable
import androidx.lifecycle.Observer
import com.gdacciaro.iOSDialog.iOSDialogBuilder
import com.Doctoor.app.BR
import com.Doctoor.app.BuildConfig
import com.Doctoor.app.DoctoorApp
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseViewModelFragment
import com.Doctoor.app.data.source.State
import com.Doctoor.app.data.source.Status
import com.Doctoor.app.databinding.FragmentCompleteOrderBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.ui.interfaces.ConfirmOrder
import com.Doctoor.app.ui.modules.frame.FrameActivity
import com.Doctoor.app.utils.AlertUtils
import com.Doctoor.app.utils.Constants.CASH_PAID
import com.Doctoor.app.utils.Constants.EASY_PAISA_CC_METHOD
import com.Doctoor.app.utils.Constants.JAZZ_CASH_PAYMENT_METHOD
import com.Doctoor.app.utils.Constants.JAZ_CASH_CC_METHOD
import com.Doctoor.app.utils.debug
import com.Doctoor.app.utils.string
import com.Doctoor.app.widget.progressbutton.OnAnimationEndListener
import kotlinx.android.synthetic.main.fragment_complete_order.*
import javax.inject.Inject

class CompleteOrderFragment :
    BaseViewModelFragment<FragmentCompleteOrderBinding, CompleteOrderFragmentVM>(),
    ConfirmOrder {
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

    override fun layoutRes() = R.layout.fragment_complete_order

    companion object {
        fun newInstance(): CompleteOrderFragment {
            return CompleteOrderFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<CompleteOrderFragmentVM>

    override fun getToolBarTile() = getString(R.string.checkout)

    override fun toolbarColor() = 0

    override fun getViewModel() = viewModel.get()

    override fun getBindingVariable() = BR.completeOrderFragmentVM

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mWebViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                view.loadUrl("javascript:window.android.onUrlChange(window.location.href);")
//                debug("current URL $url")
                getViewModel().isProgressShow.value = false
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                val summary = "<html><body>Could not connect to the server.</body></html>"
                paymentWebView.loadData(summary, "text/html", null)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)

                getViewModel().isProgressShow.value = true
            }
        }
        getViewModel().confirmOrder = this
        paymentWebView.webViewClient = mWebViewClient

        paymentWebView.settings.loadsImagesAutomatically = true
        paymentWebView.settings.javaScriptEnabled = true
        paymentWebView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        paymentWebView.addJavascriptInterface(MyJavaScriptInterface(), "android")

        getViewModel().paymentUrl?.observe(this, Observer { url ->
            completeOrder.visibility = View.GONE
            stepsLayout.visibility = View.GONE
            getmViewModel().isWebViewStarted = true
            paymentWebView.loadUrl(url)
        })
    }

    internal inner class MyJavaScriptInterface {
        @JavascriptInterface
        fun onUrlChange(url: String) {
            debug("current URL $url")

            getmViewModel().currentUrl = url

            if (url == "${BuildConfig.BASE_URL}paymentResponse") {
                when (getmViewModel().paymentMethodId) {
                    EASY_PAISA_CC_METHOD -> {
                        getmViewModel().confirmFromEasyPaisaServer()
                    }
                    JAZZ_CASH_PAYMENT_METHOD, JAZ_CASH_CC_METHOD -> {
                        getmViewModel().successPayment(DoctoorApp.string(R.string.transaction_completed))
                    }
                }
            }

            if (url == BuildConfig.BASE_URL + "paymentResponseFail") {

                AlertUtils.showAlertDialog(
                    getBaseActivity(),
                    DoctoorApp.instance?.getString(R.string.transaction_Failed)!!
                ) { _, i ->
                    navigatorHelper.finishActivity()
                }

            }
            if (url == "https://payments.jazzcash.com.pk/CustomerPortal/ErrorPages/PaymentLoggedOut") {
                AlertUtils.showAlertDialog(
                    getBaseActivity(),
                    "Difficulty while placing order!",
                    "Your request cannot be serviced due to some technical issues.",
                    "Retry"
                ) { _, i ->
                    getmViewModel().getPaymentUrl(
                        getmViewModel().totalAmount,
                        getmViewModel().orderNumber
                    )
                }
            }
        }
    }

    override fun handleState(@Nullable state: State?) = when {
        state?.status == Status.LOADING -> {
            completeOrder.revertAnimation()
            completeOrder.startAnimation()
        }
        state?.status == Status.ERROR -> completeOrder.revertAnimation(object :
            OnAnimationEndListener {
            override fun onAnimationEnd() {
                AlertUtils.showAlertDialog(getBaseActivity(), state.message)
            }
        })
        state?.status == Status.SUCCESS -> completeOrder.revertAnimation(object :
            OnAnimationEndListener {
            override fun onAnimationEnd() {

            }

        })
        else -> completeOrder.revertAnimation(object : OnAnimationEndListener {
            override fun onAnimationEnd() {
            }
        })
    }

    override fun onBackPressed() {
        if (!getmViewModel().isWebViewStarted) {
            navigatorHelper.finishActivity()
        } else {
            /*AlertUtils.showAlertDialog(
                getBaseActivity(),
                "Warning",
                "Are you sure you want to cancel the payment?",
                "Cancel",
                "Continue",
                { _, i ->

                },
                { _, i ->
                    navigatorHelper.finishActivity()
                }
            )*/

            iOSDialogBuilder(FrameActivity.instance)
                .setTitle("Warning")
                .setSubtitle("Are you sure you want to cancel the payment?")
                .setBoldPositiveLabel(true)
                .setCancelable(false)
                .setPositiveListener("Cancel") { dialog ->
                    dialog.dismiss()
                }
                .setNegativeListener(
                    "Continue"
                ) { dialog ->
                    navigatorHelper.finishActivity()
                    dialog.dismiss()
                }
                .build().show()
        }
    }
}
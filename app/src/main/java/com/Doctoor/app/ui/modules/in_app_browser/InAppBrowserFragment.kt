package com.Doctoor.app.ui.modules.in_app_browser

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.Observer
import com.Doctoor.app.BR
import com.Doctoor.app.BuildConfig.BASE_CMS_URL
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseViewModelFragment
import com.Doctoor.app.databinding.FragmentInAppBrowserBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.utils.Constants.TITLE
import com.Doctoor.app.utils.debug
import kotlinx.android.synthetic.main.fragment_in_app_browser.*
import javax.inject.Inject

class InAppBrowserFragment :
    BaseViewModelFragment<FragmentInAppBrowserBinding, InAppBrowserFragmentVM>() {

    override fun layoutRes() = R.layout.fragment_in_app_browser

    companion object {
        fun newInstance(): InAppBrowserFragment {
            return InAppBrowserFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<InAppBrowserFragmentVM>

    override fun getToolBarTile(): String = arguments?.getString(TITLE)!!

    override fun toolbarColor() = 0

    override fun getViewModel(): InAppBrowserFragmentVM = viewModel.get();

    override fun getBindingVariable() = BR.inAppBrowserFragmentVM

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mWebViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {

            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return true
            }

        }
        inAppBrowser.webChromeClient = object : WebChromeClient() {

            override fun onReceivedTitle(view: WebView, title: String) {
                super.onReceivedTitle(view, title)

            }

        }

        inAppBrowser.webViewClient = mWebViewClient

        inAppBrowser.settings.loadsImagesAutomatically = true
        inAppBrowser.settings.javaScriptEnabled = true
        inAppBrowser.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        inAppBrowser.settings.cacheMode = WebSettings.LOAD_DEFAULT
        inAppBrowser.settings.allowContentAccess = true
        inAppBrowser.settings.safeBrowsingEnabled = true
        inAppBrowser.settings.setGeolocationEnabled(false)

        getViewModel().endPoint.observe(this, Observer { it ->
            val url = getAbsoluteUrl(it)
            inAppBrowser.loadUrl(url)
        })
    }

    private fun getAbsoluteUrl(url: String): String {
        debug(BASE_CMS_URL + url)
        return BASE_CMS_URL + url
    }
}
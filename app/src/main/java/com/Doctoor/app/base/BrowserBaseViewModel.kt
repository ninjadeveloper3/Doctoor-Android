package com.Doctoor.app.base

import android.os.Bundle
import com.Doctoor.app.ui.modules.in_app_browser.InAppBrowserFragment
import com.Doctoor.app.utils.Constants

abstract class BrowserBaseViewModel : BaseLoginViewModel() {
    override fun onFirsTimeUiCreate(bundle: Bundle?) {
    }

    fun onTerms() {
        val bundle = Bundle()
        bundle.putString(Constants.TITLE, "Terms & Conditions")
        bundle.putString(Constants.CMS_END_POINT, "Conditions")
        navigatorHelper?.startFragmentWithToolbar<InAppBrowserFragment>(
            InAppBrowserFragment::class.java.name, bundle = bundle
        )
    }

    fun onPrivacy() {
        val bundle = Bundle()
        bundle.putString(Constants.TITLE, "Privacy Policy")
        bundle.putString(Constants.CMS_END_POINT, "Privacy")
        navigatorHelper?.startFragmentWithToolbar<InAppBrowserFragment>(
            InAppBrowserFragment::class.java.name, bundle = bundle
        )
    }
}
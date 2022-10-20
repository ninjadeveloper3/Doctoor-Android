package com.Doctoor.app.ui.modules.landing

import com.Doctoor.app.base.BrowserBaseViewModel
import com.Doctoor.app.ui.modules.corporate_signup.CorporateSignupFragment
import com.Doctoor.app.ui.modules.login.LoginFragment
import com.Doctoor.app.ui.modules.signup.SignupFragment
import javax.inject.Inject

class LandingFragmentVM @Inject constructor() : BrowserBaseViewModel() {
    public fun onCorporateSignup() =
        navigatorHelper?.startFragment<CorporateSignupFragment>(
            CorporateSignupFragment::class.java.name,
            false
        )

    public fun onLogin() =
        navigatorHelper?.startFragment<LoginFragment>(LoginFragment::class.java.name, false)

    public fun onSignup() =
        navigatorHelper?.startFragment<SignupFragment>(SignupFragment::class.java.name, false)

}
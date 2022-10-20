package com.Doctoor.app.base

import android.os.Bundle
import com.Doctoor.app.ui.modules.social_login.FacebookHelper
import com.Doctoor.app.ui.modules.social_login.GoogleSignInHelper
import com.Doctoor.app.utils.validation.Validator
import com.onesignal.OneSignal


abstract class BaseLoginViewModel : BaseViewModel() {
    internal var validator: Validator? = null
    var facebookHelper: FacebookHelper? = null

    var googleSignInHelper: GoogleSignInHelper? = null

    override fun onFirsTimeUiCreate(bundle: Bundle?) {
    }

    fun registerDevice(email: String, id: Int) {
        OneSignal.sendTag("Email", email)
        OneSignal.sendTag("ID", id.toString())
        OneSignal.setSubscription(true)
        OneSignal.idsAvailable { userId, registrationId ->
        }
    }

    public fun onFbLogin() {
        facebookHelper?.connect()
    }

    public fun onGoogleLogin() = run {
        googleSignInHelper?.connect();
        googleSignInHelper?.signIn()
    }
}
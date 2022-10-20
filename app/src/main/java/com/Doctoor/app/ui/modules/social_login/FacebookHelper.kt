package com.Doctoor.app.ui.modules.social_login

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.widget.ShareDialog
import java.util.*


class FacebookHelper {
    private val permissions = Arrays.asList("public_profile ", "email")
    private var callbackManager: CallbackManager? = null
    private var loginManager: LoginManager? = null
    private var shareDialog: ShareDialog? = null
    private var activity: Activity? = null
    private var fragment: Fragment? = null
    private var fbSignInListener: OnFbSignInListener? = null

    /**
     * Interface to listen the Facebook login
     */
    interface OnFbSignInListener {
        fun OnFbSignInComplete(
            graphResponse: GraphResponse?,
            error: String?,
            accessToken: AccessToken? = null
        )
    }

    constructor (fragment: Fragment?, fbSignInListener: OnFbSignInListener?) {
        this.fragment = fragment
        this.fbSignInListener = fbSignInListener
    }

    constructor(
        callbackManager: CallbackManager?,
        loginManager: LoginManager?,
        shareDialog: ShareDialog?,
        activity: Activity?,
        fbSignInListener: OnFbSignInListener?
    ) {
        this.callbackManager = callbackManager
        this.loginManager = loginManager
        this.shareDialog = shareDialog
        this.activity = activity
        this.fbSignInListener = fbSignInListener
    }

    constructor(activity: Activity?, fbSignInListener: OnFbSignInListener?) {
        this.activity = activity
        this.fbSignInListener = fbSignInListener
    }

    fun connect() {
        callbackManager = CallbackManager.Factory.create()
        loginManager = LoginManager.getInstance()
        if (activity != null)
            loginManager!!.logInWithReadPermissions(activity, permissions)
        else
            loginManager?.logInWithReadPermissions(fragment, permissions)
        loginManager?.registerCallback(callbackManager!!,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {

                    if (AccessToken.getCurrentAccessToken() != null) {
                        LoginManager.getInstance().logOut()
                    }
                    callGraphAPI(loginResult.accessToken)
                }

                override fun onCancel() {
                    fbSignInListener!!.OnFbSignInComplete(null, "User cancelled.")
                }

                override fun onError(exception: FacebookException) {
                    if (exception is FacebookAuthorizationException) {
                        if (AccessToken.getCurrentAccessToken() != null) {
                            LoginManager.getInstance().logOut()
                        }
                    }
                    fbSignInListener?.OnFbSignInComplete(null, exception.message)
                }
            })

    }

    private fun callGraphAPI(accessToken: AccessToken) {
        val request = GraphRequest.newMeRequest(
            accessToken
        ) { `object`, response ->
            fbSignInListener?.OnFbSignInComplete(
                response,
                null,
                accessToken
            )
        }
        val parameters = Bundle()
        //Explicitly we need to specify the fields to get values else some values will be null.
        parameters.putString("fields", "id,email,first_name,gender,name")
        request.parameters = parameters
        request.executeAsync()
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * To share the details in facebook wall.
     *
     * @param title       of the content
     * @param description of the content
     * @param url         link to share.
     */
    fun shareOnFBWall(title: String, description: String, url: String) {
        if (ShareDialog.canShow(ShareLinkContent::class.java)) {
            val linkContent = ShareLinkContent.Builder()
                .setContentTitle(title)
                .setContentDescription(description)
                .setContentUrl(Uri.parse(url))
                .build()
            shareDialog!!.show(linkContent)
        }
    }

}
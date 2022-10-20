package com.Doctoor.app.ui.modules.social_login

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.Doctoor.app.utils.debug


class GoogleSignInHelper {

    val RC_SIGN_IN = 1008

    // GoogleSignInClient
    private var googleSignInClient: GoogleSignInClient? = null

    private var fragment: Fragment? = null

    /**
     * Google sign in Listener
     */
    private var onGoogleSignInListener: OnGoogleSignInListener? = null

    constructor(fragment: Fragment?, onGoogleSignInListener: OnGoogleSignInListener) {
        this.fragment = fragment
        this.onGoogleSignInListener = onGoogleSignInListener
    }

    /**
     * Connect to google
     */
    fun connect() {
        //Mention the GoogleSignInOptions to get the user profile and email.
        // Instantiate Google SignIn Client.
        googleSignInClient = fragment?.activity?.let {
            GoogleSignIn.getClient(
                it,
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build()
            )
        }
    }

    /**
     * Call this method in your onStart().If user have already signed in it will provide result directly.
     */
    fun onStart() {
//        val account = GoogleSignIn.getLastSignedInAccount(fragment?.activity)
//
//        if (account != null && onGoogleSignInListener != null) {
//            onGoogleSignInListener?.OnGSignInSuccess(account)
//        }
    }

    /**
     * To Init the sign in process.
     */
    fun signIn() {

        if (!isGooglePlayServicesAvailable(fragment?.activity)) {
            onGoogleSignInListener?.OnGSignInError("Google Play Equipments not installed")
            debug("Google Play Equipments not installed")
            return
        }

        signOut()

        val signInIntent = googleSignInClient?.signInIntent

        fragment?.startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    /**
     * To signOut from the application.
     */
    fun signOut() {
        if (googleSignInClient != null) {
            googleSignInClient?.signOut()
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            // Signed in successfully
            onGoogleSignInListener?.OnGSignInSuccess(account!!)

        } catch (e: ApiException) {
            onGoogleSignInListener?.OnGSignInError(
                GoogleSignInStatusCodes.getStatusCodeString(e.statusCode)
            )
        }

    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == RC_SIGN_IN && resultCode == Activity.RESULT_OK) {
            // The Task returned from this call is always completed, no need to attach a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    /**
     * Interface to listen the Google sign in
     */
    interface OnGoogleSignInListener {
        fun OnGSignInSuccess(googleSignInAccount: GoogleSignInAccount)

        fun OnGSignInError(error: String)
    }

    /*Check either google play services install on phone or not*/

    fun isGooglePlayServicesAvailable(activity: Activity?): Boolean {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val status = googleApiAvailability.isGooglePlayServicesAvailable(activity)
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(activity, status, 2404).show()
            }
            return false
        }
        return true
    }
}
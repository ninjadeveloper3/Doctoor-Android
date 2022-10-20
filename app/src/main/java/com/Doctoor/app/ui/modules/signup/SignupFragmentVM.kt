package com.Doctoor.app.ui.modules.signup

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.facebook.AccessToken
import com.facebook.GraphResponse
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.gson.Gson
import com.Doctoor.app.DoctoorApp
import com.Doctoor.app.base.BrowserBaseViewModel
import com.Doctoor.app.data.remote.UserRestService
import com.Doctoor.app.data.repository.UserRepository
import com.Doctoor.app.data.source.State
import com.Doctoor.app.model.request.UserRequest
import com.Doctoor.app.preference.DataStoreManager
import com.Doctoor.app.rx.functions.PlainConsumer
import com.Doctoor.app.ui.modules.code_verification.CodeVerificationFragment
import com.Doctoor.app.ui.modules.dashboard.HomeFragment
import com.Doctoor.app.ui.modules.frame.FrameActivity
import com.Doctoor.app.ui.modules.login.LoginFragment
import com.Doctoor.app.ui.modules.main.MainActivity
import com.Doctoor.app.ui.modules.register_phone.RegisterPhoneFragment
import com.Doctoor.app.ui.modules.social_login.FacebookHelper
import com.Doctoor.app.ui.modules.social_login.GoogleSignInHelper
import com.Doctoor.app.utils.Constants
import com.Doctoor.app.utils.Constants.ID
import com.Doctoor.app.utils.DateUtils
import com.Doctoor.app.utils.debug
import javax.inject.Inject


class SignupFragmentVM @Inject constructor(
        private val dataStoreManager: DataStoreManager,
        private val userRepo: UserRepository,
        private val apiService: UserRestService
) :
        BrowserBaseViewModel(), FacebookHelper.OnFbSignInListener,
        GoogleSignInHelper.OnGoogleSignInListener {

    var phone = MutableLiveData<String>()
        set(value) {
            notifyChange()
            field = value
        }
    var password = MutableLiveData<String>()
        set(value) {
            notifyChange()
            field = value
        }
    var email = MutableLiveData<String>()
        set(value) {
            notifyChange()
            field = value
        }
    var userName = MutableLiveData<String>()
        set(value) {
            notifyChange()
            field = value
        }
    var date = MutableLiveData<String>().apply { setValue(DateUtils.getCurrentDate()) }
    var timePickerDialogData = MutableLiveData<Boolean>()
    var gender = MutableLiveData<String>().apply { setValue("Male") }

    var onSuccessMsg = MutableLiveData<String>()
        set(value) {
            field = value
            notifyChange()
        }

    fun onDisplayTimePickerDialogClick() {
        timePickerDialogData.value = true
    }

    fun getTimePickerDialogData(): LiveData<Boolean> {
        return timePickerDialogData
    }

    fun setGender(gender: String) {
        this.gender.postValue(gender)
    }

    override fun onFirsTimeUiCreate(bundle: Bundle?) {
        phone.value = "+92"
    }

    fun onLogin() =
            navigatorHelper?.startFragment<LoginFragment>(LoginFragment::class.java.name, true)

    fun onSignup() {
        if (validator?.validate()!!) {
            signup()
        }
    }

    override fun OnGSignInSuccess(googleSignInAccount: GoogleSignInAccount) {
        debug("rawResponse ${Gson().toJson(googleSignInAccount)}}")

        val email = googleSignInAccount.email;
        val fullName = googleSignInAccount.displayName
        val socialId = googleSignInAccount.id
        val socialAccessToken = googleSignInAccount.id
        val request = UserRequest.SocialLogin(fullName, email, socialId, socialAccessToken)
        socialLogin(request)
    }

    var responseMessage = MutableLiveData<String>()
    override fun OnGSignInError(error: String) {
        debug("rawResponse $error")

    }

    override fun OnFbSignInComplete(
            graphResponse: GraphResponse?,
            error: String?,
            accessToken: AccessToken?
    ) {
        debug("rawResponse $error")
        debug("rawResponse $graphResponse")
        val facebookData = graphResponse?.jsonObject
        val fullName = facebookData?.getString("name")
        val socialId = facebookData?.getString("id")
        val socialAccessToken = accessToken?.token

        val email = if (facebookData?.has("email")!!) {
            facebookData.getString("email")
        } else {
            "$fullName@facebook.com"
        }

        val request = UserRequest.SocialLogin(fullName, email, socialId, socialAccessToken)
        socialLogin(request)
    }

    private fun socialLogin(request: UserRequest.SocialLogin) {

        execute(true, apiService.socialLogin(request), PlainConsumer { response ->
            responseMessage.value = response.responseHeader?.responseMessage
            when (response.user.id > 0) {
                true -> {
                    if (response.user.is_active == 1) {
                        dataStoreManager.startUserSession(response.user, response.user.token)
                        registerDevice(response.user.email, response.user.id)
                        navigateMainActivity(FrameActivity.instance)
                    } else {
                        val bundle = Bundle()
                        bundle.putInt(ID, response.user.id)
                        navigatePhoneReg(bundle, FrameActivity.instance)
                    }
                }
            }
        }, PlainConsumer { response -> responseMessage.postValue(response.message) })
    }

    private fun navigatePhoneReg(bundle: Bundle, activity: FrameActivity?) {
        val intent = Intent(DoctoorApp.instance, FrameActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra(Constants.FRAGMENT_CLASS, RegisterPhoneFragment::class.java.name)
        intent.putExtra(Constants.SHOW_TOOLBAR, false)
        intent.putExtra(Constants.EXTRA, bundle)
        activity?.startActivity(intent)
    }

    private fun navigateMainActivity(activity: FrameActivity?) {
        val intent = Intent(DoctoorApp.instance, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra(Constants.FRAGMENT_CLASS, HomeFragment::class.java.name)
        intent.putExtra(Constants.SHOW_TOOLBAR, true)
        intent.putExtra(Constants.SHOW_CART_MENU, true)
        activity?.startActivity(intent)
        activity?.finishAffinity()
    }

    private fun signup() {
        val phone = phone.value?.removePrefix("+")

        val request = UserRequest.SignUp(
                email.value,
                userName.value,
                gender.value,
                password.value,
                phone
        )
        execute(true, userRepo.signUp(request), PlainConsumer { response ->
            //            debug("Id is " + response.user.id)
            val responseMessage = response.responseHeader?.responseMessage

            when (response.user.id > 0) {
                true -> {
                    onSuccessMsg.value = responseMessage.toString();

                    val bundle = Bundle()
                    bundle.putInt(ID, response.user.id)
                    navigatorHelper?.startFragment<CodeVerificationFragment>(
                            CodeVerificationFragment::class.java.name,
                            false, bundle
                    )
                }
                else -> {
                    publishState(State.error(responseMessage.toString()))
                }
            }
        })
    }
}
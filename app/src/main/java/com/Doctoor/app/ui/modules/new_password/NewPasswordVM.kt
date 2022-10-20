package com.Doctoor.app.ui.modules.new_password

import android.os.Bundle
import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import com.Doctoor.app.base.BaseLoginViewModel
import com.Doctoor.app.data.repository.UserRepository
import com.Doctoor.app.data.source.State
import com.Doctoor.app.model.request.UserRequest
import com.Doctoor.app.preference.DataStoreManager
import com.Doctoor.app.rx.functions.PlainConsumer
import com.Doctoor.app.utils.debug
import com.Doctoor.app.utils.sup
import javax.inject.Inject

class NewPasswordVM @Inject constructor(
    private val userRepo: UserRepository,
    private val dataStoreManager: DataStoreManager
) : BaseLoginViewModel() {
    override fun onFirsTimeUiCreate(bundle: Bundle?) {
        super.onFirsTimeUiCreate(bundle)
        timer = object : CountDownTimer(59000, 1000) {

            override fun onTick(millisUntilFinished: Long) {

                timerString.value = "Resend 00:" + millisUntilFinished / 1000
                //here you can have your logic to set text to edittext
            }

            override fun onFinish() {
                timerString.value = "Resend 00:00"
                canResend.value = true;
            }
        }
        startTime()
    }

    private lateinit var timer: CountDownTimer
    var timerString = MutableLiveData<String>()
    var canResend = MutableLiveData<Boolean>().apply { value = false }

    var password = MutableLiveData<String>()
        set(value) {
            notifyChange()
            field = value
        }
    var id = MutableLiveData<Int>()
        set(value) {
            notifyChange()
            field = value
        }
    var code = MutableLiveData<String>()
        set(value) {
            notifyChange()
            field = value
        }

    var onSuccessMsg = MutableLiveData<String>()
        set(value) {
            field = value
            notifyChange()
        }

    fun startTime() {
        canResend.value = false
        timer.start()
    }

    fun onSubmit() {
        if (validator?.validate()!!) {

            val request = UserRequest.ResetPassword(
                code.value,
                password.value,
                id.value
            )

            execute(true, userRepo.resetPassword(request), PlainConsumer { response ->
                val responseMessage = response.responseHeader?.responseMessage

                when (response.user.id > 0) {
                    true -> {
                        onSuccessMsg.value = responseMessage.toString();
                        dataStoreManager.startUserSession(response.user, response.user.token)
                        navigatorHelper?.navigateMainActivity(true)
                    }
                    else -> {
                        publishState(State.error(responseMessage.toString()))
                    }
                }
            })
        }
    }

    public fun onResend() {
        startTime()
        val request = UserRequest.ResendCode(id.value)

        execute(true, userRepo.resendCode(request), PlainConsumer { response ->
            val responseMessage = response.responseHeader?.responseMessage
            publishState(State.success(responseMessage.toString()))
            code.postValue("")
            password.postValue("")

        })
    }
}
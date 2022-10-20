package com.Doctoor.app.ui.modules.code_verification

import android.os.Bundle
import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import com.Doctoor.app.base.BaseLoginViewModel
import com.Doctoor.app.data.repository.UserRepository
import com.Doctoor.app.data.source.State
import com.Doctoor.app.model.request.UserRequest
import com.Doctoor.app.preference.DataStoreManager
import com.Doctoor.app.rx.functions.PlainConsumer
import com.Doctoor.app.utils.Constants
import javax.inject.Inject


class CodeVerificationVM @Inject constructor(
    private val userRepo: UserRepository,
    private val dataStoreManager: DataStoreManager
) :
    BaseLoginViewModel() {
    lateinit var timer: CountDownTimer
    var timerString = MutableLiveData<String>()
    var canResend = MutableLiveData<Boolean>().apply { value = false }
    override fun onFirsTimeUiCreate(bundle: Bundle?) {
        super.onFirsTimeUiCreate(bundle)
        id.value = bundle?.getInt(Constants.ID, 0)

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

    fun onSubmit() {
        if (validator?.validate()!!) {

            val request = UserRequest.UserActivate(
                code.value,
                id.value
            )

            execute(true, userRepo.userActivate(request), PlainConsumer { response ->
                val responseMessage = response.responseHeader?.responseMessage
                when (response.user.id > 0) {
                    true -> {
                        if (response.user.is_active == 1) {
                            try {
                                registerDevice(response.user.email, response.user.id)
                                dataStoreManager.startUserSession(
                                    response.user,
                                    response.user.token
                                )
                                navigatorHelper?.navigateMainActivity(true)
                            } catch (e: Exception) {
                            }
                        } else {
                            publishState(State.error(responseMessage.toString()))
                        }
                    }
                    else -> {
                        publishState(State.error(responseMessage.toString()))
                    }
                }
            })
        }
    }

    fun startTime() {
        canResend.value = false
        timer.start()
    }

    fun onResend() {
        startTime()
        val request = UserRequest.ResendCode(id.value)

        execute(true, userRepo.resendCode(request), PlainConsumer { response ->
            val responseMessage = response.responseHeader?.responseMessage
            publishState(State.success(responseMessage.toString()))
            code.postValue("")
        })
    }
}
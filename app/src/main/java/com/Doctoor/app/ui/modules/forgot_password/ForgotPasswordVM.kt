package com.Doctoor.app.ui.modules.forgot_password

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.Doctoor.app.base.BaseLoginViewModel
import com.Doctoor.app.data.repository.UserRepository
import com.Doctoor.app.data.source.State
import com.Doctoor.app.model.request.UserRequest
import com.Doctoor.app.rx.functions.PlainConsumer
import com.Doctoor.app.ui.modules.new_password.NewPasswordFragment
import com.Doctoor.app.utils.Constants.ID
import com.Doctoor.app.utils.debug
import javax.inject.Inject

class ForgotPasswordVM @Inject constructor(private val userRepo: UserRepository) :
        BaseLoginViewModel() {

    override fun onFirsTimeUiCreate(bundle: Bundle?) {
        phone.value = "+92"
    }

    var phone = MutableLiveData<String>()
        set(value) {
            notifyChange()
            field = value
        }

    var id = MutableLiveData<Int>()
        set(value) {
            notifyChange()
            field = value
        }

    public fun onSubmit() {
        if (validator?.validate()!!) {
            val phone = phone.value?.removePrefix("+")
            val request = UserRequest.forgotPassword(phone)
            execute(true, userRepo.forgotPassword(request), PlainConsumer { response ->
                val responseMessage = response.responseHeader?.responseMessage
                when (response.user.id > 0) {
                    true -> {
                        id.value = response.user.id;
                        publishState(State.success(responseMessage.toString()))
                    }
                    else -> {
                        publishState(State.error(responseMessage.toString()))
                    }
                }
            })
        }
    }
}
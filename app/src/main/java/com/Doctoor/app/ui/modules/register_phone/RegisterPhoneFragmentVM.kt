package com.Doctoor.app.ui.modules.register_phone

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.Doctoor.app.base.BaseLoginViewModel
import com.Doctoor.app.data.repository.UserRepository
import com.Doctoor.app.data.source.State
import com.Doctoor.app.model.request.UserRequest
import com.Doctoor.app.rx.functions.PlainConsumer
import com.Doctoor.app.utils.Constants.ID
import com.Doctoor.app.utils.debug
import javax.inject.Inject

class RegisterPhoneFragmentVM @Inject constructor(private val userRepo: UserRepository) :
    BaseLoginViewModel() {
    override fun onFirsTimeUiCreate(bundle: Bundle?) {
        super.onFirsTimeUiCreate(bundle)
        id.value = bundle?.getInt(ID, 10)
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
    var gender = MutableLiveData<String>().apply { setValue("Male") }
    fun setGender(gender: String) {
        this.gender.postValue(gender)
    }

    public fun onSubmit() {
        if (validator?.validate()!!) {
            val phone = phone.value?.removePrefix("+")
            val request = UserRequest.RegisterPhone(gender.value, phone, id.value)
            execute(true, userRepo.registerPhone(request), PlainConsumer { response ->
                val responseMessage = response.responseHeader?.responseMessage

                when (response.user.id > 0) {
                    true -> {
                        publishState(State.success(responseMessage))
                    }
                    false -> {
                        publishState(State.error(responseMessage!!))
                    }
                }
            })
        }
    }

}
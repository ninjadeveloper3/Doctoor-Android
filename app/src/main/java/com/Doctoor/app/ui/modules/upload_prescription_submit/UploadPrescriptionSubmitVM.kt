package com.Doctoor.app.ui.modules.upload_prescription_submit

import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.Doctoor.app.base.BaseViewModel
import com.Doctoor.app.data.repository.MedicineRepository
import com.Doctoor.app.data.source.State
import com.Doctoor.app.rx.functions.PlainConsumer
import com.Doctoor.app.utils.Constants
import com.Doctoor.app.utils.Constants.IMAGES_URI
import com.Doctoor.app.utils.Constants.IMAGES_URL
import com.Doctoor.app.utils.validation.Validator
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class UploadPrescriptionSubmitVM @Inject constructor(private val medicineRepository: MedicineRepository) :
    BaseViewModel() {
    var filePath: String? = null
    var uri = MutableLiveData<Uri>()
    internal var validator: Validator? = null
    var isFromCart = false
    var phone = MutableLiveData<String>()
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

    override fun onFirsTimeUiCreate(bundle: Bundle?) {
        uri.value = bundle?.getParcelable(IMAGES_URI)
        filePath = bundle?.getString(IMAGES_URL)

        if (bundle != null) {
            if (bundle.containsKey(Constants.IS_FROM_CART)) {
                isFromCart = bundle.getBoolean(Constants.IS_FROM_CART)
            }
        }
        phone.value = "+92"
    }


    public fun onSubmit() {

        prescriptionId = 0

        if (validator?.validate()!!) {

            val phone = phone.value?.removePrefix("+")

            val file = File(filePath!!)
            val fileReqBody = RequestBody.create(MediaType.parse("image/*"), file)
            val part = MultipartBody.Part.createFormData("image", file.name, fileReqBody)
            val prescriptionFor: String
            if (isFromCart) {
                prescriptionFor = "medicine"
            } else {
                prescriptionFor = "manual"
            }

            execute(true, medicineRepository.uploadPrescription(
                part,
                RequestBody.create(MediaType.parse("text/plain"), userName.value!!),
                RequestBody.create(MediaType.parse("text/plain"), phone!!),
                RequestBody.create(MediaType.parse("text/plain"), email.value!!),
                prescriptionFor = RequestBody.create(
                    MediaType.parse("text/plain"),
                    prescriptionFor
                )
            ), PlainConsumer { response ->

                val responseMessage = response.responseHeader?.responseMessage

                when (response.data.id > 0) {
                    true -> {
                        prescriptionId = response.data.id
                        publishState(State.success(responseMessage))
                    }
                    else -> {
                        publishState(State.error(responseMessage.toString()))
                    }
                }

            })
        }
    }


}
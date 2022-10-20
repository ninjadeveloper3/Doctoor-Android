package com.Doctoor.app.data.repository

import com.Doctoor.app.data.remote.MedicineRestService
import com.Doctoor.app.data.source.RestHelper
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class MedicineRepository @Inject constructor(
    private val apiService: MedicineRestService
) : BaseRepository() {

    fun uploadPrescription(
        file: MultipartBody.Part,
        name: RequestBody,
        phone: RequestBody,
        email: RequestBody,
        prescriptionFor: RequestBody
    ) =
        RestHelper.createRemoteSourceMapper(
            apiService.uploadPrescription(
                file,
                email,
                phone,
                name,
                prescriptionFor
            ),
            null
        )

}
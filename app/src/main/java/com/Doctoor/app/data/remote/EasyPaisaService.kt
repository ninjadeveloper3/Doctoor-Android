package com.Doctoor.app.data.remote

import com.Doctoor.app.BuildConfig.CREDENTIALS
import com.Doctoor.app.model.request.ServiceRequest
import com.Doctoor.app.model.response.MedicalServices
import io.reactivex.Single
import retrofit2.http.*

interface EasyPaisaService {
    @Headers("Credentials:$CREDENTIALS")
    @POST("rest/v4/inquire-transaction")
    fun inquiryPayment(@Body service: ServiceRequest.ConfirmEasyPaisaCC): Single<MedicalServices.EasyPaisaCCPResponse>
}
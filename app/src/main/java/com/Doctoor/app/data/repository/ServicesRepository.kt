package com.Doctoor.app.data.repository

import com.Doctoor.app.data.remote.EasyPaisaService
import com.Doctoor.app.data.remote.ServicesRestService
import com.Doctoor.app.data.source.RestHelper
import com.Doctoor.app.model.request.ServiceRequest
import javax.inject.Inject

class ServicesRepository @Inject constructor(
    private val apiService: ServicesRestService,
    private val easyPaisaService: EasyPaisaService
) : BaseRepository() {

    fun orderService(request: ServiceRequest.OrderService) =
        RestHelper.createRemoteSourceMapper(apiService.orderService(request), null)

    fun orderRental(request: ServiceRequest.RentalEquipment) =
        RestHelper.createRemoteSourceMapper(apiService.orderRental(request), null)

    fun newOrder(request: ServiceRequest.Order) =
        RestHelper.createRemoteSourceMapper(apiService.newOrder(request), null)

    fun confirmOrder(request: ServiceRequest.ConfirmOrder) =
        RestHelper.createRemoteSourceMapper(apiService.confirmOrder(request), null)


    fun inquiryPayment(request: ServiceRequest.ConfirmEasyPaisaCC) =
        RestHelper.createRemoteSourceMapper(easyPaisaService.inquiryPayment(request), null)

}
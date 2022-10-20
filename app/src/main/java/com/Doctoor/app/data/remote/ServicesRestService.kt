package com.Doctoor.app.data.remote

import com.Doctoor.app.model.request.ServiceRequest
import com.Doctoor.app.model.response.BaseHeaderList
import com.Doctoor.app.model.response.BaseResponse
import com.Doctoor.app.model.response.Equipments
import com.Doctoor.app.model.response.MedicalServices
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ServicesRestService {
    @GET("medical_equipment")
    fun medicalEquipments(
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int = 1
    ): Single<BaseHeaderList<Equipments.Equipment>>

    @GET("in_demand_equipment")
    fun medicalEquipmentsInDemand(
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int = 1
    ): Single<BaseHeaderList<Equipments.Equipment>>

    @GET("get_all_rental")
    fun rentalMedicalEquipments(@Query("city_id") cityId: Int = 1): Single<BaseHeaderList<Equipments.RentalEquipment>>

    @GET("search_medical_equipment")
    fun searchEquipments(
        @Query("equipment_name") equipmentName: String = "",
        @Query("city_id") cityId: Int = 1
    ): Single<BaseHeaderList<Equipments.Equipment>>

    @GET("home_medical_services_list")
    fun medicalServicesList(@Query("city_id") cityId: Int = 1): Single<BaseHeaderList<MedicalServices.Service>>

    @GET("home_medical_services_list")
    fun medicalPopularServicesList(): Single<BaseHeaderList<MedicalServices.Service>>

    @POST("order_service")
    fun orderService(@Body service: ServiceRequest.OrderService): Single<MedicalServices.OrderService>

    @POST("order_rent")
    fun orderRental(@Body service: ServiceRequest.RentalEquipment): Single<MedicalServices.RentalEquipmentResponse>

    @GET("check_promotion")
    fun checkPromotion(@Query("coupon_code") couponCode: String = "123456"): Single<MedicalServices.CouponCodeResponse>

    @POST("new_order")
    fun newOrder(@Body service: ServiceRequest.Order): Single<MedicalServices.OrderResponse>

    @POST("confirmOrder")
    fun confirmOrder(@Body service: ServiceRequest.ConfirmOrder): Single<BaseResponse>

    @GET("order_history_med")
    fun historyMedicine(
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int = 1
    ): Single<BaseHeaderList<MedicalServices.MyOrder>>

    @GET("serviceOrderHistory ")
    fun serviceOrderHistory(
        @Query("offset") offset: Int = 0, @Query("limit") limit: Int = 10, @Query(
            "page"
        ) page: Int = 1
    ): Single<BaseHeaderList<MedicalServices.MyOrder>>

    @GET("rental_history ")
    fun rentalHistory(
        @Query("offset") offset: Int = 0, @Query("limit") limit: Int = 10, @Query(
            "page"
        ) page: Int = 1
    ): Single<BaseHeaderList<MedicalServices.RentalHistory>>

    @GET("paymentauth")
    fun doPayment(
        @Query("method") paymentMethod: Int = 1,
        @Query("order_id") orderNumber: String = "HM1569478051",
        @Query(
            "total_amount"
        ) totalAmount: Double = 1.0
    ): Single<MedicalServices.PaymentResponse>

    @GET("get_shipping_details")
    fun getShippingDetails(): Single<ServiceRequest.ShippingBillingDetails>

    @GET("get_billing_details")
    fun getBillingDetails(): Single<ServiceRequest.ShippingBillingDetails>

    @POST("easyPaisaPayment")
    fun easyPaisaPayment(@Body service: ServiceRequest.EasyPaisaPayment): Single<MedicalServices.EasyPaisaResponseResponse>

}
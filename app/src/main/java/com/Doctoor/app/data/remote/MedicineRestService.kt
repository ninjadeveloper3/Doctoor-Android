package com.Doctoor.app.data.remote

import com.Doctoor.app.model.response.BaseHeaderList
import com.Doctoor.app.model.response.Medicines
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*


interface MedicineRestService {

    @Multipart
    @POST("upload_prescription")
    fun uploadPrescription(
        @Part file: MultipartBody.Part, @Part("email") email: RequestBody
        , @Part("phone") phone: RequestBody
        , @Part("name") fullName: RequestBody,
        @Part("prescription_for") prescriptionFor: RequestBody
    ): Single<Medicines.PrescriptionResponse>

    @GET("medicines_list")
    fun medicinesList(
        @Query("page") page: Int = 1, @Query("limit") limit: Long = 30,
        @Query("category_id") id: Int = 0
    ): Single<BaseHeaderList<Medicines.Product>>

    @GET("get_medicine_categories")
    fun medicinesCategories(
        @Query("page") page: Int = -1, @Query("limit") limit: Long = 100
    ): Single<BaseHeaderList<Medicines.Category>>

    @GET("in_demand_med_cat")
    fun medicinesPopularCategories(
        @Query("page") page: Int = -1, @Query("limit") limit: Long = 100
    ): Single<BaseHeaderList<Medicines.Category>>

    @GET("search_medicine")
    fun searchMedicine(
        @Query("medicine_name") medicineName: String = ""
    ): Single<Medicines.SearchResponse>

    @GET("search_medicine")
    fun searchMedicineWithCategory(
        @Query("medicine_name") medicineName: String = "", @Query("cat_id") categoryId: String = ""
    ): Single<BaseHeaderList<Medicines.Product>>

    @GET("get_notifications")
    fun getNotifications(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Long = 30
    ): Single<BaseHeaderList<Medicines.Notification>>

    @GET("other_medicines")
    fun otherMedicinesList(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Long = 30
    ): Single<BaseHeaderList<Medicines.Product>>

    @GET("search_other_medicines")
    fun searchOtherMedicine(
        @Query("medicine_name") medicineName: String = ""
    ): Single<BaseHeaderList<Medicines.Product>>
}


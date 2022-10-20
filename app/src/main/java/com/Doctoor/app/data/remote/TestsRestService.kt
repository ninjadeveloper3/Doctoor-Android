package com.Doctoor.app.data.remote

import com.Doctoor.app.model.response.BaseHeaderList
import com.Doctoor.app.model.response.Tests
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface TestsRestService {
    @GET("labs_list")
    fun labList(
    ): Single<BaseHeaderList<Tests.Lab>>

    @GET("test_category_list")
    fun testCategoryList(
        @Query("page") page: Int = 1, @Query("limit") limit: Long = 100,
        @Query("lab_id") id: Int = 0
    ): Single<BaseHeaderList<Tests.TestCategory>>

    @GET("test_list")
    fun TestList(
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 30,
        @Query("lab_id") lab_id: Int = 0,
        @Query("category_id") category_id: Int = 0,
        @Query("page") page: Int = 1
    ): Single<BaseHeaderList<Tests.Test>>

    @GET("search_test")
    fun searchTest(
        @Query("test_name") testName: String = "",
        @Query("lab_id") labId: String = "1"
    ): Single<BaseHeaderList<Tests.Test>>

    @GET("my_prescriptions")
    fun myPrescriptions(): Single<BaseHeaderList<Tests.MyPrescription>>

    @GET("get_user_test_reports")
    fun labReports(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Long = 30
    ): Single<BaseHeaderList<Tests.LabReport>>

    @GET("in_demand_test_cat")
    fun testPopularCategoryList(): Single<BaseHeaderList<Tests.TestPopularCategory>>

    @GET("home_medical_services_list")
    fun homeMedicalServicesList(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Long = 30
    ): Single<BaseHeaderList<Tests.PopularService>>

}
package com.Doctoor.app.data.remote

import com.Doctoor.app.model.response.BaseHeaderList
import com.Doctoor.app.model.response.Home
import io.reactivex.Single
import retrofit2.http.GET

interface HomeRestService {
    @GET("banner_images")
    fun getBanners(): Single<BaseHeaderList<Home.BannerImage>>

    @GET("in_demand_products")
    fun getInDemandProducts(): Single<Home.InDemand>
}
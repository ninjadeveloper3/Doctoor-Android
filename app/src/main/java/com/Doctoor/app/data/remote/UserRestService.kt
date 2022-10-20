package com.Doctoor.app.data.remote

import com.Doctoor.app.model.request.UserRequest
import com.Doctoor.app.model.response.BaseHeaderList
import com.Doctoor.app.model.response.City
import com.Doctoor.app.model.response.Home
import com.Doctoor.app.model.response.UserResponse
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.http.*

interface UserRestService {

    @POST("signin")
    fun login(@Body login: UserRequest.Login): Single<UserResponse>

    @POST("signup")
    fun signup(@Body signup: UserRequest.SignUp): Single<UserResponse>

    @POST("user_activate")
    fun userActivate(@Body userActivate: UserRequest.UserActivate): Single<UserResponse>

    @POST("social_signup")
    fun socialLogin(@Body socialLogin: UserRequest.SocialLogin): Single<UserResponse>

    @POST("socialPhoneVerification")
    fun registerPhone(@Body registerPhone: UserRequest.RegisterPhone): Single<UserResponse>

    @POST("forgot_password")
    fun forgotPassword(@Body forgotPassword: UserRequest.forgotPassword): Single<UserResponse>

    @POST("reset_password")
    fun resetPassword(@Body resetPassword: UserRequest.ResetPassword): Single<UserResponse>

    @POST("resend_code")
    fun resendCode(@Body resendCode: UserRequest.ResendCode): Single<UserResponse>

    @GET("get_profile")
    fun getProfile(): Single<UserResponse>

    @GET("get_cities")
    fun getCities(): Single<BaseHeaderList<City>>

    @GET("about")
    fun getVersion(): Single<UserResponse.VersionResponse>

    @POST("edit_profile")
    fun editProfile(@Body editProfile: UserRequest.EditProfile): Single<UserResponse>

    @Multipart
    @POST("upload_profile_picture")
    fun uploadAvatar(@Part file: MultipartBody.Part): Single<UserResponse>

    @POST("update_player_id")
    fun updatePlayId(@Body socialLogin: UserRequest.PlayId): Single<Home.PlayId>
}
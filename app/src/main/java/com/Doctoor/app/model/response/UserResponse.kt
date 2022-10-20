package com.Doctoor.app.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class UserResponse(
    @SerializedName("data")
    val user: User
) : BaseResponse() {
    @Parcelize
    class User(
        @SerializedName("id") val id: Int,
        @SerializedName("full_name") val full_name: String,
        @SerializedName("email") val email: String,
        @SerializedName("email_verified_at") val email_verified_at: String,
        @SerializedName("player_id") val player_id: String,
        @SerializedName("social_access_token") val social_access_token: String,
        @SerializedName("social_id") val social_id: String,
        @SerializedName("login_type") val login_type: String,
        @SerializedName("phone") val phone: String,
        @SerializedName("gender") val gender: String,
        @SerializedName("dob") val dob: String,
        @SerializedName("is_active") val is_active: Int,
        @SerializedName("activation_code") val activation_code: Int,
        @SerializedName("role_id") val role_id: Int,
        @SerializedName("avatar") val avatar: String,
        @SerializedName("shipping_id") val shipping_id: String,
        @SerializedName("billing_id") val billing_id: String,
        @SerializedName("created_at") val created_at: String,
        @SerializedName("token") val token: String
    ) : BaseModel, Parcelable

    data class VersionResponse(
        @SerializedName("data")
        val appVersions: AppVersion?
    ) : BaseResponse()

    data class AppVersion(
        @SerializedName("androidApplicationVersion")
        val androidApplicationVersion: String?,
        @SerializedName("apiVersion")
        val apiVersion: String,
        @SerializedName("iosApplicationVersion")
        val iosApplicationVersion: String
    )

}

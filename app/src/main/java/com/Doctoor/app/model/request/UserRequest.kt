package com.Doctoor.app.model.request

import com.google.gson.annotations.SerializedName
import com.Doctoor.app.model.response.BaseModel

object UserRequest {
    data class Login(
        @SerializedName("password")
        val password: String,
        @SerializedName("phone")
        val phone: String
    ) : BaseModel

    data class SignUp(
        @SerializedName("email")
        val email: String?,
        @SerializedName("full_name")
        val fullName: String?,
        @SerializedName("gender")
        val gender: String?,
        @SerializedName("password")
        val password: String?,
        @SerializedName("phone")
        val phone: String?
    ) : BaseModel

    data class UserActivate(
        @SerializedName("code")
        val code: String?,
        @SerializedName("user_id")
        val user_id: Int?
    ) : BaseModel

    data class SocialLogin(
        @SerializedName("full_name")
        val fullName: String?,
        @SerializedName("email")
        val email: String?,
        @SerializedName("social_id")
        val social_id: String?,
        @SerializedName("social_access_token")
        val social_access_token: String?
    ) : BaseModel

    data class RegisterPhone(
        @SerializedName("gender")
        val gender: String?,
        @SerializedName("phone")
        val phone: String?,
        @SerializedName("id")
        val id: Int?
    ) : BaseModel

    data class forgotPassword(
        @SerializedName("phone")
        val phone: String?
    )

    data class ResetPassword(
        @SerializedName("reset_code")
        val resetCode: String?,
        @SerializedName("new_password")
        val newPassword: String?,
        @SerializedName("user_id")
        val id: Int?
    )

    data class ResendCode(
        @SerializedName("user_id")
        val id: Int?
    ) : BaseModel

    data class EditProfile(
        @SerializedName("full_name")
        val fullName: String?
    ) : BaseModel


    data class PlayId(
        @SerializedName("player_id")
        val playerId: String?
    ) : BaseModel

}


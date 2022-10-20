package com.Doctoor.app.data.repository

import com.Doctoor.app.data.remote.UserRestService
import com.Doctoor.app.data.source.RestHelper
import com.Doctoor.app.model.request.UserRequest
import okhttp3.MultipartBody
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: UserRestService
) : BaseRepository() {
    fun login(request: UserRequest.Login) =
        RestHelper.createRemoteSourceMapper(apiService.login(request), null)

    fun signUp(request: UserRequest.SignUp) =
        RestHelper.createRemoteSourceMapper(apiService.signup(request), null)

    fun userActivate(request: UserRequest.UserActivate) =
        RestHelper.createRemoteSourceMapper(apiService.userActivate(request), null)

    fun socialLogin(request: UserRequest.SocialLogin) =
        RestHelper.createRemoteSourceMapper(apiService.socialLogin(request), null)

    fun registerPhone(request: UserRequest.RegisterPhone) =
        RestHelper.createRemoteSourceMapper(apiService.registerPhone(request), null)

    fun forgotPassword(request: UserRequest.forgotPassword) =
        RestHelper.createRemoteSourceMapper(apiService.forgotPassword(request), null)

    fun resetPassword(request: UserRequest.ResetPassword) =
        RestHelper.createRemoteSourceMapper(apiService.resetPassword(request), null)

    fun resendCode(request: UserRequest.ResendCode) =
        RestHelper.createRemoteSourceMapper(apiService.resendCode(request), null)

    fun editProfile(request: UserRequest.EditProfile) =
        RestHelper.createRemoteSourceMapper(apiService.editProfile(request), null)

    fun uploadAvatar(request: MultipartBody.Part) =
        RestHelper.createRemoteSourceMapper(apiService.uploadAvatar(request), null)

    fun updatePlayerId(request: UserRequest.PlayId) =
        RestHelper.createRemoteSourceMapper(apiService.updatePlayId(request), null)

}
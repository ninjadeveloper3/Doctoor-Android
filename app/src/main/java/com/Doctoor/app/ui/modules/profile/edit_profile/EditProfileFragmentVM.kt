package com.Doctoor.app.ui.modules.profile.edit_profile

import android.app.Activity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.Doctoor.app.base.BaseLoginViewModel
import com.Doctoor.app.data.repository.UserRepository
import com.Doctoor.app.data.source.State
import com.Doctoor.app.imagepicker.ImagePicker
import com.Doctoor.app.model.request.UserRequest
import com.Doctoor.app.preference.DataStoreManager
import com.Doctoor.app.preference.UserDataStore
import com.Doctoor.app.rx.functions.PlainConsumer
import com.Doctoor.app.utils.permissions.PermissionHelper
import com.Doctoor.app.utils.permissions.constants.PermissionConstant
import com.Doctoor.app.utils.permissions.enums.PermissionEnum
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class EditProfileFragmentVM @Inject constructor(
    val userManager: DataStoreManager,
    private val userRepo: UserRepository,
    private val dataStore: UserDataStore,
    private val builder: ImagePicker.Builder,
    private val permissionHelper: PermissionHelper
) : BaseLoginViewModel() {

    var fullName = MutableLiveData<String>()
        set(value) {
            notifyChange()
            field = value
        }
    var gender = MutableLiveData<String>()
        set(value) {
            notifyChange()
            field = value
        }

    fun setGender(gender: String) {
        this.gender.postValue(gender)
    }

    var email = MutableLiveData<String>()
        set(value) {
            notifyChange()
            field = value
        }
    var avatar = MutableLiveData<String>()
        set(value) {
            field = value
            notifyChange()
        }

    var timePickerDialogData = MutableLiveData<Boolean>()

    fun onDisplayTimePickerDialogClick() {
        timePickerDialogData.postValue(true)
    }

    override fun onFirsTimeUiCreate(bundle: Bundle?) {
        fullName.value = userManager.currentUser?.full_name
        avatar.value = userManager.currentUser?.avatar
    }

    fun onSave() {
        if (validator?.validate()!!) {
            doEditProfile()
        }
    }

    fun onGalleryClick() {

        if (!permissionHelper.isPermissionGranted(PermissionEnum.WRITE_EXTERNAL_STORAGE)) {
            askPermission(PermissionConstant.WRITE_EXTERNAL_STORAGE)
        } else {
            builder.galleryOnly()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start { resultCode, data ->
                    if (resultCode == Activity.RESULT_OK) {
                        val fileUri = data?.data
                        val filePath: String = ImagePicker.getFilePath(data)!!
                        uploadProfilePic(filePath)
                    }
                }
        }
    }

    private fun askPermission(type: Int) {
        permissionHelper.askPermission(PermissionEnum.WRITE_EXTERNAL_STORAGE, type)
    }

    private fun uploadProfilePic(filePath: String) {
        val file = File(filePath)
        val fileReqBody = RequestBody.create(MediaType.parse("image/*"), file)
        val part =
            MultipartBody.Part.createFormData("image", file.name, fileReqBody)
        execute(true, userRepo.uploadAvatar(part), PlainConsumer { response ->
            val ResponseMessage = response.responseHeader?.responseMessage
            when (response.user.id > 0) {
                true -> {
                    dataStore.setUser(response.user)
                    avatar.value = response.user.avatar
                }
                else -> {
                    publishState(State.error(ResponseMessage.toString()))
                }
            }
        })
    }

    private fun doEditProfile() {

        val request = UserRequest.EditProfile(fullName.value)

        execute(true, userRepo.editProfile(request), PlainConsumer { response ->

            val responseMessage = response.responseHeader?.responseMessage

            when (response.user.id > 0) {
                true -> {
                    dataStore.setUser(response.user)
                    navigatorHelper?.finishActivity()
                }
                false -> {
                    publishState(State.error(responseMessage.toString()))
                }
            }
        })
    }
}
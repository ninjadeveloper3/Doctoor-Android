package com.Doctoor.app.ui.modules.upload_prescription

import android.app.Activity.RESULT_OK
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.Doctoor.app.base.BaseViewModel
import com.Doctoor.app.imagepicker.ImagePicker
import com.Doctoor.app.ui.modules.upload_prescription_submit.UploadPrescriptionSubmitFragment
import com.Doctoor.app.utils.Constants.IMAGES_URI
import com.Doctoor.app.utils.Constants.IMAGES_URL
import com.Doctoor.app.utils.Constants.IS_FROM_CART
import com.Doctoor.app.utils.OpenWhatsApp
import com.Doctoor.app.utils.permissions.PermissionHelper
import com.Doctoor.app.utils.permissions.constants.PermissionConstant
import com.Doctoor.app.utils.permissions.enums.PermissionEnum
import javax.inject.Inject


class UploadPrescriptionVM @Inject constructor(
    private val builder: ImagePicker.Builder,
    private val permissionHelper: PermissionHelper
) : BaseViewModel() {
    var isFromCart = false
    var phone = MutableLiveData<String>()
        set(value) {
            notifyChange()
            field = value
        }

    override fun onFirsTimeUiCreate(bundle: Bundle?) {
        phone.value = "+92"
        if (bundle != null) {
            if (bundle.containsKey(IS_FROM_CART)) {
                isFromCart = bundle.getBoolean(IS_FROM_CART)
            }
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
                    if (resultCode == RESULT_OK) {
                        val fileUri = data?.data
                        val filePath: String = ImagePicker.getFilePath(data)!!
                        navigateToNextScreen(fileUri, filePath)
                    }
                }
        }
    }

    fun onCameraClick() {
        if (!permissionHelper.isPermissionGranted(PermissionEnum.WRITE_EXTERNAL_STORAGE)) {
            askPermission(PermissionConstant.CAMERA)
        } else {
            builder.cameraOnly().compress(1024).maxResultSize(1080, 1920)
                .start { resultCode, data ->
                    if (resultCode == RESULT_OK) {
                        val fileUri = data?.data
                        val filePath: String = ImagePicker.getFilePath(data)!!
                        navigateToNextScreen(fileUri, filePath)
                    }
                }
        }
    }

    private fun askPermission(type: Int) {
        permissionHelper.askPermission(PermissionEnum.WRITE_EXTERNAL_STORAGE, type)
    }

    public fun navigateToNextScreen(imageUri: Uri?, url: String) {
        val bundle = Bundle()
        bundle.putParcelable(IMAGES_URI, imageUri)
        bundle.putString(IMAGES_URL, url)
        bundle.putBoolean(IS_FROM_CART, isFromCart)
        navigatorHelper?.startFragmentWithToolbar<UploadPrescriptionSubmitFragment>(
            UploadPrescriptionSubmitFragment::class.java.name, bundle = bundle
        )
        navigatorHelper?.finishActivity()
    }

    public fun openWhatsApp() = OpenWhatsApp()

    @BindingAdapter("android:src")
    fun setImageResource(imageView: ImageView, resource: Int) {
        imageView.setImageResource(resource)
    }

}
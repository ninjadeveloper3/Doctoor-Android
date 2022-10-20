package com.Doctoor.app.ui.modules.upload_prescription

import com.Doctoor.app.BR
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseViewModelFragment
import com.Doctoor.app.databinding.FragmentUploadPrescriptionBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.utils.permissions.PermissionHelper
import com.Doctoor.app.utils.permissions.PermissionManager
import com.Doctoor.app.utils.permissions.constants.PermissionConstant.CAMERA
import com.Doctoor.app.utils.permissions.enums.PermissionEnum
import com.Doctoor.app.utils.permissions.interfaces.FullCallback
import java.util.*
import javax.inject.Inject


class UploadPrescriptionFragment :
    BaseViewModelFragment<FragmentUploadPrescriptionBinding, UploadPrescriptionVM>(),
    FullCallback {

    @Inject
    protected lateinit var permissionHelper: PermissionHelper

    override fun result(
        permissionsGranted: ArrayList<PermissionEnum>,
        permissionsDenied: ArrayList<PermissionEnum>,
        permissionsDeniedForever: ArrayList<PermissionEnum>,
        permissionsAsked: ArrayList<PermissionEnum>, key: Int
    ) {
        when {
            permissionsGranted.size == permissionsAsked.size -> {
                if (key == CAMERA) getViewModel().onCameraClick()
                else getViewModel().onGalleryClick()
            }
            permissionsDeniedForever.size > 0 -> //If user answer "Never ask again" to a request for permission, you can redirect user to app settings, with an utils
                permissionHelper.showDialog(getString(R.string.permission_gallery_denied))
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        PermissionManager.handleResult(this, requestCode, permissions, grantResults)
    }


    override fun layoutRes() = R.layout.fragment_upload_prescription

    companion object {
        fun newInstance(): UploadPrescriptionFragment {
            return UploadPrescriptionFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<UploadPrescriptionVM>

    override fun getToolBarTile() = getString(R.string.upload_prescription)

    override fun toolbarColor() = 0

    override fun getViewModel() = viewModel.get();

    override fun getBindingVariable() = BR.uploadPrescriptionVM

}
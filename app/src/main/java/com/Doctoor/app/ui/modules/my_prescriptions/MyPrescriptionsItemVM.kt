package com.Doctoor.app.ui.modules.my_prescriptions

import android.app.DownloadManager
import android.content.Context.DOWNLOAD_SERVICE
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.os.Environment
import android.view.View
import com.facebook.FacebookSdk.getApplicationContext
import com.Doctoor.app.BuildConfig
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseListDataViewModel
import com.Doctoor.app.model.response.Tests
import com.Doctoor.app.utils.debug
import com.Doctoor.app.utils.permissions.PermissionHelper
import com.Doctoor.app.utils.permissions.constants.PermissionConstant
import com.Doctoor.app.utils.permissions.enums.PermissionEnum
import com.Doctoor.app.utils.toast
import java.io.File


class MyPrescriptionsItemVM constructor(private val permissionHelper: PermissionHelper) :
    BaseListDataViewModel<Tests.MyPrescription>() {
    private lateinit var myPresItem: Tests.MyPrescription
    var date = ""

    override fun setItem(item: Tests.MyPrescription) {
        date = item.createdAt
        this.myPresItem = item
        notifyChange()
    }

    override fun getItem() = myPresItem
    override fun layoutRes() = R.layout.item_my_prescriptions
    override fun onItemClick(v: View, item: Tests.MyPrescription) {
    }

    fun onDownload(item: Tests.MyPrescription) {

        if (!permissionHelper.isPermissionGranted(PermissionEnum.WRITE_EXTERNAL_STORAGE)) {
            askPermission(PermissionConstant.WRITE_EXTERNAL_STORAGE)
        } else {
            downloadReport()
        }
    }

    fun downloadReport() {
        val item: Tests.MyPrescription = getItem()
        if (item.file.isNullOrBlank()) {
            permissionHelper.showMessageDialog("Broken download link")
            return
        }
        val path = BuildConfig.BASE_PROFILE_URL.plus(item.file)
        val filePath =
            Environment.getExternalStorageDirectory().toString() + File.separator + item.file
        debug("filePath $filePath")
        if (File(filePath).exists()) {
            getApplicationContext().toast("File Already existed")
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(path))
            browserIntent.setFlags(FLAG_ACTIVITY_NEW_TASK)
            getApplicationContext().startActivity(browserIntent)
            return
        }

        val file = File(Environment.getExternalStorageDirectory(), item.file)

        val request = DownloadManager.Request(Uri.parse(path))
            .setTitle(item.name)// Title of the Download Notification
            .setDescription("Downloading Prescription")// Description of the Download Notification
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)// Visibility of the download Notification
            .setDestinationUri(Uri.fromFile(file))// Uri of the destination file
            .setRequiresCharging(false)// Set if charging is required to begin the download
            .setAllowedOverMetered(true)// Set if download is allowed on Mobile network
            .setAllowedOverRoaming(true)// Set if download is allowed on roaming network

        val downloadManager =
            getApplicationContext().getSystemService(DOWNLOAD_SERVICE) as DownloadManager?
        var downloadID =
            downloadManager!!.enqueue(request)// enqueue puts the download request in the queue.
    }

    private fun askPermission(type: Int) {
        permissionHelper.askPermission(PermissionEnum.WRITE_EXTERNAL_STORAGE, type)
    }
}
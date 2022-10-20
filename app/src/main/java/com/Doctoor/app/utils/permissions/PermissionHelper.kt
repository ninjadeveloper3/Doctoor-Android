package com.Doctoor.app.utils.permissions

import androidx.appcompat.app.AlertDialog
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseViewModelFragment
import com.Doctoor.app.utils.permissions.enums.PermissionEnum
import com.Doctoor.app.utils.permissions.interfaces.FullCallback

open class PermissionHelper(
    var fragment: BaseViewModelFragment<*, *>,
    var fullCallback: FullCallback
) {

    fun askPermission(permissionEnum: PermissionEnum, key: Int) {
        PermissionManager.Builder()
            .key(key)
            .permission(permissionEnum)
            .callback(fullCallback)
            .ask(fragment)
    }

    fun isPermissionGranted(enum: PermissionEnum): Boolean {
        return fragment.requireContext().let { PermissionUtils.isGranted(it, enum) }
    }

    /*
    "You have denied location permission, please allow permissions manually"
    * */

    fun showDialog(message: String) {
        fragment.requireContext().let {
            AlertDialog.Builder(it)
                .setTitle("Permission needed")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok) { dialogInterface, i ->
                    PermissionUtils.openApplicationSettings(
                        fragment.requireContext(),
                        R::class.java.getPackage()?.name
                    )
                }
                .setNegativeButton(android.R.string.cancel) { dialogInterface, i -> dialogInterface.dismiss() }
                .show()
        }
    }

    fun showMessageDialog(message: String) {
        fragment.requireContext().let {
            AlertDialog.Builder(it)
                .setTitle("Information")
                .setMessage(message)
                .setNegativeButton(android.R.string.cancel) { dialogInterface, i -> dialogInterface.dismiss() }
                .show()
        }
    }
}
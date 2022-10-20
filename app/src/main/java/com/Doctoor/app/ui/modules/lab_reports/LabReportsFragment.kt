package com.Doctoor.app.ui.modules.lab_reports

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.view.View
import androidx.databinding.ViewDataBinding
import com.Doctoor.app.BR
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseRecyclerViewFragment
import com.Doctoor.app.databinding.FragmentLabReportsBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.model.response.Tests
import com.Doctoor.app.navigation.NavigatorHelper
import com.Doctoor.app.ui.adapters.BaseRVFooterAdapter
import com.Doctoor.app.ui.adapters.BaseViewHolder
import com.Doctoor.app.ui.modules.notification.NotificationFragment
import com.Doctoor.app.utils.permissions.PermissionHelper
import com.Doctoor.app.utils.permissions.constants.PermissionConstant
import com.Doctoor.app.utils.permissions.enums.PermissionEnum
import com.Doctoor.app.utils.permissions.interfaces.FullCallback
import com.Doctoor.app.utils.toast
import java.util.ArrayList
import javax.inject.Inject
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor


class LabReportsFragment : BaseRecyclerViewFragment<FragmentLabReportsBinding, LabReportsFragmentVM, LabReportsFragment.Adapter, Tests.LabReport>(), FullCallback {
    override fun result(permissionsGranted: ArrayList<PermissionEnum>, permissionsDenied: ArrayList<PermissionEnum>, permissionsDeniedForever: ArrayList<PermissionEnum>, permissionsAsked: ArrayList<PermissionEnum>, key: Int) {
        when {
            permissionsGranted.size == permissionsAsked.size -> {
                adapter.getViewModel().downloadReport()
            }
            permissionsDeniedForever.size > 0 -> //If user answer "Never ask again" to a request for permission, you can redirect user to app settings, with an utils
                permissionHelper.showDialog(getString(R.string.permission_gallery_denied))
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<LabReportsFragmentVM>

    @Inject
    protected lateinit var permissionHelper: PermissionHelper

    override fun layoutRes() = R.layout.fragment_lab_reports

    companion object {
        fun newInstance(): NotificationFragment {
            return NotificationFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        downloadManager= getSystemService(this.context!!,)  as DownloadManager
    }

    override fun getToolBarTile() = getString(R.string.lab_reports)

    override fun toolbarColor() = 0

    override fun getViewModel() = viewModel.get()

    override fun getBindingVariable() = BR.labReportsFragmentVM

    class Adapter(context: Context, mValue: MutableList<Tests.LabReport>, mNavidation: NavigatorHelper, var permissionHelper: PermissionHelper) :
            BaseRVFooterAdapter<Tests.LabReport, LabReportsItemVM, Adapter.ViewHolder>(
                    context,
                    mValue,
                    mNavidation
            ) {
        override fun getLayoutId(viewType: Int) = getViewModel().layoutRes()
        override fun getViewHolder(
                view: View,
                viewModel: LabReportsItemVM,
                mDataBinding: ViewDataBinding, viewType: Int
        ): ViewHolder {
            val kotlinClass: KClass<ViewHolder> = ViewHolder::class
            val ctor = kotlinClass.primaryConstructor
            val myObject = ctor?.call(view, viewModel, mDataBinding) as ViewHolder
            return myObject
        }

        override fun getViewModel() = LabReportsItemVM(permissionHelper)
        override fun getVariableId() = BR.labReportsItemVM

        class ViewHolder(view: View, viewModel: LabReportsItemVM, mDataBinding: ViewDataBinding) :
                BaseViewHolder<Tests.LabReport, LabReportsItemVM>(view, viewModel, mDataBinding)
    }
}
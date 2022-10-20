package com.Doctoor.app.ui.modules.my_prescriptions

import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import com.Doctoor.app.BR
import javax.inject.Inject
import com.Doctoor.app.R
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.base.BaseFragment
import com.Doctoor.app.base.BaseRecyclerViewFragment
import com.Doctoor.app.databinding.FragmentMyPrescriptionsBinding
import com.Doctoor.app.model.response.Tests
import com.Doctoor.app.navigation.NavigatorHelper
import com.Doctoor.app.ui.adapters.BaseRVFooterAdapter
import com.Doctoor.app.ui.adapters.BaseViewHolder
import com.Doctoor.app.ui.modules.lab_reports.LabReportsItemVM
import com.Doctoor.app.utils.permissions.PermissionHelper
import com.Doctoor.app.utils.permissions.enums.PermissionEnum
import com.Doctoor.app.utils.permissions.interfaces.FullCallback
import java.util.ArrayList
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class MyPrescriptionsFragment :
    BaseRecyclerViewFragment<FragmentMyPrescriptionsBinding, MyPrescriptionsVM, MyPrescriptionsFragment.Adapter, Tests.MyPrescription>(),
    FullCallback {

    override fun result(
        permissionsGranted: ArrayList<PermissionEnum>,
        permissionsDenied: ArrayList<PermissionEnum>,
        permissionsDeniedForever: ArrayList<PermissionEnum>,
        permissionsAsked: ArrayList<PermissionEnum>,
        key: Int
    ) {
        when {
            permissionsGranted.size == permissionsAsked.size -> {
                adapter.getViewModel().downloadReport()
            }
            permissionsDeniedForever.size > 0 -> //If user answer "Never ask again" to a request for permission, you can redirect user to app settings, with an utils
                permissionHelper.showDialog(getString(R.string.permission_gallery_denied))
        }
    }


    override fun layoutRes() = R.layout.fragment_my_prescriptions

    @Inject
    protected lateinit var permissionHelper: PermissionHelper


    override fun getToolBarTile() = getString(R.string.my_prescriptions)

    override fun toolbarColor() = 0

    override fun getViewModel() = viewModel.get()

    override fun getBindingVariable() = BR.myPrescriptionsVM

    companion object {
        fun newInstance(): MyPrescriptionsFragment {
            return MyPrescriptionsFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<MyPrescriptionsVM>

    class Adapter(
        context: Context,
        mValue: MutableList<Tests.MyPrescription>,
        mNavidation: NavigatorHelper,
        var permissionHelper: PermissionHelper
    ) :
        BaseRVFooterAdapter<Tests.MyPrescription, MyPrescriptionsItemVM, Adapter.ViewHolder>(
            context,
            mValue,
            mNavidation
        ) {
        override fun getLayoutId(viewType: Int) = getViewModel().layoutRes()
        override fun getViewHolder(
            view: View,
            viewModel: MyPrescriptionsItemVM,
            mDataBinding: ViewDataBinding, viewType: Int
        ): ViewHolder {
            val kotlinClass: KClass<ViewHolder> = ViewHolder::class
            val ctor = kotlinClass.primaryConstructor
            val myObject = ctor?.call(view, viewModel, mDataBinding) as ViewHolder
            return myObject
        }

        override fun getViewModel() = MyPrescriptionsItemVM(permissionHelper)
        override fun getVariableId() = BR.myPrescriptionsItemVM

        class ViewHolder(
            view: View,
            viewModel: MyPrescriptionsItemVM,
            mDataBinding: ViewDataBinding
        ) :
            BaseViewHolder<Tests.MyPrescription, MyPrescriptionsItemVM>(
                view,
                viewModel,
                mDataBinding
            )
    }
}
package com.Doctoor.app.ui.modules.dashboard

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.Doctoor.app.BR
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseViewModelFragment
import com.Doctoor.app.databinding.FragmentMainBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.model.response.BaseModel
import com.Doctoor.app.navigation.NavigatorHelper
import com.Doctoor.app.ui.adapters.BaseRVAdapter
import com.Doctoor.app.ui.adapters.BaseViewHolder
import com.Doctoor.app.ui.adapters.SliderAdapter
import com.Doctoor.app.utils.permissions.PermissionManager
import com.Doctoor.app.widget.recyclerview.SpacesItemDecorationHome
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class HomeFragment : BaseViewModelFragment<FragmentMainBinding, HomeFragmentVM>() {

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<HomeFragmentVM>
    @Inject
    protected lateinit var sliderAdapter: SliderAdapter

    @Inject
    lateinit var adapter: Adapter

    override fun layoutRes() = R.layout.fragment_main

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewModel().sliderAdapter.set(sliderAdapter)
        getViewModel().adapter.set(adapter)
        refreshLayout?.setColorSchemeResources(
            R.color.sky_blue, R.color.blue,
            R.color.light_pink, R.color.pink
        )
        refreshLayout?.setOnRefreshListener {
            getmViewModel().fetchBanner()
            getmViewModel().fetchInDemand()
        }

//        if (!permissionHelper.isPermissionGranted(PermissionEnum.ACCESS_COARSE_LOCATION)!! || !permissionHelper.isPermissionGranted(PermissionEnum.ACCESS_FINE_LOCATION)!!) {
//            permissionHelper.askPermission(PermissionEnum.ACCESS_FINE_LOCATION, PermissionConstant.KEY_LOCATION)
////            permissionHelper?.askPermission(PermissionEnum.ACCESS_COARSE_LOCATION, PermissionConstant.KEY_LOCATION)
//        }
        recyclerView.addItemDecoration(SpacesItemDecorationHome(16, true))

    }

    override fun setLoading(loading: Boolean) {
        super.setLoading(loading)
        refreshLayout.isRefreshing=loading
    }

//    override fun result(permissionsGranted: ArrayList<PermissionEnum>, permissionsDenied: ArrayList<PermissionEnum>, permissionsDeniedForever: ArrayList<PermissionEnum>, permissionsAsked: ArrayList<PermissionEnum>) {
//        when {
//            permissionsGranted.size == permissionsAsked.size -> {
//                //Do some action
//                toast("Permission granted")
//            }
//            permissionsDeniedForever.size > 0 -> //If user answer "Never ask again" to a request for permission, you can redirect user to app settings, with an utils
//                permissionHelper.showDialog("You have denied location permission, please allow permissions manually")
//            else -> permissionHelper.askPermission(PermissionEnum.ACCESS_FINE_LOCATION, PermissionConstant.KEY_LOCATION)
//        }
//    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        PermissionManager.handleResult(this, requestCode, permissions, grantResults)
    }

    override fun getToolBarTile() = getString(R.string.home_medical)

    override fun toolbarColor() = 0

    override fun getViewModel() = viewModel.get();

    override fun getBindingVariable() = BR.homeFragmentVM

    class Adapter(mValue: MutableList<BaseModel>, mNavidation: NavigatorHelper) :
        BaseRVAdapter<BaseModel, HomeItemVM, Adapter.ViewHolder>(mValue, mNavidation) {
        override fun getLayoutId(viewType: Int) = getViewModel().layoutRes()
        override fun getViewHolder(
            view: View,
            viewModel: HomeItemVM,
            mDataBinding: ViewDataBinding, viewType: Int
        ): ViewHolder {
            val kotlinClass: KClass<ViewHolder> = ViewHolder::class
            val ctor = kotlinClass.primaryConstructor
            val myObject = ctor?.call(view, viewModel, mDataBinding) as ViewHolder
            return myObject
        }

        override fun getViewModel() = HomeItemVM()
        override fun getVariableId() = BR.homeItemVM

        class ViewHolder(view: View, viewModel: HomeItemVM, mDataBinding: ViewDataBinding) :
            BaseViewHolder<BaseModel, HomeItemVM>(view, viewModel, mDataBinding)
    }

}
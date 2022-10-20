package com.Doctoor.app.ui.modules.popular_services

import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import com.Doctoor.app.BR
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseRecyclerViewFragment
import com.Doctoor.app.databinding.FragmentPopularTestsBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.model.response.Tests
import com.Doctoor.app.navigation.NavigatorHelper
import com.Doctoor.app.ui.adapters.BaseRVFooterAdapter
import com.Doctoor.app.ui.adapters.BaseViewHolder
import javax.inject.Inject
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class PopularServicesFragment :
    BaseRecyclerViewFragment<FragmentPopularTestsBinding, PopularServicesVM, PopularServicesFragment.Adapter, Tests.PopularService>() {


    override fun layoutRes() = R.layout.fragment_popular_services

    override fun toolbarColor() = 0

    override fun getViewModel() = viewModel.get()

    override fun getBindingVariable() = BR.popularServicesVM

    override fun getToolBarTile() = getString(R.string.other_categories)

    companion object {
        fun newInstance(): PopularServicesFragment {
            return PopularServicesFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<PopularServicesVM>

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

    class Adapter(
        context: Context,
        mValue: MutableList<Tests.PopularService>,
        mNavidation: NavigatorHelper
    ) :
        BaseRVFooterAdapter<Tests.PopularService, PopularServiceItemVM, Adapter.ViewHolder>(
            context,
            mValue,
            mNavidation
        ) {
        override fun getLayoutId(viewType: Int) = getViewModel().layoutRes()
        override fun getViewHolder(
            view: View,
            viewModel: PopularServiceItemVM,
            mDataBinding: ViewDataBinding, viewType: Int
        ): ViewHolder {
            val kotlinClass: KClass<ViewHolder> = ViewHolder::class
            val ctor = kotlinClass.primaryConstructor
            val myObject = ctor?.call(view, viewModel, mDataBinding) as ViewHolder
            return myObject
        }

        override fun getViewModel() = PopularServiceItemVM()
        override fun getVariableId() = BR.popularServiceItemVM

        class ViewHolder(
            view: View,
            viewModel: PopularServiceItemVM,
            mDataBinding: ViewDataBinding
        ) :
            BaseViewHolder<Tests.PopularService, PopularServiceItemVM>(
                view,
                viewModel,
                mDataBinding
            )
    }
}
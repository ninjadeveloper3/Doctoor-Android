package com.Doctoor.app.ui.modules.myorder.services_history

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.Doctoor.app.BR
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseRecyclerViewFragment
import com.Doctoor.app.databinding.FragmentServicesHistoryFragmentBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.model.response.MedicalServices
import com.Doctoor.app.navigation.NavigatorHelper
import com.Doctoor.app.ui.adapters.BaseRVFooterAdapter
import com.Doctoor.app.ui.adapters.BaseViewHolder
import com.Doctoor.app.widget.MultiStateView
import javax.inject.Inject
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class ServicesHistoryFragment :
    BaseRecyclerViewFragment<FragmentServicesHistoryFragmentBinding, ServicesHistoryFragmentVM, ServicesHistoryFragment.Adapter, MedicalServices.MyOrder>() {

    override fun layoutRes() = R.layout.fragment_services_history_fragment

    companion object {
        fun newInstance(): ServicesHistoryFragment {
            return ServicesHistoryFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<ServicesHistoryFragmentVM>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stateLayout?.viewState = MultiStateView.ViewState.EMPTY
    }

    override fun getToolBarTile() = getString(R.string.my_orders)

    override fun toolbarColor() = 0

    override fun getViewModel() = viewModel.get()

    override fun getBindingVariable() = BR.servicesHistoryFragmentVM
    class Adapter(
        context: Context,
        mValue: MutableList<MedicalServices.MyOrder>,
        mNavidation: NavigatorHelper
    ) :
        BaseRVFooterAdapter<MedicalServices.MyOrder, ServicesHistoryItemVM, Adapter.ViewHolder>(
            context,
            mValue,
            mNavidation
        ) {
        var bundle: Bundle = Bundle()

        override fun getLayoutId(viewType: Int) = getViewModel().layoutRes()
        override fun getViewHolder(
            view: View,
            viewModel: ServicesHistoryItemVM,
            mDataBinding: ViewDataBinding, viewType: Int
        ): ViewHolder {
            val kotlinClass: KClass<ViewHolder> = ViewHolder::class
            val ctor = kotlinClass.primaryConstructor
            val myObject = ctor?.call(view, viewModel, mDataBinding) as ViewHolder
            return myObject
        }

        override fun getViewModel() = ServicesHistoryItemVM()

        override fun getVariableId() = BR.servicesHistoryItemVM

        class ViewHolder(
            view: View,
            viewModel: ServicesHistoryItemVM,
            mDataBinding: ViewDataBinding
        ) :
            BaseViewHolder<MedicalServices.MyOrder, ServicesHistoryItemVM>(
                view,
                viewModel,
                mDataBinding
            )
    }
}
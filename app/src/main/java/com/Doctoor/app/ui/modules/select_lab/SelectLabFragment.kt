package com.Doctoor.app.ui.modules.select_lab

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.Doctoor.app.BR
import com.Doctoor.app.DoctoorApp.Companion.string
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseRecyclerViewFragment
import com.Doctoor.app.databinding.FragmentSelectLabBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.model.response.Tests
import com.Doctoor.app.navigation.NavigatorHelper
import com.Doctoor.app.ui.adapters.BaseRVFooterAdapter
import com.Doctoor.app.ui.adapters.BaseViewHolder
import javax.inject.Inject
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class SelectLabFragment :
    BaseRecyclerViewFragment<FragmentSelectLabBinding, SelectLabFragmentVM, SelectLabFragment.Adapter, Tests.Lab>() {

    override fun getToolBarTile() = string(R.string.select_lab)

    override fun toolbarColor() = 0

    override fun layoutRes() = R.layout.fragment_select_lab

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true);
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<SelectLabFragmentVM>

    override fun getViewModel() = viewModel.get()

    override fun getBindingVariable() = BR.selectLabFragmentVM

    class Adapter(context: Context, mValue: MutableList<Tests.Lab>, mNavidation: NavigatorHelper) :
        BaseRVFooterAdapter<Tests.Lab, SelectLabItemVM, Adapter.ViewHolder>(
            context,
            mValue,
            mNavidation
        ) {
        override fun getLayoutId(viewType: Int) = getViewModel().layoutRes()
        override fun getViewHolder(
            view: View,
            viewModel: SelectLabItemVM,
            mDataBinding: ViewDataBinding, viewType: Int
        ): ViewHolder {
            val kotlinClass: KClass<ViewHolder> = ViewHolder::class
            val ctor = kotlinClass.primaryConstructor
            val myObject = ctor?.call(view, viewModel, mDataBinding) as ViewHolder
            return myObject
        }

        override fun getViewModel() = SelectLabItemVM()

        override fun getVariableId() = BR.selectLabItemVM

        class ViewHolder(
            view: View,
            viewModel: SelectLabItemVM,
            mDataBinding: ViewDataBinding
        ) :
            BaseViewHolder<Tests.Lab, SelectLabItemVM>(view, viewModel, mDataBinding)
    }

}
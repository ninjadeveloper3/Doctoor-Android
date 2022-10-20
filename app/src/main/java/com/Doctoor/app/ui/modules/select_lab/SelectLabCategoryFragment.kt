package com.Doctoor.app.ui.modules.select_lab

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.Doctoor.app.BR
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseRecyclerViewFragment
import com.Doctoor.app.databinding.FragmentSelectLabCategoryBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.model.response.Tests
import com.Doctoor.app.navigation.NavigatorHelper
import com.Doctoor.app.ui.adapters.BaseRVFooterAdapter
import com.Doctoor.app.ui.adapters.BaseViewHolder
import javax.inject.Inject
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class SelectLabCategoryFragment :
    BaseRecyclerViewFragment<FragmentSelectLabCategoryBinding, SelectLabCategoryFragmentVM, SelectLabCategoryFragment.Adapter, Tests.TestCategory>() {

    override fun layoutRes() = R.layout.fragment_select_lab_category

    companion object {
        fun newInstance(): SelectLabCategoryFragment {
            return SelectLabCategoryFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true);
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<SelectLabCategoryFragmentVM>

    override fun getToolBarTile() = getString(R.string.select_category)

    override fun toolbarColor() = 0

    override fun getViewModel() = viewModel.get()

    override fun getBindingVariable() = BR.selectLabCategoryFragmentVM

    class Adapter(
        context: Context,
        mValue: MutableList<Tests.TestCategory>,
        mNavidation: NavigatorHelper
    ) :
        BaseRVFooterAdapter<Tests.TestCategory, SelectLabCategoryItemVM, Adapter.ViewHolder>(
            context,
            mValue,
            mNavidation
        ) {
        override fun getLayoutId(viewType: Int) = getViewModel().layoutRes()
        override fun getViewHolder(
            view: View,
            viewModel: SelectLabCategoryItemVM,
            mDataBinding: ViewDataBinding, viewType: Int
        ): ViewHolder {
            val kotlinClass: KClass<ViewHolder> = ViewHolder::class
            val ctor = kotlinClass.primaryConstructor
            val myObject = ctor?.call(view, viewModel, mDataBinding) as ViewHolder
            return myObject
        }

        override fun getViewModel() = SelectLabCategoryItemVM()
        override fun getVariableId() = BR.itemViewModel

        class ViewHolder(
            view: View,
            viewModel: SelectLabCategoryItemVM,
            mDataBinding: ViewDataBinding
        ) :
            BaseViewHolder<Tests.TestCategory, SelectLabCategoryItemVM>(
                view,
                viewModel,
                mDataBinding
            )
    }
}
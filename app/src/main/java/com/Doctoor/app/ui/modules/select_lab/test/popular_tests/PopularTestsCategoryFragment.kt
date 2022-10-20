package com.Doctoor.app.ui.modules.select_lab.test.popular_tests

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

class PopularTestsCategoryFragment :
    BaseRecyclerViewFragment<FragmentPopularTestsBinding, PopularTestsCategoryFragmentVM, PopularTestsCategoryFragment.Adapter, Tests.TestPopularCategory>() {

    override fun layoutRes() = R.layout.fragment_popular_tests

    companion object {
        fun newInstance(): PopularTestsCategoryFragment {
            return PopularTestsCategoryFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<PopularTestsCategoryFragmentVM>

    override fun toolbarColor() = 0

    override fun getViewModel() = viewModel.get()

    override fun getBindingVariable() = BR.popularTestsFragmentVM

    override fun getToolBarTile() = getString(R.string.other_categories)

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

    class Adapter(
        context: Context,
        mValue: MutableList<Tests.TestPopularCategory>,
        mNavidation: NavigatorHelper
    ) :
        BaseRVFooterAdapter<Tests.TestPopularCategory, TestCategoryItemVM, Adapter.ViewHolder>(
            context,
            mValue,
            mNavidation
        ) {
        override fun getLayoutId(viewType: Int) = getViewModel().layoutRes()
        override fun getViewHolder(
            view: View,
            viewModel: TestCategoryItemVM,
            mDataBinding: ViewDataBinding, viewType: Int
        ): ViewHolder {
            val kotlinClass: KClass<ViewHolder> = ViewHolder::class
            val ctor = kotlinClass.primaryConstructor
            val myObject = ctor?.call(view, viewModel, mDataBinding) as ViewHolder
            return myObject
        }

        override fun getViewModel() = TestCategoryItemVM()
        override fun getVariableId() = BR.itemViewModel

        class ViewHolder(
            view: View,
            viewModel: TestCategoryItemVM,
            mDataBinding: ViewDataBinding
        ) :
            BaseViewHolder<Tests.TestPopularCategory, TestCategoryItemVM>(
                view,
                viewModel,
                mDataBinding
            )
    }
}
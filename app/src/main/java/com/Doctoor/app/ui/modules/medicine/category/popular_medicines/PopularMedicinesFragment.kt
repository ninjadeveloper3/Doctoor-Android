package com.Doctoor.app.ui.modules.medicine.category.popular_medicines

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
import com.Doctoor.app.databinding.FragmentPopularMedicinesBinding
import com.Doctoor.app.model.response.Medicines
import com.Doctoor.app.navigation.NavigatorHelper
import com.Doctoor.app.ui.adapters.BaseRVFooterAdapter
import com.Doctoor.app.ui.adapters.BaseViewHolder
import com.Doctoor.app.ui.modules.medicine.category.MedicineCategoryItemVM
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class PopularMedicinesFragment :
    BaseRecyclerViewFragment<FragmentPopularMedicinesBinding, PopularMedicinesFragmentVM, PopularMedicinesFragment.Adapter, Medicines.Category>() {

    override fun layoutRes() = R.layout.fragment_popular_medicines

    companion object {
        fun newInstance(): PopularMedicinesFragment {
            return PopularMedicinesFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<PopularMedicinesFragmentVM>

    override fun toolbarColor() = 0

    override fun getViewModel() = viewModel.get()

    override fun getBindingVariable() = BR.popularMedicinesFragmentVM

    override fun getToolBarTile() = getString(R.string.other_categories)

    class Adapter(
        context: Context,
        mValue: MutableList<Medicines.Category>,
        mNavidation: NavigatorHelper
    ) :
        BaseRVFooterAdapter<Medicines.Category, MedicineCategoryItemVM, Adapter.ViewHolder>(
            context,
            mValue,
            mNavidation
        ) {
        override fun getLayoutId(viewType: Int) = getViewModel().layoutRes()
        override fun getViewHolder(
            view: View,
            viewModel: MedicineCategoryItemVM,
            mDataBinding: ViewDataBinding, viewType: Int
        ): ViewHolder {
            val kotlinClass: KClass<ViewHolder> = ViewHolder::class
            val ctor = kotlinClass.primaryConstructor
            val myObject = ctor?.call(view, viewModel, mDataBinding) as ViewHolder
            return myObject
        }

        override fun getViewModel() =
            MedicineCategoryItemVM()

        override fun getVariableId() = BR.medicineCategoryItemVM

        class ViewHolder(
            view: View,
            viewModel: MedicineCategoryItemVM,
            mDataBinding: ViewDataBinding
        ) :
            BaseViewHolder<Medicines.Category, MedicineCategoryItemVM>(
                view,
                viewModel,
                mDataBinding
            )
    }
}
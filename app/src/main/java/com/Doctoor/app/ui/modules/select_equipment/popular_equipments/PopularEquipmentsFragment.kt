package com.Doctoor.app.ui.modules.select_equipment.popular_equipments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.Doctoor.app.BR
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseRecyclerViewFragment
import com.Doctoor.app.data.database.CartManager
import com.Doctoor.app.data.database.EquipmentDao
import com.Doctoor.app.databinding.FragmentPopularEquipmentsBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.model.response.Equipments
import com.Doctoor.app.navigation.NavigatorHelper
import com.Doctoor.app.ui.adapters.BaseRVFooterAdapter
import com.Doctoor.app.ui.adapters.BaseViewHolder
import javax.inject.Inject
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class PopularEquipmentsFragment :
    BaseRecyclerViewFragment<FragmentPopularEquipmentsBinding, PopularEquipmentsVM,
            PopularEquipmentsFragment.Adapter, Equipments.Equipment>() {

    override fun layoutRes() = R.layout.fragment_popular_equipments

    companion object {
        fun newInstance(): PopularEquipmentsFragment {
            return PopularEquipmentsFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<PopularEquipmentsVM>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewModel().adapter.set(adapter)
    }

    override fun getToolBarTile() = getString(R.string.popular_equipments)

    override fun toolbarColor() = 0


    override fun getBindingVariable() = BR.popularEquipmentsVM
    override fun onResume() {
        super.onResume()
        getViewModel().getEquipments()
        adapter.notifyDataSetChanged()
    }

    override fun getViewModel(): PopularEquipmentsVM = viewModel.get()

    class Adapter(
        context: Context,
        mValue: MutableList<Equipments.Equipment>,
        mNavidation: NavigatorHelper,
        private val dao: EquipmentDao,
        var cartManager: CartManager
    ) :
        BaseRVFooterAdapter<Equipments.Equipment, PopularEquipmentItemVM, Adapter.ViewHolder>(
            context,
            mValue,
            mNavidation
        ) {
        override fun getLayoutId(viewType: Int) = getViewModel().layoutRes()
        override fun getViewHolder(
            view: View,
            viewModel: PopularEquipmentItemVM,
            mDataBinding: ViewDataBinding, viewType: Int
        ): ViewHolder {
            val kotlinClass: KClass<ViewHolder> = ViewHolder::class
            val ctor = kotlinClass.primaryConstructor
            val myObject = ctor?.call(view, viewModel, mDataBinding) as ViewHolder
            return myObject
        }

        override fun getViewModel() = PopularEquipmentItemVM(dao, cartManager)
        override fun getVariableId() = BR.popularEquipmentItemVM

        class ViewHolder(
            view: View,
            viewModel: PopularEquipmentItemVM,
            mDataBinding: ViewDataBinding
        ) :
            BaseViewHolder<Equipments.Equipment, PopularEquipmentItemVM>(
                view,
                viewModel,
                mDataBinding
            )
    }

}
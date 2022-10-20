package com.Doctoor.app.ui.modules.myorder

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.Doctoor.app.BR
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseRecyclerViewFragment
import com.Doctoor.app.data.database.CartManager
import com.Doctoor.app.data.database.EquipmentDao
import com.Doctoor.app.data.database.LabTestDao
import com.Doctoor.app.data.database.MedicineDao
import com.Doctoor.app.databinding.FragmentMyOrderBinding
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

class MyOrderFragment :
    BaseRecyclerViewFragment<FragmentMyOrderBinding, MyOrderFragmentVM, MyOrderFragment.Adapter, MedicalServices.MyOrder>() {

    override fun layoutRes() = R.layout.fragment_my_order

    companion object {
        fun newInstance(): MyOrderFragment {
            return MyOrderFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stateLayout?.viewState = MultiStateView.ViewState.EMPTY
    }

    @Inject
    lateinit var myOrderAdapter: Adapter

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<MyOrderFragmentVM>

    override fun getToolBarTile() = getString(R.string.my_orders)

    override fun toolbarColor() = 0

    override fun getViewModel() = viewModel.get()

    override fun getBindingVariable() = BR.myOrderFragmentVM

    class Adapter(
        context: Context,
        mValue: MutableList<MedicalServices.MyOrder>,
        var equipmentDao: EquipmentDao,
        var medicineDao: MedicineDao,
        var labTestDao: LabTestDao,
        var cartManager: CartManager,
        mNavidation: NavigatorHelper
    ) :
        BaseRVFooterAdapter<MedicalServices.MyOrder, MyOrderItemVM, Adapter.ViewHolder>(
            context,
            mValue,
            mNavidation
        ) {

        override fun getLayoutId(viewType: Int) = getViewModel().layoutRes()
        override fun getViewHolder(
            view: View,
            viewModel: MyOrderItemVM,
            mDataBinding: ViewDataBinding, viewType: Int
        ): ViewHolder {
            val kotlinClass: KClass<ViewHolder> = ViewHolder::class
            val ctor = kotlinClass.primaryConstructor
            val myObject = ctor?.call(view, viewModel, mDataBinding) as ViewHolder
            return myObject
        }

        override fun getViewModel() =
            MyOrderItemVM(equipmentDao, medicineDao, labTestDao, cartManager)

        override fun getVariableId() = BR.myOrderItemVM

        class ViewHolder(view: View, viewModel: MyOrderItemVM, mDataBinding: ViewDataBinding) :
            BaseViewHolder<MedicalServices.MyOrder, MyOrderItemVM>(view, viewModel, mDataBinding)
    }
}
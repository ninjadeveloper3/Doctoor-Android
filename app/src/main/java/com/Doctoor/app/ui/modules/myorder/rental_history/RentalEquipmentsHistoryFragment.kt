package com.Doctoor.app.ui.modules.myorder.rental_history

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.Doctoor.app.BR
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseRecyclerViewFragment
import com.Doctoor.app.databinding.FragmentRentalEquipmentsBinding
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

class RentalEquipmentsHistoryFragment :
    BaseRecyclerViewFragment<FragmentRentalEquipmentsBinding, RentalEquipmentsHistoryVM, RentalEquipmentsHistoryFragment.Adapter, MedicalServices.RentalHistory>() {

    override fun layoutRes() = R.layout.fragment_rental_equipments_history

    companion object {
        fun newInstance(): RentalEquipmentsHistoryFragment {
            return RentalEquipmentsHistoryFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<RentalEquipmentsHistoryVM>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stateLayout?.viewState = MultiStateView.ViewState.EMPTY
    }

    override fun getToolBarTile() = getString(R.string.my_orders)

    override fun toolbarColor() = 0


    override fun getViewModel() = viewModel.get()

    class Adapter(
        context: Context,
        mValue: MutableList<MedicalServices.RentalHistory>,
        mNavidation: NavigatorHelper
    ) :
        BaseRVFooterAdapter<MedicalServices.RentalHistory, RentalHistoryItemVM, Adapter.ViewHolder>(
            context,
            mValue,
            mNavidation
        ) {
        var bundle: Bundle = Bundle()

        override fun getLayoutId(viewType: Int) = getViewModel().layoutRes()
        override fun getViewHolder(
            view: View,
            viewModel: RentalHistoryItemVM,
            mDataBinding: ViewDataBinding, viewType: Int
        ): ViewHolder {
            val kotlinClass: KClass<ViewHolder> = ViewHolder::class
            val ctor = kotlinClass.primaryConstructor
            val myObject = ctor?.call(view, viewModel, mDataBinding) as ViewHolder
            return myObject
        }

        override fun getViewModel() = RentalHistoryItemVM()

        override fun getVariableId() = BR.rentalHistoryItemVM

        class ViewHolder(
            view: View,
            viewModel: RentalHistoryItemVM,
            mDataBinding: ViewDataBinding
        ) :
            BaseViewHolder<MedicalServices.RentalHistory, RentalHistoryItemVM>(
                view,
                viewModel,
                mDataBinding
            )
    }

    override fun getBindingVariable() = BR.rentalEquipmentsHistoryVM
}
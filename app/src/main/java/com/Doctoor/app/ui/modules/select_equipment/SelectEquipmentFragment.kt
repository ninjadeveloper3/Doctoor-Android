package com.Doctoor.app.ui.modules.select_equipment

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.Doctoor.app.BR
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseRecyclerViewFragment
import com.Doctoor.app.data.database.CartManager
import com.Doctoor.app.data.database.EquipmentDao
import com.Doctoor.app.databinding.FragmentSelectEquipmentBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.model.response.Equipments
import com.Doctoor.app.navigation.NavigatorHelper
import com.Doctoor.app.ui.adapters.BaseRVFooterAdapter
import com.Doctoor.app.ui.adapters.BaseViewHolder
import com.Doctoor.app.utils.hideKeyboard
import com.Doctoor.app.utils.isEmpty
import kotlinx.android.synthetic.main.fragment_select_equipment.*
import javax.inject.Inject
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class SelectEquipmentFragment :
    BaseRecyclerViewFragment<FragmentSelectEquipmentBinding, SelectEquipmentVM,
            SelectEquipmentFragment.Adapter, Equipments.Equipment>(), View.OnTouchListener {
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val x = event.x.toInt()
            val y = event.y.toInt()
            if (event.rawX >= (equipmentSearchView.right - equipmentSearchView.compoundDrawables[2].bounds.width())) {
                // your action here
                if (!isEmpty(getmViewModel().query))
                    getmViewModel().clearSearch()
                return true;
            }
        }
        return false
    }

    override fun layoutRes() = R.layout.fragment_select_equipment

    companion object {
        fun newInstance(): SelectEquipmentFragment {
            return SelectEquipmentFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewModel().adapter.set(adapter)
        getmViewModel().keyboardState.observe(this, Observer { hideKeyboard() })
        equipmentSearchView.setOnTouchListener(this)
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<SelectEquipmentVM>

    override fun getToolBarTile() = getString(R.string.select_equipments)

    override fun toolbarColor() = 0

    override fun getViewModel() = viewModel.get()

    override fun getBindingVariable() = BR.selectEquipmentVM

    override fun onResume() {
        super.onResume()
        getViewModel().getEquipments()
        adapter.notifyDataSetChanged()
    }

    class Adapter(
        context: Context,
        mValue: MutableList<Equipments.Equipment>,
        mNavidation: NavigatorHelper,
        private val dao: EquipmentDao,
        var cartManager: CartManager
    ) :
        BaseRVFooterAdapter<Equipments.Equipment, SelectEquipmentItemVM, Adapter.ViewHolder>(
            context,
            mValue,
            mNavidation
        ) {
        override fun getLayoutId(viewType: Int) = getViewModel().layoutRes()
        override fun getViewHolder(
            view: View,
            viewModel: SelectEquipmentItemVM,
            mDataBinding: ViewDataBinding, viewType: Int
        ): ViewHolder {
            val kotlinClass: KClass<ViewHolder> = ViewHolder::class
            val ctor = kotlinClass.primaryConstructor
            val myObject = ctor?.call(view, viewModel, mDataBinding) as ViewHolder
            return myObject
        }

        override fun getViewModel() = SelectEquipmentItemVM(dao, cartManager)
        override fun getVariableId() = BR.selectEquipmentItemVM

        class ViewHolder(
            view: View,
            viewModel: SelectEquipmentItemVM,
            mDataBinding: ViewDataBinding
        ) :
            BaseViewHolder<Equipments.Equipment, SelectEquipmentItemVM>(
                view,
                viewModel,
                mDataBinding
            )
    }
}
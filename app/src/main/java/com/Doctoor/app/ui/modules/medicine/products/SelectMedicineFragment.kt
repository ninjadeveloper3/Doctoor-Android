package com.Doctoor.app.ui.modules.medicine.products

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.Doctoor.app.BR
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseRecyclerViewFragment
import com.Doctoor.app.data.database.CartManager
import com.Doctoor.app.data.database.MedicineDao
import com.Doctoor.app.databinding.FragmentSelectMedicineBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.model.response.Medicines
import com.Doctoor.app.navigation.NavigatorHelper
import com.Doctoor.app.ui.adapters.BaseRVFooterAdapter
import com.Doctoor.app.ui.adapters.BaseViewHolder
import com.Doctoor.app.utils.hideKeyboard
import com.Doctoor.app.utils.isEmpty
import com.Doctoor.app.utils.showKeyboard
import kotlinx.android.synthetic.main.fragment_select_medicine.*
import javax.inject.Inject
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class SelectMedicineFragment :
    BaseRecyclerViewFragment<FragmentSelectMedicineBinding, SelectMedicineVM, SelectMedicineFragment.Adapter, Medicines.Product>(),
    View.OnTouchListener {
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val x = event.x.toInt()
            val y = event.y.toInt()
            if (event.rawX >= (medicineSearchView.right - medicineSearchView.compoundDrawables[2].bounds.width())) {
                // your action here
                if (!isEmpty(getmViewModel().query))
                    getmViewModel().clearSearch()
                return true;
            }
        }
        return false
    }

    override fun layoutRes() = R.layout.fragment_select_medicine

    companion object {
        fun newInstance(): SelectMedicineFragment {
            return SelectMedicineFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getmViewModel().keyboardState.observe(this, Observer { hideKeyboard() })
        medicineSearchView.setOnTouchListener(this)
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<SelectMedicineVM>

    override fun getToolBarTile() = getString(R.string.select_medicines)

    override fun toolbarColor() = 0

    override fun getViewModel() = viewModel.get()

    override fun getBindingVariable() = BR.selectMedicineVM
    private val mHandler = Handler()

    private val runnable = Runnable {
        showKeyboard()
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
        if (getmViewModel().isSearchEnable) {
            mHandler.postDelayed(runnable, 1000)
        }
    }

    class Adapter(
        context: Context,
        mValue: MutableList<Medicines.Product>,
        mNavidation: NavigatorHelper, private val dao: MedicineDao, var cartManager: CartManager
    ) :
        BaseRVFooterAdapter<Medicines.Product, SelectMedicinesItemVM, Adapter.ViewHolder>(
            context,
            mValue,
            mNavidation
        ) {
        override fun getLayoutId(viewType: Int) = getViewModel().layoutRes()
        override fun getViewHolder(
            view: View,
            viewModel: SelectMedicinesItemVM,
            mDataBinding: ViewDataBinding, viewType: Int
        ): ViewHolder {
            val kotlinClass: KClass<ViewHolder> = ViewHolder::class
            val ctor = kotlinClass.primaryConstructor
            val myObject = ctor?.call(view, viewModel, mDataBinding) as ViewHolder
            return myObject
        }

        override fun getVariableId() = BR.selectMedicinesItemVM

        override fun getViewModel() =
            SelectMedicinesItemVM(dao, cartManager)


        class ViewHolder(
            view: View,
            viewModel: SelectMedicinesItemVM,
            mDataBinding: ViewDataBinding
        ) : BaseViewHolder<Medicines.Product, SelectMedicinesItemVM>(view, viewModel, mDataBinding)

    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacks(runnable)
    }
}
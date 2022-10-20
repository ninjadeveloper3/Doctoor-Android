package com.Doctoor.app.ui.modules.select_lab.test

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import bolts.Task
import com.Doctoor.app.BR
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseRecyclerViewFragment
import com.Doctoor.app.data.database.CartManager
import com.Doctoor.app.data.database.LabTestDao
import com.Doctoor.app.databinding.FragmentSelectTestBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.model.response.Tests
import com.Doctoor.app.navigation.NavigatorHelper
import com.Doctoor.app.ui.adapters.BaseRVFooterAdapter
import com.Doctoor.app.ui.adapters.BaseViewHolder
import com.Doctoor.app.utils.hideKeyboard
import com.Doctoor.app.utils.isEmpty
import com.Doctoor.app.utils.showKeyboard
import kotlinx.android.synthetic.main.fragment_select_test.*
import javax.inject.Inject
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class SelectTestFragment :
    BaseRecyclerViewFragment<FragmentSelectTestBinding, SelectTestFragmentVM, SelectTestFragment.Adapter, Tests.Test>(),
    View.OnTouchListener {
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val x = event.x.toInt()
            val y = event.y.toInt()
            if (event.rawX >= (testsSearchView.right - testsSearchView.compoundDrawables[2].bounds.width())) {
                if (!isEmpty(getmViewModel().query))
                    getmViewModel().clearSearch()
                return true;
            }
        }
        return false
    }

    override fun layoutRes() = R.layout.fragment_select_test

    companion object {
        fun newInstance(): SelectTestFragment {
            return SelectTestFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<SelectTestFragmentVM>

    override fun toolbarColor() = 0

    override fun getViewModel() = viewModel.get()

    override fun getBindingVariable() = BR.selectTestFragmentVM

    override fun getToolBarTile() = getString(R.string.select_test)

    private val mHandler = Handler()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getmViewModel().keyboardState.observe(this, Observer { if (it) hideKeyboard() })
        testsSearchView.setOnTouchListener(this)
    }

    private val runnable = Runnable {
        showKeyboard()
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
        if (getmViewModel().isSearchEnable.value!!) {
            mHandler.postDelayed(runnable, 1000)
        }
    }

    class Adapter(
        context: Context,
        mValue: MutableList<Tests.Test>,
        mNavidation: NavigatorHelper,
        private var dao: LabTestDao,
        private var cartManager: CartManager
    ) :
        BaseRVFooterAdapter<Tests.Test, SelectTestItemVM, Adapter.ViewHolder>(
            context,
            mValue,
            mNavidation
        ) {
        override fun getLayoutId(viewType: Int) = getViewModel().layoutRes()
        override fun getViewHolder(
            view: View, viewModel: SelectTestItemVM, mDataBinding: ViewDataBinding, viewType: Int
        ): ViewHolder {
            val kotlinClass: KClass<ViewHolder> = ViewHolder::class
            val ctor = kotlinClass.primaryConstructor
            val myObject = ctor?.call(view, viewModel, mDataBinding) as ViewHolder
            return myObject
        }

        override fun getViewModel() = SelectTestItemVM(dao, cartManager)

        override fun getVariableId() = BR.selectTestItemVM

        class ViewHolder(view: View, viewModel: SelectTestItemVM, mDataBinding: ViewDataBinding) :
            BaseViewHolder<Tests.Test, SelectTestItemVM>(view, viewModel, mDataBinding)
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacks(runnable)
    }
}
package com.Doctoor.app.base;

import android.os.Bundle
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.Doctoor.app.R
import com.Doctoor.app.data.source.State
import com.Doctoor.app.data.source.Status
import com.Doctoor.app.navigation.NavigatorHelper
import com.Doctoor.app.utils.AlertUtils
import com.Doctoor.app.utils.makeCall
import com.Doctoor.app.utils.toast
import javax.inject.Inject


abstract class BaseViewModelActivity<out VB : ViewDataBinding, out VM : BaseViewModel> :
    BaseActivity() {
    private lateinit var mViewDataBinding: VB
    private var mViewModel: VM? = null

    @Inject
    lateinit var navigatorHelper: NavigatorHelper

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
        setupToolbar()
        mViewModel?.stateLiveData?.observe(this, Observer { handleState(it) })
    }

    private fun performDataBinding() {
        mViewDataBinding = DataBindingUtil
            .setContentView(this, layoutRes())
        this.mViewModel = if (mViewModel == null) getMyViewModel() else mViewModel
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding.lifecycleOwner = this
        mViewDataBinding.executePendingBindings()
        mViewModel?.onCreate(intent.extras, navigatorHelper)
    }

    abstract fun getBindingVariable(): Int
    abstract fun getMyViewModel(): VM
    protected fun getViewDataBinding(): VB = mViewDataBinding

    /**
     * Default state handling, can be override
     * @param state viewModel's state
     */
    open fun handleState(@Nullable state: State?) {
        setLoading(state != null && state.status == Status.LOADING)
        handleMessageState(state)
    }

    protected fun handleMessageState(@Nullable state: State?) {
        if (state?.message != null) {
            if (state.hardAlert) {
                AlertUtils.showAlertDialog(this, state.message)
            } else {
                toast(state.message!!)

            }
        }
    }

    fun setLoading(loading: Boolean) {
    }

    fun openDialer() {
        makeCall(getString(R.string.representative_number))
    }
}
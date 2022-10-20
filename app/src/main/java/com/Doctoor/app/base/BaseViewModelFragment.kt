package com.Doctoor.app.base;

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.Nullable
import androidx.appcompat.widget.Toolbar
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.Doctoor.app.R
import com.Doctoor.app.data.source.State
import com.Doctoor.app.data.source.Status
import com.Doctoor.app.di.components.Injectable
import com.Doctoor.app.navigation.NavigatorHelper
import com.Doctoor.app.utils.dimen
import com.Doctoor.app.utils.makeCall
import com.Doctoor.app.utils.toast
import com.Doctoor.app.widget.recyclerview.SpacesItemDecoration
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


abstract class BaseViewModelFragment<out VB : ViewDataBinding, out VM : BaseViewModel> :
    BaseFragment<VB>(),
    HasSupportFragmentInjector,
    Injectable {
    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var mActivity: BaseActivity
    private lateinit var mViewModel: VM

    @Inject
    lateinit var navigatorHelper: NavigatorHelper

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as BaseActivity
        AndroidSupportInjection.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = getViewModel()
        mViewModel.onCreate(arguments, navigatorHelper)
        getViewDataBinding().setVariable(getBindingVariable(), mViewModel)
        getViewDataBinding().lifecycleOwner = this
        getViewDataBinding().executePendingBindings()
        setupToolbar(getViewDataBinding().root.findViewById(R.id.toolbar))
        mViewModel.stateLiveData.observe(
            this,
            Observer { if (it.status != Status.IDEAL) handleState(it) })
    }

    private fun setupToolbar(toolbar: Toolbar?) {
        if (toolbar != null) {
            toolbar.title = ""
            getBaseActivity().setSupportActionBar(toolbar)
            getBaseActivity().supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setHomeButtonEnabled(true)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        check(mActivity is BaseViewModelActivity<*, *>) {
            throw  IllegalStateException("All fragment's container must extend BaseViewModelActivity<*,*>")
        }
    }

    protected fun getmViewModel() = mViewModel

    override fun supportFragmentInjector() = fragmentInjector

    abstract fun toolbarColor(): Int

    fun getBaseActivity() = mActivity

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract fun getViewModel(): VM

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract fun getBindingVariable(): Int

    protected fun spacesItemDecoration() =
        SpacesItemDecoration(dimen(R.dimen.elevationMax_x)!!, true)

    protected fun spacesItemDecoration(resource: Int) =
        SpacesItemDecoration(dimen(resource)!!, true)


    /**
     * Default state handling, can be override
     * @param state viewModel's state
     */
    public open fun handleState(@Nullable state: State?) {
        setLoading(state != null && state.status == Status.LOADING)
    }

    public open fun handleMessageState(@Nullable state: State?) {
        if (state?.message != null) {
            if (state.hardAlert) {
                Log.d(TAG, "handleMessageState: " + state.message)
            } else {
                toast(state.message!!)

                Log.d(TAG, "handleMessageState: " + state.message)
            }
        }
    }

    open fun setLoading(loading: Boolean) {
    }

    fun openDialer() {
        makeCall(getString(R.string.representative_number))
    }

}
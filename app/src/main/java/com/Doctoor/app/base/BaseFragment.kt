package com.Doctoor.app.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding


abstract class BaseFragment<out VB : ViewDataBinding> : SBaseFragment() {
    private lateinit var mViewDataBinding: VB
    protected lateinit var TAG: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding =
            AutoClearedValue<VB>(
                this,
                DataBindingUtil.inflate(inflater, layoutRes(), container, false)
            ).get()!!
        return mViewDataBinding.root
    }

    @LayoutRes
    protected abstract fun layoutRes(): Int

    fun getViewDataBinding() = mViewDataBinding
}
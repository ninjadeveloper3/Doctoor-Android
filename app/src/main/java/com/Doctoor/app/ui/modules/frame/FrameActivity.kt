package com.Doctoor.app.ui.modules.frame

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import com.Doctoor.app.BR
import com.Doctoor.app.DoctoorApp
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseFragment
import com.Doctoor.app.base.BaseViewModelActivity
import com.Doctoor.app.base.BaseViewModelFragment
import com.Doctoor.app.databinding.ActivityFrameBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.ui.modules.checkout.complete_order.CompleteOrderFragment
import com.Doctoor.app.utils.Constants.EXTRA
import com.Doctoor.app.utils.Constants.FRAGMENT_CLASS
import com.Doctoor.app.utils.Constants.SHOW_TOOLBAR
import com.Doctoor.app.utils.setBoldFontTypeSpan
import javax.inject.Inject


class FrameActivity : BaseViewModelActivity<ActivityFrameBinding, FrameActivityVM>() {
    private lateinit var fragment: BaseViewModelFragment<*, *>
    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<FrameActivityVM>

    override fun layoutRes() = R.layout.activity_frame

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val extras = intent
        if (extras == null) {
            finish()
            return
        }
        val fragmentName = extras.getStringExtra(FRAGMENT_CLASS)
        if (fragmentName == null || TextUtils.isEmpty(fragmentName)) {
            finish()
            return
        }

        fragment = navigatorHelper.instantiateFragment<Fragment>(
            this,
            fragmentName
        ) as BaseViewModelFragment<*, *>

        if (extras.hasExtra(EXTRA)) {
            navigatorHelper.createFragmentInstance(fragment, extras.getBundleExtra(EXTRA)!!)
        } else {
            navigatorHelper.createFragmentInstance(fragment, Bundle())
        }


        viewModel.get().toolbarTitle.postValue(
            setBoldFontTypeSpan(
                fragment.getToolBarTile(),
                R.font.mark_pro_light
            )
        )

    }

    init {
        instance = this;
    }

    companion object {
        var instance: FrameActivity? = null
    }

    override fun toolbar() = intent.getBooleanExtra(SHOW_TOOLBAR, true)

    override fun getBindingVariable() = BR.frameActivityVM

    override fun getMyViewModel() = viewModel.get()
    override fun getHomeAsUpIndicator() = R.drawable.ic_back_arrow

    override fun onBackPressed() {
        callOnBackInFragments()
    }

    private fun callOnBackInFragments() {
        val fragments = supportFragmentManager.fragments
        for (f in fragments) {
            if (f != null && f is CompleteOrderFragment)
                f.onBackPressed()
            else
                super.onBackPressed()
        }
    }
}
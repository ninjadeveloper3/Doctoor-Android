package com.Doctoor.app.ui.modules.myorder

import android.os.Bundle
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.viewpager.widget.ViewPager
import com.Doctoor.app.base.BaseViewModel
import com.Doctoor.app.ui.adapters.SectionsPagerAdapter
import javax.inject.Inject

class MyOrderPagerFragmentVM @Inject constructor() : BaseViewModel() {
    val adapter = ObservableField<SectionsPagerAdapter>()

    companion object {
        @JvmStatic
        @BindingAdapter("app:mAdapter")
        fun setPagerAdapter(viewPager: ViewPager, pagerAdapter: SectionsPagerAdapter?) {
            if (null == pagerAdapter)
                return
            viewPager.adapter = pagerAdapter

        }
    }

    override fun onFirsTimeUiCreate(bundle: Bundle?) {
    }
}
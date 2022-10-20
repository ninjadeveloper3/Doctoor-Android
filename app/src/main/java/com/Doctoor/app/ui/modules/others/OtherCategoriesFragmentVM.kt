package com.Doctoor.app.ui.modules.others

import android.app.Application
import android.os.Bundle
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.viewpager.widget.ViewPager
import javax.inject.Inject
import com.Doctoor.app.base.BaseViewModel
import com.Doctoor.app.ui.adapters.SectionsPagerAdapter

class OtherCategoriesFragmentVM @Inject constructor() : BaseViewModel() {
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
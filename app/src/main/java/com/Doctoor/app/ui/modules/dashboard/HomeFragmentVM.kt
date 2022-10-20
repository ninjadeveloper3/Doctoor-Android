package com.Doctoor.app.ui.modules.dashboard

import android.os.Bundle
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.RecyclerView
import com.Doctoor.app.base.BaseViewModel
import com.Doctoor.app.data.remote.HomeRestService
import com.Doctoor.app.rx.functions.PlainConsumer
import com.Doctoor.app.ui.adapters.SliderAdapter
import com.Doctoor.app.ui.modules.landing.LandingFragment
import com.Doctoor.app.ui.modules.medicine.category.MedicineCategoryFragment
import com.Doctoor.app.ui.modules.select_city.SelectCityFragment
import com.Doctoor.app.ui.modules.select_lab.SelectLabFragment
import com.Doctoor.app.ui.modules.upload_prescription.UploadPrescriptionFragment
import com.Doctoor.app.utils.AlertUtils
import com.Doctoor.app.widget.slider.SliderView
import javax.inject.Inject

class HomeFragmentVM @Inject constructor(private val apiServices: HomeRestService) :
    BaseViewModel() {
    val sliderAdapter = ObservableField<SliderAdapter>()
    val adapter = ObservableField<HomeFragment.Adapter>()

    override fun onFirsTimeUiCreate(bundle: Bundle?) {
        fetchBanner()
        fetchInDemand()
    }

    fun fetchBanner() {
        execute(false, apiServices.getBanners(),
            PlainConsumer { t ->
                sliderAdapter.get()?.setData(t.data)
            }
        )
    }

    fun fetchInDemand() {
        execute(false, apiServices.getInDemandProducts(),
            PlainConsumer { t ->
                adapter.get()?.addAll(t.data.medicines)
                adapter.get()?.addAll(t.data.tests)
                adapter.get()?.addAll(t.data.equipments)
            }
        )
    }

    companion object {
        @JvmStatic
        @BindingAdapter("app:mAdapter")
        fun setAdapter(
            view: RecyclerView,
            adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>?
        ) {
            if (null == adapter)
                return
            view.adapter = adapter
        }

        @JvmStatic
        @BindingAdapter("app:mPagerAdapter")
        fun setSliderAdapter(view: SliderView, pagerAdapter: SliderAdapter?) {
            if (null == pagerAdapter)
                return
            view.sliderAdapter = pagerAdapter
        }
    }

    fun gotoLabTest() {
        navigatorHelper?.startFragmentWithBottomNavigation<SelectLabFragment>(SelectLabFragment::class.java.name)
    }

    fun gotoMedicineCategory() =
        navigatorHelper?.startFragmentWithBottomNavigation<MedicineCategoryFragment>(
            MedicineCategoryFragment::class.java.name
        )

    fun onService() =
        navigatorHelper?.startFragmentWithBottomNavigation<SelectCityFragment>(SelectCityFragment::class.java.name)

    fun onPrescription(view:View) {
        if (BaseViewModel.isLogin.value!!) {
            navigatorHelper?.startFragmentWithToolbar<UploadPrescriptionFragment>(
                UploadPrescriptionFragment::class.java.name
            )
        } else {
            navigateToLogin(view)
        }
    }

}
package com.Doctoor.app.ui.modules.others

import android.os.Bundle
import android.view.View
import com.Doctoor.app.BR
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseViewModelFragment
import com.Doctoor.app.databinding.FragmentOtherCategoriesBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.ui.adapters.SectionsPagerAdapter
import com.Doctoor.app.ui.modules.popular_services.PopularServicesFragment
import com.Doctoor.app.ui.modules.select_equipment.popular_equipments.PopularEquipmentsFragment
import com.Doctoor.app.ui.modules.select_lab.test.popular_tests.PopularTestsCategoryFragment
import javax.inject.Inject

class OtherCategoriesFragment :
    BaseViewModelFragment<FragmentOtherCategoriesBinding, OtherCategoriesFragmentVM>() {
    @Inject
    lateinit var adapter: SectionsPagerAdapter

    override fun layoutRes() = R.layout.fragment_other_categories

    companion object {
        fun newInstance(): OtherCategoriesFragment {
            return OtherCategoriesFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<OtherCategoriesFragmentVM>

    override fun getToolBarTile() = getString(R.string.other_categories)

    override fun toolbarColor() = 0

    override fun getViewModel() = viewModel.get()

    override fun getBindingVariable() = BR.otherCategoriesFragmentVM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.addFragmentInfo(
            PopularEquipmentsFragment::class.java.name,
            "Popular Equipment",
            Bundle()
        )

        adapter.addFragmentInfo(
            PopularTestsCategoryFragment::class.java.name,
            "Popular Tests",
            Bundle()
        )
        adapter.addFragmentInfo(
            PopularServicesFragment::class.java.name,
            "Medical Services",
            Bundle()
        )

        getViewModel().adapter.set(adapter)
    }
}
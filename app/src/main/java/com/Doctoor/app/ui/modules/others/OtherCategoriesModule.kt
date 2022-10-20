package com.Doctoor.app.ui.modules.others

import dagger.Module
import dagger.Provides
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule
import com.Doctoor.app.ui.adapters.SectionsPagerAdapter
import com.Doctoor.app.ui.modules.myorder.MyOrderPagerFragment

@Module
class OtherCategoriesModule: BaseFragmentModule<OtherCategoriesFragment>() {

    @Provides
    @ViewModelInjection
    fun provideOtherCategoriesVM(
        fragment: OtherCategoriesFragment,
        viewModelProvider: InjectionViewModelProvider<OtherCategoriesFragmentVM>
    ) = viewModelProvider.get(fragment, OtherCategoriesFragmentVM::class)

    @Provides
    fun provideOtherCategoriesFragmentAdapter(fragment: OtherCategoriesFragment): SectionsPagerAdapter {
        return SectionsPagerAdapter(fragment.getBaseActivity(), fragment.childFragmentManager)
    }

}
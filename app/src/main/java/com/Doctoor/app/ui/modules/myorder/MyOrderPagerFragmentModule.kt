package com.Doctoor.app.ui.modules.myorder

import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule
import com.Doctoor.app.ui.adapters.SectionsPagerAdapter
import dagger.Module
import dagger.Provides

@Module
class MyOrderPagerFragmentModule : BaseFragmentModule<MyOrderPagerFragment>() {

    @Provides
    @ViewModelInjection
    fun provideMyOrderPagerVM(
        fragment: MyOrderPagerFragment,
        viewModelProvider: InjectionViewModelProvider<MyOrderPagerFragmentVM>
    ) = viewModelProvider.get(fragment, MyOrderPagerFragmentVM::class)

    @Provides
    fun provideMyOrderPagerFragmentAdapter(fragment: MyOrderPagerFragment): SectionsPagerAdapter {
        return SectionsPagerAdapter(fragment.getBaseActivity(), fragment.childFragmentManager)
    }

}
package com.Doctoor.app.ui.modules.popular_services

import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule
import dagger.Module
import dagger.Provides

@Module
class PopularServicesModule : BaseFragmentModule<PopularServicesFragment>() {

    @Provides
    @ViewModelInjection
    fun providePopularServicesVM(
        fragment: PopularServicesFragment,
        viewModelProvider: InjectionViewModelProvider<PopularServicesVM>
    ) = viewModelProvider.get(fragment, PopularServicesVM::class)

    @Provides
    fun providePopularServicesFragmentAdapter(fragment: PopularServicesFragment) =
        PopularServicesFragment.Adapter(
            fragment.mActivity,
            ArrayList(),
            fragment.navigatorHelper
        )
}
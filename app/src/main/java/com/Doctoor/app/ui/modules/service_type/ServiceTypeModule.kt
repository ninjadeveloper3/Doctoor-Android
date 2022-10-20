package com.Doctoor.app.ui.modules.service_type

import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule
import dagger.Module
import dagger.Provides

@Module
class ServiceTypeModule : BaseFragmentModule<ServiceTypeFragment>() {

    @Provides
    @ViewModelInjection
    fun provideServiceTypeVM(
        fragment: ServiceTypeFragment,
        viewModelProvider: InjectionViewModelProvider<ServiceTypeVM>
    ) = viewModelProvider.get(fragment, ServiceTypeVM::class)
}
package com.Doctoor.app.ui.modules.service_type

import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule
import dagger.Module
import dagger.Provides

@Module
class MedicalServicesFragmentModule : BaseFragmentModule<MedicalServicesFragment>() {

    @Provides
    @ViewModelInjection
    fun provideMedicalServicesVM(
        fragment: MedicalServicesFragment,
        viewModelProvider: InjectionViewModelProvider<MedicalServicesFragmentVM>
    ) = viewModelProvider.get(fragment, MedicalServicesFragmentVM::class)
}
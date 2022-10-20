package com.Doctoor.app.ui.modules.doctor_profile

import dagger.Module
import dagger.Provides
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule

@Module
class DoctorProfileModule : BaseFragmentModule<DoctorProfileFragment>() {

    @Provides
    @ViewModelInjection
    fun provideDoctorProfileVM(
        fragment: DoctorProfileFragment,
        viewModelProvider: InjectionViewModelProvider<DoctorProfileVM>
    ) = viewModelProvider.get(fragment, DoctorProfileVM::class)
}
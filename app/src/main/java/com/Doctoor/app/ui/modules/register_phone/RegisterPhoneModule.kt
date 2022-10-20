package com.Doctoor.app.ui.modules.register_phone

import dagger.Module
import dagger.Provides
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule

@Module
class RegisterPhoneModule : BaseFragmentModule<RegisterPhoneFragment>() {

    @Provides
    @ViewModelInjection
    fun provideRegisterPhoneVM(
            fragment: RegisterPhoneFragment,
            viewModelProvider: InjectionViewModelProvider<RegisterPhoneFragmentVM>
    ) = viewModelProvider.get(fragment, RegisterPhoneFragmentVM::class)
}
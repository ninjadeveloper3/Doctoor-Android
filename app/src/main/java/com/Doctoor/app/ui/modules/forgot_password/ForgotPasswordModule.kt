package com.Doctoor.app.ui.modules.forgot_password

import dagger.Module
import dagger.Provides
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule

@Module
class ForgotPasswordModule : BaseFragmentModule<ForgotPasswordFragment>() {

    @Provides
    @ViewModelInjection
    fun provideForgotPasswordVM(
        fragment: ForgotPasswordFragment,
        viewModelProvider: InjectionViewModelProvider<ForgotPasswordVM>
    ) = viewModelProvider.get(fragment, ForgotPasswordVM::class)
}
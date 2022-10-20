package com.Doctoor.app.ui.modules.code_verification

import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule
import dagger.Module
import dagger.Provides

@Module
class CodeVerificationModule : BaseFragmentModule<CodeVerificationFragment>() {

    @Provides
    @ViewModelInjection
    fun provideCodeVerificationVM(
        fragment: CodeVerificationFragment,
        viewModelProvider: InjectionViewModelProvider<CodeVerificationVM>
    ) = viewModelProvider.get(fragment, CodeVerificationVM::class)
}
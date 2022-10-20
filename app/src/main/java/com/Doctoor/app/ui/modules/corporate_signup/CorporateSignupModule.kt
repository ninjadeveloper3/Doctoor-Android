package com.Doctoor.app.ui.modules.corporate_signup

import dagger.Module
import dagger.Provides
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule

@Module
class CorporateSignupModule : BaseFragmentModule<CorporateSignupFragment>() {

    @Provides
    @ViewModelInjection
    fun provideCorporateSignupVM(
        fragment: CorporateSignupFragment,
        viewModelProvider: InjectionViewModelProvider<CorporateSignupVM>
    ) = viewModelProvider.get(fragment, CorporateSignupVM::class)
}
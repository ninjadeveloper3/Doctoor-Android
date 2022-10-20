package com.Doctoor.app.ui.modules.landing

import dagger.Module
import dagger.Provides
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule

@Module
class LandingFragmentModule: BaseFragmentModule<LandingFragment>() {

    @Provides
    @ViewModelInjection
    fun provideLandingVM(
        fragment: LandingFragment,
        viewModelProvider: InjectionViewModelProvider<LandingFragmentVM>
    ) = viewModelProvider.get(fragment, LandingFragmentVM::class)
}
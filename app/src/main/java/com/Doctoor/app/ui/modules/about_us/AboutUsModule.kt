package com.Doctoor.app.ui.modules.about_us

import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule
import dagger.Module
import dagger.Provides

@Module
class AboutUsModule : BaseFragmentModule<AboutUsFragment>() {

    @Provides
    @ViewModelInjection
    fun provideAboutUsVM(
        fragment: AboutUsFragment,
        viewModelProvider: InjectionViewModelProvider<AboutUsVM>
    ) = viewModelProvider.get(fragment, AboutUsVM::class)
}
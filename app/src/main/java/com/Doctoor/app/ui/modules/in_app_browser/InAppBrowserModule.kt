package com.Doctoor.app.ui.modules.in_app_browser

import dagger.Module
import dagger.Provides
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule

@Module
class InAppBrowserModule : BaseFragmentModule<InAppBrowserFragment>() {

    @Provides
    @ViewModelInjection
    fun provideInAppBrowserVM(
        fragment: InAppBrowserFragment,
        viewModelProvider: InjectionViewModelProvider<InAppBrowserFragmentVM>
    ) = viewModelProvider.get(fragment, InAppBrowserFragmentVM::class)
}
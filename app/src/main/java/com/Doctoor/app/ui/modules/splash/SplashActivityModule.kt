package com.Doctoor.app.ui.modules.splash

import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.ui_modules.activity.BaseActivityModule
import dagger.Module
import dagger.Provides

@Module
class SplashActivityModule : BaseActivityModule<SplashActivity>() {

    @Provides
    @ViewModelInjection
    fun provideSplashActivityVM(
        activity: SplashActivity,
        viewModelProvider: InjectionViewModelProvider<SplashActivityVM>
    ) = viewModelProvider.get(activity, SplashActivityVM::class)
}
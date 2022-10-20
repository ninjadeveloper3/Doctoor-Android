package com.Doctoor.app.ui.modules.frame

import dagger.Module
import dagger.Provides
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.ui_modules.activity.BaseActivityModule

@Module
class BottomNavigationFrameModule : BaseActivityModule<BottomNavigationFrameActivity>() {

    @Provides
    @ViewModelInjection
    fun provideBottomNavigationFrameVM(
        activity: BottomNavigationFrameActivity,
        viewModelProvider: InjectionViewModelProvider<BottomNavigationFrameVM>
    ) = viewModelProvider.get(activity, BottomNavigationFrameVM::class)
}
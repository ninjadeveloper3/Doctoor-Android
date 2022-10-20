package com.Doctoor.app.ui.modules.frame

import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.ui_modules.activity.BaseActivityModule
import dagger.Module
import dagger.Provides

@Module
class FrameActivityModule : BaseActivityModule<FrameActivity>() {

    @Provides
    @ViewModelInjection
    fun provideFrameActivityVM(
        activity: FrameActivity,
        viewModelProvider: InjectionViewModelProvider<FrameActivityVM>
    ) = viewModelProvider.get(activity, FrameActivityVM::class)


}
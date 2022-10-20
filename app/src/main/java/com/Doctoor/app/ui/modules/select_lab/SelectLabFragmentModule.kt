package com.Doctoor.app.ui.modules.select_lab

import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule
import dagger.Module
import dagger.Provides


@Module
class SelectLabFragmentModule : BaseFragmentModule<SelectLabFragment>() {

    @Provides
    @ViewModelInjection
    fun provideSelectLabVM(
        fragment: SelectLabFragment,
        viewModelProvider: InjectionViewModelProvider<SelectLabFragmentVM>
    ) = viewModelProvider.get(fragment, SelectLabFragmentVM::class)

    @Provides
    fun provideSelectLabFragmentAdapter(fragment: SelectLabFragment) =
        SelectLabFragment.Adapter(
            fragment.mActivity,
            ArrayList(),
            fragment.navigatorHelper
        )
}
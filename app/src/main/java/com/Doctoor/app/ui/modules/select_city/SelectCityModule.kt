package com.Doctoor.app.ui.modules.select_city

import dagger.Module
import dagger.Provides
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule

@Module
class SelectCityModule : BaseFragmentModule<SelectCityFragment>() {

    @Provides
    @ViewModelInjection
    fun provideSelectCityVM(
        fragment: SelectCityFragment,
        viewModelProvider: InjectionViewModelProvider<SelectCityFragmentVM>
    ) = viewModelProvider.get(fragment, SelectCityFragmentVM::class)
}
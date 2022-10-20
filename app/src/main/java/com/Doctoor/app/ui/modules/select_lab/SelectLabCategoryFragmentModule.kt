package com.Doctoor.app.ui.modules.select_lab

import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule
import dagger.Module
import dagger.Provides

@Module
class SelectLabCategoryFragmentModule : BaseFragmentModule<SelectLabCategoryFragment>() {

    @Provides
    @ViewModelInjection
    fun provideSelectLabCategoryVM(
        fragment: SelectLabCategoryFragment,
        viewModelProvider: InjectionViewModelProvider<SelectLabCategoryFragmentVM>
    ) = viewModelProvider.get(fragment, SelectLabCategoryFragmentVM::class)

    @Provides
    fun provideSelectLabCategoryFragmentAdapter(fragment: SelectLabCategoryFragment) =
        SelectLabCategoryFragment.Adapter(
            fragment.mActivity,
            ArrayList(),
            fragment.navigatorHelper
        )
}
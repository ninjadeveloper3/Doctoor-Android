package com.Doctoor.app.ui.modules.select_lab.test.popular_tests

import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule
import dagger.Module
import dagger.Provides

@Module
class PopularTestsCategoryModule : BaseFragmentModule<PopularTestsCategoryFragment>() {

    @Provides
    @ViewModelInjection
    fun providePopularTestsVM(
        fragment: PopularTestsCategoryFragment,
        viewModelProvider: InjectionViewModelProvider<PopularTestsCategoryFragmentVM>
    ) = viewModelProvider.get(fragment, PopularTestsCategoryFragmentVM::class)

    @Provides
    fun providePopularTestsCategoryFragmentAdapter(fragment: PopularTestsCategoryFragment) =
        PopularTestsCategoryFragment.Adapter(
            fragment.mActivity,
            ArrayList(),
            fragment.navigatorHelper
        )
}
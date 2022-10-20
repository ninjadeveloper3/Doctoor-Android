package com.Doctoor.app.ui.modules.myorder.services_history

import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule
import dagger.Module
import dagger.Provides

@Module
class ServicesHistoryFragmentModule : BaseFragmentModule<ServicesHistoryFragment>() {

    @Provides

    @ViewModelInjection
    fun provideServicesHistoryFragmentVM(
        fragment: ServicesHistoryFragment,
        viewModelProvider: InjectionViewModelProvider<ServicesHistoryFragmentVM>
    ) = viewModelProvider.get(fragment, ServicesHistoryFragmentVM::class)

    @Provides
    fun provideServicesHistoryFragmentAdapter(fragment: ServicesHistoryFragment) =
        ServicesHistoryFragment.Adapter(fragment.mActivity, ArrayList(), fragment.navigatorHelper)
}
package com.Doctoor.app.ui.modules.checkout.billing

import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule
import dagger.Module
import dagger.Provides

@Module
class BillingModule : BaseFragmentModule<BillingFragment>() {

    @Provides
    @ViewModelInjection
    fun provideBillingVM(
        fragment: BillingFragment,
        viewModelProvider: InjectionViewModelProvider<BillingFragmentVM>
    ) = viewModelProvider.get(fragment, BillingFragmentVM::class)
}
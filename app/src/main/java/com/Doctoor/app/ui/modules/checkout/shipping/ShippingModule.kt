package com.Doctoor.app.ui.modules.checkout.shipping

import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule
import dagger.Module
import dagger.Provides

@Module
class ShippingModule : BaseFragmentModule<ShippingFragment>() {

    @Provides
    @ViewModelInjection
    fun provideShippingVM(
        fragment: ShippingFragment,
        viewModelProvider: InjectionViewModelProvider<ShippingFragmentVM>
    ) = viewModelProvider.get(fragment, ShippingFragmentVM::class)
}
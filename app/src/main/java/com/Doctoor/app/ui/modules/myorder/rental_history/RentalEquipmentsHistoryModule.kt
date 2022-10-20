package com.Doctoor.app.ui.modules.myorder.rental_history

import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule
import dagger.Module
import dagger.Provides

@Module
class RentalEquipmentsHistoryModule : BaseFragmentModule<RentalEquipmentsHistoryFragment>() {

    @Provides
    @ViewModelInjection
    fun provideRentalEquipmentsHistoryVM(
        fragment: RentalEquipmentsHistoryFragment,
        viewModelProvider: InjectionViewModelProvider<RentalEquipmentsHistoryVM>
    ) = viewModelProvider.get(fragment, RentalEquipmentsHistoryVM::class)

    @Provides
    fun provideRentalEquipmentsHistoryFragmentAdapter(fragment: RentalEquipmentsHistoryFragment) =
        RentalEquipmentsHistoryFragment.Adapter(
            fragment.mActivity,
            ArrayList(),
            fragment.navigatorHelper
        )
}
package com.Doctoor.app.ui.modules.select_equipment.rental_equipments

import dagger.Module
import dagger.Provides
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule

@Module
class RentalEquipmentsModule : BaseFragmentModule<RentalEquipmentsFragment>() {

    @Provides
    @ViewModelInjection
    fun provideRentalEquipmentsVM(
        fragment: RentalEquipmentsFragment,
        viewModelProvider: InjectionViewModelProvider<RentalEquipmentsVM>
    ) = viewModelProvider.get(fragment, RentalEquipmentsVM::class)
}
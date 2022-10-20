package com.Doctoor.app.ui.modules.select_equipment.popular_equipments

import com.Doctoor.app.data.database.AppDatabase
import com.Doctoor.app.data.database.CartManager
import com.Doctoor.app.data.database.EquipmentDao
import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule
import dagger.Module
import dagger.Provides

@Module
class PopularEquipmentsModule : BaseFragmentModule<PopularEquipmentsFragment>() {

    @Provides
    @ViewModelInjection
    fun providePopularEquipmentsVM(
        fragment: PopularEquipmentsFragment,
        viewModelProvider: InjectionViewModelProvider<PopularEquipmentsVM>
    ) = viewModelProvider.get(fragment, PopularEquipmentsVM::class)

    @Provides
    fun provideSelectEquipmentFragmentAdapter(
        fragment: PopularEquipmentsFragment,
        dao: EquipmentDao,
        cartManager: CartManager
    ) =
        PopularEquipmentsFragment.Adapter(
            fragment.mActivity,
            ArrayList(),
            fragment.navigatorHelper,
            dao,
            cartManager
        )

    @Provides
    fun provideEquipmentDao(database: AppDatabase) = database.equipmentDao()

    @Provides
    fun provideCartManager() = CartManager()
}
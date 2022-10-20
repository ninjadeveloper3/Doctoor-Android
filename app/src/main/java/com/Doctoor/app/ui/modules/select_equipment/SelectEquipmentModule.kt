package com.Doctoor.app.ui.modules.select_equipment

import com.Doctoor.app.data.database.AppDatabase
import com.Doctoor.app.data.database.CartManager
import com.Doctoor.app.data.database.EquipmentDao
import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule
import dagger.Module
import dagger.Provides

@Module
class SelectEquipmentModule : BaseFragmentModule<SelectEquipmentFragment>() {

    @Provides
    @ViewModelInjection
    fun provideSelectEquipmentVM(
        fragment: SelectEquipmentFragment,
        viewModelProvider: InjectionViewModelProvider<SelectEquipmentVM>
    ) = viewModelProvider.get(fragment, SelectEquipmentVM::class)

    @Provides
    fun provideSelectEquipmentFragmentAdapter(
        fragment: SelectEquipmentFragment,
        dao: EquipmentDao,
        cartManager: CartManager
    ) =
        SelectEquipmentFragment.Adapter(
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
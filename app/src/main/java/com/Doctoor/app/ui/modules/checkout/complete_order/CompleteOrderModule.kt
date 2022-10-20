package com.Doctoor.app.ui.modules.checkout.complete_order

import com.Doctoor.app.data.database.AppDatabase
import com.Doctoor.app.data.database.CartManager
import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule
import dagger.Module
import dagger.Provides

@Module
class CompleteOrderModule : BaseFragmentModule<CompleteOrderFragment>() {

    @Provides
    @ViewModelInjection
    fun provideCompleteOrderVM(
        fragment: CompleteOrderFragment,
        viewModelProvider: InjectionViewModelProvider<CompleteOrderFragmentVM>
    ) = viewModelProvider.get(fragment, CompleteOrderFragmentVM::class)

    @Provides
    fun provideLabTestDao(database: AppDatabase) = database.labTestDao()

    @Provides
    fun provideMedicineDao(database: AppDatabase) = database.medicineDao()

    @Provides
    fun provideEquipmentDao(database: AppDatabase) = database.equipmentDao()

    @Provides
    fun provideCartManager() = CartManager()
}
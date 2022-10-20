package com.Doctoor.app.ui.modules.product_details

import com.Doctoor.app.data.database.AppDatabase
import com.Doctoor.app.data.database.CartManager
import dagger.Module
import dagger.Provides
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule

@Module
class ProductDetailsModule :BaseFragmentModule<ProductDetailsFragment>(){

    @Provides
    @ViewModelInjection
    fun provideProductDetailsVM(
        fragment: ProductDetailsFragment,
        viewModelProvider: InjectionViewModelProvider<ProductDetailsVM>
    ) = viewModelProvider.get(fragment, ProductDetailsVM::class)

    @Provides
    fun provideEquipmentDao(database: AppDatabase) = database.equipmentDao()

    @Provides
    fun provideCartManager() = CartManager()
}
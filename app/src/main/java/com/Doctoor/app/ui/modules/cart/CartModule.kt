package com.Doctoor.app.ui.modules.cart

import com.Doctoor.app.data.database.*
import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule
import dagger.Module
import dagger.Provides

@Module
class CartModule : BaseFragmentModule<CartFragment>() {

    @Provides
    @ViewModelInjection
    fun provideConfirmSelectionMedicineVM(
        fragment: CartFragment,
        viewModelProvider: InjectionViewModelProvider<CartFragmentVM>
    ) = viewModelProvider.get(fragment, CartFragmentVM::class)

    //    private var equipmentDao: EquipmentDao, private var medicineDao: MedicineDao, private var labTestDao: LabTestDao
    @Provides
    fun provideCartFragmentAdapter(
        fragment: CartFragment,
        equipmentDao: EquipmentDao,
        medicineDao: MedicineDao,
        labTestDao: LabTestDao,
        cartManager: CartManager
    ) =
        CartFragment.Adapter(
            ArrayList(),
            equipmentDao,
            medicineDao,
            labTestDao,
            cartManager,
            fragment.navigatorHelper,
            fragment
        )

    @Provides
    fun provideEquipmentDao(database: AppDatabase) = database.equipmentDao()

    @Provides
    fun provideLabTestDao(database: AppDatabase) = database.labTestDao()

    @Provides
    fun provideMedicineDao(database: AppDatabase) = database.medicineDao()

    @Provides
    fun provideCartManager() = CartManager()
}
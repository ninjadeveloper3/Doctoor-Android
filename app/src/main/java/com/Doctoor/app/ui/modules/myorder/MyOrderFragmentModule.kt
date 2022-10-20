package com.Doctoor.app.ui.modules.myorder

import com.Doctoor.app.base.BaseListDataViewModel
import com.Doctoor.app.data.database.*
import dagger.Module
import dagger.Provides
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule

@Module
class MyOrderFragmentModule : BaseFragmentModule<MyOrderFragment>() {

    @Provides
    @ViewModelInjection
    fun provideMyOrderVM(
            fragment: MyOrderFragment,
            viewModelProvider: InjectionViewModelProvider<MyOrderFragmentVM>
    ) = viewModelProvider.get(fragment, MyOrderFragmentVM::class)

    @Provides
    fun provideMyOrderFragmentFragmentAdapter(fragment: MyOrderFragment, equipmentDao: EquipmentDao, medicineDao: MedicineDao, labTestDao: LabTestDao, cartManager: CartManager) =
            MyOrderFragment.Adapter(fragment.mActivity, ArrayList(), equipmentDao, medicineDao, labTestDao, cartManager, fragment.navigatorHelper)

    @Provides
    fun provideEquipmentDao(database: AppDatabase) = database.equipmentDao()

    @Provides
    fun provideLabTestDao(database: AppDatabase) = database.labTestDao()

    @Provides
    fun provideMedicineDao(database: AppDatabase) = database.medicineDao()

    @Provides
    fun provideCartManager() = CartManager()
}
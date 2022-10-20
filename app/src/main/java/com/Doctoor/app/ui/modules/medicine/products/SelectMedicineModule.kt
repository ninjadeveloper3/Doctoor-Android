package com.Doctoor.app.ui.modules.medicine.products

import com.Doctoor.app.data.database.AppDatabase
import com.Doctoor.app.data.database.CartManager
import com.Doctoor.app.data.database.MedicineDao
import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule
import com.Doctoor.app.ui.modules.medicine.products.SelectMedicineFragment
import dagger.Module
import dagger.Provides
import java.util.*

@Module
class SelectMedicineModule : BaseFragmentModule<SelectMedicineFragment>() {

    @Provides
    @ViewModelInjection
    fun provideSelectMedicineVM(
        fragment: SelectMedicineFragment,
        viewModelProvider: InjectionViewModelProvider<SelectMedicineVM>
    ) = viewModelProvider.get(fragment, SelectMedicineVM::class)

    @Provides
    fun provideSelectMedicineFragmentAdapter(
        fragment: SelectMedicineFragment,
        dao: MedicineDao,
        cartManager: CartManager
    ) =
        SelectMedicineFragment.Adapter(
            fragment.mActivity,
            ArrayList(),
            fragment.navigatorHelper,
            dao,
            cartManager
        )

    @Provides
    fun provideMedicineDao(database: AppDatabase) = database.medicineDao()

    @Provides
    fun provideCartManager() = CartManager()
}
package com.Doctoor.app.ui.modules.medicine.details

import com.Doctoor.app.data.database.AppDatabase
import com.Doctoor.app.data.database.CartManager
import com.Doctoor.app.data.database.MedicineDao
import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule
import com.Doctoor.app.ui.modules.medicine.products.SelectMedicineFragment
import dagger.Module
import dagger.Provides
import java.util.ArrayList

@Module
class MedicineDetailsModule : BaseFragmentModule<MedicineDetailsFragment>() {

    @Provides
    @ViewModelInjection
    fun provideMedicineDetailsVM(
        fragment: MedicineDetailsFragment,
        viewModelProvider: InjectionViewModelProvider<MedicineDetailsVM>
    ) = viewModelProvider.get(fragment, MedicineDetailsVM::class)

    @Provides
    fun provideMedicineDao(database: AppDatabase) = database.medicineDao()

    @Provides
    fun provideCartManager() = CartManager()
}
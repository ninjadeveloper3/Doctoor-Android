package com.Doctoor.app.ui.modules.checkout.payment

import com.Doctoor.app.data.database.AppDatabase
import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule
import dagger.Module
import dagger.Provides

@Module
class PaymentModule : BaseFragmentModule<PaymentFragment>() {

    @Provides
    @ViewModelInjection
    fun providePaymentVM(
        fragment: PaymentFragment,
        viewModelProvider: InjectionViewModelProvider<PaymentFragmentVM>
    ) = viewModelProvider.get(fragment, PaymentFragmentVM::class)

    @Provides
    fun provideLabTestDao(database: AppDatabase) = database.labTestDao()

    @Provides
    fun provideMedicineDao(database: AppDatabase) = database.medicineDao()

    @Provides
    fun provideEquipmentDao(database: AppDatabase) = database.equipmentDao()

}
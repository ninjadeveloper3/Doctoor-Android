package com.Doctoor.app.ui.modules.easypaisa

import com.Doctoor.app.data.database.AppDatabase
import com.Doctoor.app.data.database.CartManager
import dagger.Module
import dagger.Provides
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule
import com.Doctoor.app.ui.modules.checkout.payment.PaymentFragment

@Module
class EasypaisaPaymentModule: BaseFragmentModule<EasypaisaPaymentFragment>()  {

    @Provides
    @ViewModelInjection
    fun provideEasypaisaPaymentVM(
        fragment: EasypaisaPaymentFragment,
        viewModelProvider: InjectionViewModelProvider<EasypaisaPaymentVM>
    )= viewModelProvider.get(fragment, EasypaisaPaymentVM::class)


    @Provides
    fun provideLabTestDao(database: AppDatabase) = database.labTestDao()

    @Provides
    fun provideMedicineDao(database: AppDatabase) = database.medicineDao()

    @Provides
    fun provideEquipmentDao(database: AppDatabase) = database.equipmentDao()

    @Provides
    fun provideCartManager() = CartManager()
}
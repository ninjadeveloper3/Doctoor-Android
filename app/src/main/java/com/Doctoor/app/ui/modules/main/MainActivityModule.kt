package com.Doctoor.app.ui.modules.main


import com.Doctoor.app.data.database.AppDatabase
import com.Doctoor.app.data.database.CartManager
import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.ui_modules.activity.BaseActivityModule
import com.Doctoor.app.preference.DataStoreManager
import dagger.Module
import dagger.Provides

@Module
public class MainActivityModule : BaseActivityModule<MainActivity>() {

    @Provides
    @ViewModelInjection
    fun provideMainActivity(
        activity: MainActivity,
        viewModelProvider: InjectionViewModelProvider<MainActivityVM>
    ) = viewModelProvider.get(activity, MainActivityVM::class)

    @Provides
    fun provideDrawerHolder(activity: MainActivity): MainDrawerHolderV2 {
        return MainDrawerHolderV2(activity.navigatorHelper)
    }

    @Provides
    fun provideNotificationFragmentAdapter(
        activity: MainActivity,
        dataStoreManager: DataStoreManager
    ) =
        MainActivity.Adapter(ArrayList(), activity.navigatorHelper, dataStoreManager, activity)

    @Provides
    fun provideEquipmentDao(database: AppDatabase) = database.equipmentDao()

    @Provides
    fun provideLabTestDao(database: AppDatabase) = database.labTestDao()

    @Provides
    fun provideMedicineDao(database: AppDatabase) = database.medicineDao()

    @Provides
    fun provideCartManager() = CartManager()

}
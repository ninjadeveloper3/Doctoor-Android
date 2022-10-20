package com.Doctoor.app.ui.modules.select_lab.test

import com.Doctoor.app.data.database.AppDatabase
import com.Doctoor.app.data.database.CartManager
import com.Doctoor.app.data.database.LabTestDao
import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule
import dagger.Module
import dagger.Provides

@Module
class SelectTestFragmentModule : BaseFragmentModule<SelectTestFragment>() {

    @Provides
    @ViewModelInjection
    fun provideSelectTestVM(
        fragment: SelectTestFragment,
        viewModelProvider: InjectionViewModelProvider<SelectTestFragmentVM>
    ) = viewModelProvider.get(fragment, SelectTestFragmentVM::class)

    @Provides
    fun provideSelectTestFragmentAdapter(
        fragment: SelectTestFragment,
        dao: LabTestDao,
        cartManager: CartManager
    ) =
        SelectTestFragment.Adapter(
            fragment.mActivity,
            ArrayList(),
            fragment.navigatorHelper,
            dao,
            cartManager
        )

    @Provides
    fun provideLabTestDao(database: AppDatabase) = database.labTestDao()

    @Provides
    fun provideCartManager() = CartManager()
}
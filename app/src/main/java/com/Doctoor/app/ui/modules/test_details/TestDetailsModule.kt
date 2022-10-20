package com.Doctoor.app.ui.modules.test_details

import com.Doctoor.app.data.database.AppDatabase
import com.Doctoor.app.data.database.CartManager
import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule
import dagger.Module
import dagger.Provides

@Module
class TestDetailsModule : BaseFragmentModule<TestDetailsFragment>() {

    @Provides
    @ViewModelInjection
    fun provideTestDetailsVM(
        fragment: TestDetailsFragment,
        viewModelProvider: InjectionViewModelProvider<TestDetailsVM>
    ) = viewModelProvider.get(fragment, TestDetailsVM::class)

    @Provides
    fun provideLabTestDao(database: AppDatabase) = database.labTestDao()

    @Provides
    fun provideCartManager() = CartManager()
}
package com.Doctoor.app.ui.modules.medicine.category

import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule
import com.Doctoor.app.ui.modules.medicine.category.MedicineCategoryFragment
import dagger.Module
import dagger.Provides

@Module
class MedicineCategoryFragmentModule : BaseFragmentModule<MedicineCategoryFragment>() {

    @Provides
    @ViewModelInjection
    fun provideMedicineCategoryVM(
        fragment: MedicineCategoryFragment,
        viewModelProvider: InjectionViewModelProvider<MedicineCategoryFragmentVM>
    ) = viewModelProvider.get(fragment, MedicineCategoryFragmentVM::class)

    @Provides
    fun provideMedicineCategoryFragmentAdapter(fragment: MedicineCategoryFragment) =
        MedicineCategoryFragment.Adapter(
            fragment.mActivity,
            ArrayList(),
            fragment.navigatorHelper
        )
}
package com.Doctoor.app.ui.modules.medicine.category.popular_medicines

import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule
import dagger.Module
import dagger.Provides

@Module
class PopularMedicinesModule : BaseFragmentModule<PopularMedicinesFragment>() {

    @Provides
    @ViewModelInjection
    fun providePopularMedicinesVM(
        fragment: PopularMedicinesFragment,
        viewModelProvider: InjectionViewModelProvider<PopularMedicinesFragmentVM>
    ) = viewModelProvider.get(fragment, PopularMedicinesFragmentVM::class)

    @Provides
    fun providePopularMedicinesFragmentAdapter(fragment: PopularMedicinesFragment) =
        PopularMedicinesFragment.Adapter(
            fragment.mActivity,
            ArrayList(),
            fragment.navigatorHelper
        )
}
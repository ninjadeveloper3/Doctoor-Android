package com.Doctoor.app.ui.modules.dashboard

import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule
import com.Doctoor.app.ui.adapters.SliderAdapter
import dagger.Module
import dagger.Provides

@Module
class HomeFragmentModule : BaseFragmentModule<HomeFragment>() {

    @Provides
    @ViewModelInjection
    fun provideMainVM(
        fragment: HomeFragment,
        viewModelProvider: InjectionViewModelProvider<HomeFragmentVM>
    ) = viewModelProvider.get(fragment, HomeFragmentVM::class)

    @Provides
    fun provideMainFragmentAdapter(fragment: HomeFragment) =
        HomeFragment.Adapter(ArrayList(), fragment.navigatorHelper)

    @Provides
    fun provideSliderAdapter(fragment: HomeFragment) =
        SliderAdapter(ArrayList())

//    @Provides
//    fun providePermissionHelper(fragment: HomeFragment): PermissionHelper {
//        return PermissionHelper(fragment, fragment)
//    }

}
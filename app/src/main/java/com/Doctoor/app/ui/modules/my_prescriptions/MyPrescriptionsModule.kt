package com.Doctoor.app.ui.modules.my_prescriptions

import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule
import com.Doctoor.app.utils.permissions.PermissionHelper
import dagger.Module
import dagger.Provides

@Module
class MyPrescriptionsModule : BaseFragmentModule<MyPrescriptionsFragment>() {

    @Provides
    @ViewModelInjection
    fun provideMyPrescriptionsVM(
        fragment: MyPrescriptionsFragment,
        viewModelProvider: InjectionViewModelProvider<MyPrescriptionsVM>
    ) = viewModelProvider.get(fragment, MyPrescriptionsVM::class)

    @Provides
    fun providePermissionHelper(fragment: MyPrescriptionsFragment): PermissionHelper {
        return PermissionHelper(fragment, fragment)
    }

    @Provides
    fun provideMyPrescriptionsFragmentAdapter(
        fragment: MyPrescriptionsFragment,
        permissionHelper: PermissionHelper
    ) =
        MyPrescriptionsFragment.Adapter(
            fragment.mActivity,
            ArrayList(),
            fragment.navigatorHelper,
            permissionHelper
        )
}
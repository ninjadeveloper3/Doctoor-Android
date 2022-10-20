package com.Doctoor.app.ui.modules.lab_reports

import com.Doctoor.app.base.BaseListDataViewModel
import dagger.Module
import dagger.Provides
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule
import com.Doctoor.app.imagepicker.ImagePicker
import com.Doctoor.app.ui.modules.upload_prescription.UploadPrescriptionFragment
import com.Doctoor.app.utils.permissions.PermissionHelper

@Module
class LabReportsFragmentModule : BaseFragmentModule<LabReportsFragment>() {

    @Provides
    @ViewModelInjection
    fun provideLabReportsVM(
            fragment: LabReportsFragment,
            viewModelProvider: InjectionViewModelProvider<LabReportsFragmentVM>
    ) = viewModelProvider.get(fragment, LabReportsFragmentVM::class)

    @Provides
    fun providePermissionHelper(fragment: LabReportsFragment): PermissionHelper {
        return PermissionHelper(fragment, fragment)
    }

    @Provides
    fun provideLabReportsFragmentAdapter(fragment: LabReportsFragment, permissionHelper: PermissionHelper) =
            LabReportsFragment.Adapter(fragment.mActivity, ArrayList(), fragment.navigatorHelper, permissionHelper)
}
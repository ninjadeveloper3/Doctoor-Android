package com.Doctoor.app.ui.modules.upload_prescription

import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule
import com.Doctoor.app.imagepicker.ImagePicker
import com.Doctoor.app.utils.permissions.PermissionHelper
import dagger.Module
import dagger.Provides

@Module
class UploadPrescriptionModule : BaseFragmentModule<UploadPrescriptionFragment>() {

    @Provides
    @ViewModelInjection
    fun provideUploadPrescriptionVM(
        fragment: UploadPrescriptionFragment,
        viewModelProvider: InjectionViewModelProvider<UploadPrescriptionVM>
    ) = viewModelProvider.get(fragment, UploadPrescriptionVM::class)

    @Provides
    fun providePermissionHelper(fragment: UploadPrescriptionFragment): PermissionHelper {
        return PermissionHelper(fragment, fragment)
    }

    @Provides
    fun provideImagePickerBuilder(fragment: UploadPrescriptionFragment): ImagePicker.Builder {
        return ImagePicker.with(fragment)
    }
}
package com.Doctoor.app.ui.modules.upload_prescription_submit

import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule
import dagger.Module
import dagger.Provides

@Module
class UploadPrescriptionSubmitModule : BaseFragmentModule<UploadPrescriptionSubmitFragment>() {

    @Provides
    @ViewModelInjection
    fun provideUploadPrescriptionSubmitVM(
        fragment: UploadPrescriptionSubmitFragment,
        viewModelProvider: InjectionViewModelProvider<UploadPrescriptionSubmitVM>
    ) = viewModelProvider.get(fragment, UploadPrescriptionSubmitVM::class)
}
package com.Doctoor.app.ui.modules.profile.edit_profile

import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule
import com.Doctoor.app.imagepicker.ImagePicker
import com.Doctoor.app.utils.permissions.PermissionHelper
import dagger.Module
import dagger.Provides

@Module
class EditProfileModule : BaseFragmentModule<EditProfileFragment>() {

    @Provides
    @ViewModelInjection
    fun provideEditProfileVM(
        fragment: EditProfileFragment,
        viewModelProvider: InjectionViewModelProvider<EditProfileFragmentVM>
    ) = viewModelProvider.get(fragment, EditProfileFragmentVM::class)

    @Provides
    fun providePermissionHelper(fragment: EditProfileFragment): PermissionHelper {
        return PermissionHelper(fragment, fragment)
    }

    @Provides
    fun provideImagePickerBuilder(fragment: EditProfileFragment): ImagePicker.Builder {
        return ImagePicker.with(fragment)
    }
}
package com.Doctoor.app.ui.modules.profile

import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule
import dagger.Module
import dagger.Provides

@Module
class UserProfileModule : BaseFragmentModule<UserProfileFragment>() {

    @Provides
    @ViewModelInjection
    fun provideUserProfileVM(
        fragment: UserProfileFragment,
        viewModelProvider: InjectionViewModelProvider<UserProfileVM>
    ) = viewModelProvider.get(fragment, UserProfileVM::class)
}
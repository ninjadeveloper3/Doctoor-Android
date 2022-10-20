package com.Doctoor.app.ui.modules.new_password

import dagger.Module
import dagger.Provides
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule

@Module
class NewPasswordModule : BaseFragmentModule<NewPasswordFragment>() {

    @Provides
    @ViewModelInjection
    fun provideNewPasswordVM(
        fragment: NewPasswordFragment,
        viewModelProvider: InjectionViewModelProvider<NewPasswordVM>
    ) = viewModelProvider.get(fragment, NewPasswordVM::class)
}
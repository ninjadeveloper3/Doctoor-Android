package com.Doctoor.app.ui.modules.login

import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule
import com.Doctoor.app.ui.modules.social_login.FacebookHelper
import com.Doctoor.app.ui.modules.social_login.GoogleSignInHelper
import dagger.Module
import dagger.Provides

@Module
class LoginFragmentModule : BaseFragmentModule<LoginFragment>() {

    @Provides
    @ViewModelInjection
    fun provideLoginVM(
        fragment: LoginFragment,
        viewModelProvider: InjectionViewModelProvider<LoginFragmentVM>
    ) = viewModelProvider.get(fragment, LoginFragmentVM::class)

    @Provides
    fun facebookHelper(fragment: LoginFragment, loginFragmentVM: LoginFragmentVM): FacebookHelper {
        return FacebookHelper(fragment, loginFragmentVM)
    }

    @Provides
    fun googleSignInHelper(
        fragment: LoginFragment,
        loginFragmentVM: LoginFragmentVM
    ): GoogleSignInHelper {
        return GoogleSignInHelper(fragment, loginFragmentVM)
    }
}
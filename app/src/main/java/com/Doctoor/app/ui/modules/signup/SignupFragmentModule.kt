package com.Doctoor.app.ui.modules.signup

import com.Doctoor.app.di.InjectionViewModelProvider
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.di.ui_modules.fragment.BaseFragmentModule
import com.Doctoor.app.ui.modules.social_login.FacebookHelper
import com.Doctoor.app.ui.modules.social_login.GoogleSignInHelper
import dagger.Module
import dagger.Provides

@Module
class SignupFragmentModule : BaseFragmentModule<SignupFragment>() {

    @Provides
    @ViewModelInjection
    fun provideSignupVM(
            fragment: SignupFragment,
            viewModelProvider: InjectionViewModelProvider<SignupFragmentVM>
    ) = viewModelProvider.get(fragment, SignupFragmentVM::class)

    @Provides
    fun facebookHelper(fragment: SignupFragment,signupFragmentVM: SignupFragmentVM): FacebookHelper {
        return FacebookHelper(fragment, signupFragmentVM)
    }

    @Provides
    fun googleSignInHelper(fragment: SignupFragment,signupFragmentVM: SignupFragmentVM): GoogleSignInHelper {
        return GoogleSignInHelper(fragment, signupFragmentVM)
    }
}
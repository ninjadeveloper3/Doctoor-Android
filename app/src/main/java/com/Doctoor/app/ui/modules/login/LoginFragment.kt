package com.Doctoor.app.ui.modules.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.Nullable
import com.Doctoor.app.BR
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseViewModelFragment
import com.Doctoor.app.data.source.State
import com.Doctoor.app.data.source.Status
import com.Doctoor.app.databinding.FragmentLoginBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.ui.interfaces.SocialNavigator
import com.Doctoor.app.ui.modules.code_verification.CodeVerificationFragment
import com.Doctoor.app.ui.modules.social_login.FacebookHelper
import com.Doctoor.app.ui.modules.social_login.GoogleSignInHelper
import com.Doctoor.app.utils.AlertUtils
import com.Doctoor.app.utils.Constants
import com.Doctoor.app.utils.debug
import com.Doctoor.app.utils.validation.Validator
import com.Doctoor.app.widget.progressbutton.OnAnimationEndListener
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.privacy_policy.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import javax.inject.Inject

class LoginFragment : BaseViewModelFragment<FragmentLoginBinding, LoginFragmentVM>(),
    SocialNavigator {
    override fun onMainActivity() {
        navigatorHelper.navigateMainActivity(true)
    }

    override fun onPhoneReg(bundle: Bundle) {
    }

    override fun layoutRes() = R.layout.fragment_login

    companion object {
        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<LoginFragmentVM>

    @Inject
    lateinit var facebookHelper: FacebookHelper

    @Inject
    lateinit var googleSignInHelper: GoogleSignInHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewModel().facebookHelper = facebookHelper
        getViewModel().googleSignInHelper = googleSignInHelper
        getViewModel().validator = Validator(getViewDataBinding())
//        getmViewModel().socialNavigator = this
//        getmViewModel().socialResponse.observe(this, Observer { isExist ->
//            navigatorHelper.navigateMainActivity(true)
//        })
    }

    override fun getToolBarTile() = ""

    override fun toolbarColor() = 0
    override fun getViewModel() = viewModel.get()

    override fun getBindingVariable() = BR.loginFragmentVM

    override fun onResume() {
        super.onResume()
        KeyboardVisibilityEvent.setEventListener(
            getBaseActivity()
        ) {
            if (it) privacyPolicy.visibility = View.GONE else privacyPolicy.visibility =
                View.VISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        debug("data $data")
        if (resultCode == Activity.RESULT_OK && requestCode == googleSignInHelper.RC_SIGN_IN) {
            googleSignInHelper.onActivityResult(requestCode, resultCode, data)
        } else if (resultCode == Activity.RESULT_OK) {
            facebookHelper.onActivityResult(requestCode, resultCode, data!!)
        }
    }

    //  @CallSuper
    override fun handleState(@Nullable state: State?) = when {
        state?.status == Status.LOADING -> {
            login.revertAnimation()
            login.startAnimation()
        }
        state?.status == Status.ERROR -> login.revertAnimation(object : OnAnimationEndListener {
            override fun onAnimationEnd() {
                AlertUtils.showAlertDialog(getBaseActivity(), state.message)
            }
        })
        state?.status == Status.SUCCESS -> login.revertAnimation(object : OnAnimationEndListener {
            override fun onAnimationEnd() {
                AlertUtils.showAlertDialog(getBaseActivity(), state.message) { _, i ->
                    val bundle = Bundle()
                    bundle.putInt(Constants.ID, getmViewModel().id.value!!)

                    navigatorHelper.startFragment<CodeVerificationFragment>(
                        CodeVerificationFragment::class.java.name,
                        false, bundle
                    )
                }
            }
        })
        else -> login.revertAnimation(object : OnAnimationEndListener {
            override fun onAnimationEnd() {

            }
        })
    }
    //  super.handleState(state)

//        toastNow("adadas>> "+state?.status)
//        getViewDataBinding().state = state

}
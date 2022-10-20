package com.Doctoor.app.ui.modules.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.Nullable
import com.Doctoor.app.BR
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseViewModelFragment
import com.Doctoor.app.data.source.State
import com.Doctoor.app.data.source.Status
import com.Doctoor.app.databinding.FragmentUserProfileBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.utils.AlertUtils
import com.Doctoor.app.utils.validation.Validator
import com.Doctoor.app.widget.progressbutton.OnAnimationEndListener
import kotlinx.android.synthetic.main.fragment_user_profile.*
import javax.inject.Inject

class UserProfileFragment : BaseViewModelFragment<FragmentUserProfileBinding, UserProfileVM>() {

    override fun layoutRes() = R.layout.fragment_user_profile
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    companion object {
        fun newInstance(): UserProfileFragment {
            return UserProfileFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getViewModel().validator = Validator(getViewDataBinding())

    }

    override fun onResume() {
        super.onResume()
        getViewModel().setUserProfileDate()
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<UserProfileVM>


    override fun getToolBarTile() = getString(R.string.profile)

    override fun toolbarColor() = 0

    override fun getViewModel() = viewModel.get()

    override fun getBindingVariable() = BR.userProfileFragmentVM

    override fun handleState(@Nullable state: State?) = when {
        state?.status == Status.LOADING -> {
            Log.d("User profile Loading>>", "Status>>" + state.status)
            save.revertAnimation()
            save.startAnimation()
        }
        state?.status == Status.ERROR -> save.revertAnimation(object : OnAnimationEndListener {
            override fun onAnimationEnd() {
                AlertUtils.showAlertDialog(getBaseActivity(), state.message)
            }
        })
        else -> save.revertAnimation(object : OnAnimationEndListener {
            override fun onAnimationEnd() {

            }
        })
    }

}
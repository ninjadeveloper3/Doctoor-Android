package com.Doctoor.app.ui.modules.signup

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.annotation.Nullable
import androidx.lifecycle.Observer
import com.Doctoor.app.BR
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseViewModelFragment
import com.Doctoor.app.data.source.State
import com.Doctoor.app.data.source.Status
import com.Doctoor.app.databinding.FragmentSignupBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.ui.modules.social_login.FacebookHelper
import com.Doctoor.app.ui.modules.social_login.GoogleSignInHelper
import com.Doctoor.app.utils.AlertUtils
import com.Doctoor.app.utils.DateUtils
import com.Doctoor.app.utils.toast
import com.Doctoor.app.utils.validation.Validator
import com.Doctoor.app.widget.progressbutton.OnAnimationEndListener
import kotlinx.android.synthetic.main.fragment_signup.*
import kotlinx.android.synthetic.main.privacy_policy.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import java.util.*
import javax.inject.Inject

class SignupFragment : BaseViewModelFragment<FragmentSignupBinding, SignupFragmentVM>(),
    DatePickerDialog.OnDateSetListener {

    override fun onDateSet(
        view: DatePicker,
        selectedYear: Int,
        selectedMonth: Int,
        selectedDay: Int
    ) {
        val date = DateUtils.formatDate(selectedYear, selectedMonth, selectedDay)
        getViewModel().date.postValue(date)
    }

    override fun layoutRes() = R.layout.fragment_signup

    companion object {
        fun newInstance(): SignupFragment {
            return SignupFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<SignupFragmentVM>

    @Inject
    lateinit var facebookHelper: FacebookHelper

    @Inject
    lateinit var googleSignInHelper: GoogleSignInHelper

    var datePickerDialog: DatePickerDialog? = null;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewModel().facebookHelper = facebookHelper
        getViewModel().googleSignInHelper = googleSignInHelper

        getViewModel().validator = Validator(getViewDataBinding())

    }

    private fun initialiseCalender() {

        val calendar = DateUtils.calendar()

        calendar.add(Calendar.YEAR, -30)

        datePickerDialog = DatePickerDialog(
            this.requireContext(),
            this,
            DateUtils.getYear(),
            DateUtils.getMonth(),
            DateUtils.getDayOfMonth()
        )

        datePickerDialog?.datePicker?.minDate = calendar.timeInMillis;

        getViewModel().getTimePickerDialogData().observe(this, Observer { display ->
            datePickerDialog?.show()
        })

        getmViewModel().onSuccessMsg.observe(
            this,
            Observer { toast(getmViewModel().onSuccessMsg.value.toString()) })

    }

    override fun onStart() {
        super.onStart()
        googleSignInHelper.onStart()
    }

    override fun getToolBarTile() = ""

    override fun toolbarColor() = 0
    override fun getViewModel() = viewModel.get()

    override fun getBindingVariable() = BR.signupFragmentVM

    override fun onResume() {
        super.onResume()
        KeyboardVisibilityEvent.setEventListener(
            getBaseActivity()
        ) {
            if (it) privacyPolicy.visibility = View.GONE else privacyPolicy.visibility =
                View.VISIBLE
            // some code depending on keyboard visiblity status
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == googleSignInHelper.RC_SIGN_IN) {
            googleSignInHelper.onActivityResult(requestCode, resultCode, data)
        } else if (resultCode == Activity.RESULT_OK) {
            facebookHelper.onActivityResult(requestCode, resultCode, data!!)
        }
    }

    //  @CallSuper
    override fun handleState(@Nullable state: State?) = when {
        state?.status == Status.LOADING -> {
            signup.revertAnimation()
            signup.startAnimation()
        }
        state?.status == Status.ERROR -> signup.revertAnimation(object : OnAnimationEndListener {
            override fun onAnimationEnd() {
                AlertUtils.showAlertDialog(getBaseActivity(), state.message)
            }
        })
        else -> signup.revertAnimation(object : OnAnimationEndListener {
            override fun onAnimationEnd() {
            }
        })
    }
}

package com.Doctoor.app.ui.modules.profile.edit_profile

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.annotation.Nullable
import com.Doctoor.app.BR
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseViewModelFragment
import com.Doctoor.app.data.source.State
import com.Doctoor.app.data.source.Status
import com.Doctoor.app.databinding.FragmentEditProfileBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.imagepicker.ImagePicker
import com.Doctoor.app.utils.AlertUtils
import com.Doctoor.app.utils.DateUtils
import com.Doctoor.app.utils.permissions.PermissionHelper
import com.Doctoor.app.utils.permissions.PermissionManager
import com.Doctoor.app.utils.permissions.enums.PermissionEnum
import com.Doctoor.app.utils.permissions.interfaces.FullCallback
import com.Doctoor.app.utils.validation.Validator
import com.Doctoor.app.widget.progressbutton.OnAnimationEndListener
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import java.util.*
import javax.inject.Inject

class EditProfileFragment :
    BaseViewModelFragment<FragmentEditProfileBinding, EditProfileFragmentVM>(),
    DatePickerDialog.OnDateSetListener, FullCallback {

    @Inject
    lateinit var builder: ImagePicker.Builder

    @Inject
    protected lateinit var permissionHelper: PermissionHelper

    override fun result(
        permissionsGranted: ArrayList<PermissionEnum>,
        permissionsDenied: ArrayList<PermissionEnum>,
        permissionsDeniedForever: ArrayList<PermissionEnum>,
        permissionsAsked: ArrayList<PermissionEnum>, key: Int
    ) {
        when {
            permissionsGranted.size == permissionsAsked.size -> {
                getViewModel().onGalleryClick()
            }
            permissionsDeniedForever.size > 0 -> //If user answer "Never ask again" to a request for permission, you can redirect user to app settings, with an utils
                permissionHelper.showDialog(getString(R.string.permission_gallery_denied))
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        PermissionManager.handleResult(this, requestCode, permissions, grantResults)
    }

    override fun layoutRes() = R.layout.fragment_edit_profile
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    companion object {
        fun newInstance(): EditProfileFragment {
            return EditProfileFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<EditProfileFragmentVM>

    override fun getToolBarTile() = getString(R.string.profile)

    override fun toolbarColor() = 0

    override fun getViewModel() = viewModel.get()

    override fun getBindingVariable() = BR.editProfileFragmentVM

    override fun onDateSet(
        view: DatePicker,
        selectedYear: Int,
        selectedMonth: Int,
        selectedDay: Int
    ) {
        val date = DateUtils.formatDate(selectedYear, selectedMonth, selectedDay)
//        getViewModel().date.postValue(date)
    }

    var datePickerDialog: DatePickerDialog? = null;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewModel().validator = Validator(getViewDataBinding())
        initialiseCalender()
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

        datePickerDialog?.datePicker?.minDate = calendar.timeInMillis

        getViewModel().timePickerDialogData.observe(this, androidx.lifecycle.Observer { display ->
            datePickerDialog?.show()
        })
    }

    override fun handleState(@Nullable state: State?) = when {
        state?.status == Status.LOADING -> {
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
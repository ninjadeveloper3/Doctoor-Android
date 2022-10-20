package com.Doctoor.app.ui.modules.profile

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.Doctoor.app.base.BaseLoginViewModel
import com.Doctoor.app.data.repository.UserRepository
import com.Doctoor.app.preference.DataStoreManager
import com.Doctoor.app.ui.modules.profile.edit_profile.EditProfileFragment
import javax.inject.Inject

class UserProfileVM @Inject constructor(
    val userManager: DataStoreManager,
    private val userRepo: UserRepository
) : BaseLoginViewModel() {

    var fullName = MutableLiveData<String>()
        set(value) {
            notifyChange()
            field = value
        }
    var gender = MutableLiveData<String>()
        set(value) {
            notifyChange()
            field = value
        }
    var phone = MutableLiveData<String>()
        set(value) {
            notifyChange()
            field = value
        }
    var email = MutableLiveData<String>()
        set(value) {
            notifyChange()
            field = value
        }
    var avatar = MutableLiveData<String>()
        set(value) {
            notifyChange()
            field = value
        }
    var date = MutableLiveData<String>()

    override fun onFirsTimeUiCreate(bundle: Bundle?) {
        super.onFirsTimeUiCreate(bundle)
        setUserProfileDate()
    }

    fun setUserProfileDate() {
        fullName.value = userManager.currentUser?.full_name
        gender.value = userManager.currentUser?.gender
        phone.value = "+" + userManager.currentUser?.phone
        email.value = userManager.currentUser?.email
        avatar.value = userManager.currentUser?.avatar
    }

    fun onEditPress() {
        navigatorHelper?.startFragmentWithToolbar<EditProfileFragment>(
            EditProfileFragment::class.java.name,
            false
        )
    }
}
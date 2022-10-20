package com.Doctoor.app.ui.modules.doctor_profile

import android.os.Bundle
import com.Doctoor.app.base.BaseViewModel
import com.Doctoor.app.ui.modules.cart.CartFragment
import javax.inject.Inject

class DoctorProfileVM @Inject constructor() : BaseViewModel() {
    override fun onFirsTimeUiCreate(bundle: Bundle?) {
    }
    public fun checkOut() =
        navigatorHelper?.startFragmentWithToolbar<CartFragment>(CartFragment::class.java.name)

    public fun continueShopping() =
            navigatorHelper?.navigateMainActivity(true)
}
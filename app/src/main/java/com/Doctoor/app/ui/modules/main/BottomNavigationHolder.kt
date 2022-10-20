package com.Doctoor.app.ui.modules.main

import com.google.android.material.snackbar.Snackbar
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseViewModel
import com.Doctoor.app.base.BaseViewModelFragment
import com.Doctoor.app.navigation.NavigatorHelper
import com.Doctoor.app.preference.DataStoreManager
import com.Doctoor.app.ui.interfaces.SnackbarCallBack
import com.Doctoor.app.ui.modules.dashboard.HomeFragment
import com.Doctoor.app.ui.modules.landing.LandingFragment
import com.Doctoor.app.ui.modules.myorder.MyOrderPagerFragment
import com.Doctoor.app.ui.modules.notification.NotificationFragment
import com.Doctoor.app.ui.modules.profile.UserProfileFragment
import com.Doctoor.app.utils.AlertUtils
import com.Doctoor.app.utils.setBoldFontTypeSpan
import com.Doctoor.app.widget.bottomnavigation.BottomNavigation
import javax.inject.Inject


class BottomNavigationHolder @Inject
constructor(
    private val navigatorHelper: NavigatorHelper,
    private val activity: MainActivity,
    private val dataStoreManager: DataStoreManager
) : BottomNavigation.OnMenuItemSelectionListener, SnackbarCallBack {
    private lateinit var navigationView: BottomNavigation

    //    private var previousIndex: Int = 0
    private lateinit var fragment: BaseViewModelFragment<*, *>

    fun init(view: BottomNavigation) {
        navigationView = view
        navigationView.menuItemSelectionListener = this
        setDefaultSelectedIndex(0)
    }

    fun setDefaultSelectedIndex(index: Int) = navigationView.setDefaultSelectedIndex(index)
    fun setSelectedIndex(index: Int) = navigationView.setSelectedIndex(index, false)
    override fun onShown(snackbar: Snackbar) {
        setSelectedIndex(0)
    }

    override fun onMenuItemSelect(itemId: Int, position: Int, fromUser: Boolean) {
        when (itemId) {
            R.id.home -> {
                fragment = HomeFragment.newInstance()
            }
            R.id.notification -> {
                if (!isLogin()) {
                    navigateToLogin()
                    return
                } else
                    fragment = NotificationFragment.newInstance()
            }
            R.id.myOrder -> {
                if (!isLogin()) {
                    navigateToLogin()
                    return
                } else
                    fragment = MyOrderPagerFragment.newInstance()
            }
            R.id.profile -> {
                if (!isLogin()) {
                    navigateToLogin()
                    return
                } else
                    fragment = UserProfileFragment.newInstance()
            }
        }

        navigatorHelper.replaceFragment<BaseViewModelFragment<*, *>>(R.id.container, fragment)
        activity.viewModel.get().toolbarTitle.postValue(
            activity.setBoldFontTypeSpan(
                fragment.getToolBarTile(),
                R.font.mark_pro_light
            )
        )
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun onNotificationEvent(event: NotificationEvent) {
//        onMenuItemReselect(R.id.notification, 2, true)
//    }

    private fun navigateToLogin() {

        /*select home by default*/
        AlertUtils.showSnackBarLongMessage(

            navigationView, "Please log in to continue", "Login", {
                login()
            }, this

        )
    }

    fun login() {
        navigatorHelper.startFragment<LandingFragment>(LandingFragment::class.java.name, false)
    }

    private fun isLogin(): Boolean {
        return BaseViewModel.isLogin.value!!
    }

    override fun onMenuItemReselect(itemId: Int, position: Int, fromUser: Boolean) {
//        when (itemId) {
//            R.id.profile -> {
//                navigatorHelper.startFragmentWithBottomNavigation<UserProfileFragment>(
//                    UserProfileFragment::class.java.name,
//                    showCartMenu = false
//                )
//            }
//        }
    }
}
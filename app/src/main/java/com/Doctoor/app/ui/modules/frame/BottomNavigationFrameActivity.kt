package com.Doctoor.app.ui.modules.frame

import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.Doctoor.app.BR
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseViewModel
import com.Doctoor.app.base.BaseViewModel.Companion.cartItemsCount
import com.Doctoor.app.base.BaseViewModelActivity
import com.Doctoor.app.base.BaseViewModelFragment
import com.Doctoor.app.databinding.ActivityBottomNavigationFrameBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.preference.DataStoreManager
import com.Doctoor.app.ui.interfaces.SnackbarCallBack
import com.Doctoor.app.ui.modules.cart.CartFragment
import com.Doctoor.app.ui.modules.landing.LandingFragment
import com.Doctoor.app.ui.modules.myorder.MyOrderPagerFragment
import com.Doctoor.app.ui.modules.notification.NotificationFragment
import com.Doctoor.app.ui.modules.profile.UserProfileFragment
import com.Doctoor.app.utils.*
import com.Doctoor.app.utils.Constants.EXTRA
import com.Doctoor.app.widget.bottomnavigation.BottomNavigation
import kotlinx.android.synthetic.main.activity_bottom_navigation_frame.*
import javax.inject.Inject


class BottomNavigationFrameActivity :
    BaseViewModelActivity<ActivityBottomNavigationFrameBinding, BottomNavigationFrameVM>(),
    BottomNavigation.OnMenuItemSelectionListener, SnackbarCallBack {
    override fun onShown(snackbar: Snackbar) {
    }

    private lateinit var fragment: BaseViewModelFragment<*, *>

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<BottomNavigationFrameVM>

    @Inject
    lateinit var dataStoreManager: DataStoreManager

    override fun layoutRes() = R.layout.activity_bottom_navigation_frame
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bottomNavigation.menuItemSelectionListener = this
        val extras = intent
        if (extras == null) {
            finish()
            return
        }
        val fragmentName = extras.getStringExtra(Constants.FRAGMENT_CLASS);
        if (fragmentName == null || TextUtils.isEmpty(fragmentName)) {
            finish()
            return
        }

        fragment =
            navigatorHelper.instantiateFragment<Fragment>(
                this,
                fragmentName
            ) as BaseViewModelFragment<*, *>

        if (extras.hasExtra(EXTRA)) {
            navigatorHelper.createFragmentInstance(fragment, extras.getBundleExtra(EXTRA)!!)
        } else {
            navigatorHelper.createFragmentInstance(fragment, Bundle())
        }

        viewModel.get().toolbarTitle.postValue(
            setBoldFontTypeSpan(
                fragment.getToolBarTile(),
                R.font.mark_pro_light
            )
        )

        representativeFab.setOnClickListener {
            makeCall(getString(R.string.representative_number))
        }
    }

    override fun getHomeAsUpIndicator() = R.drawable.ic_back_arrow
    override fun getBindingVariable() = BR.bottomNavigationFrameVM
    override fun getMyViewModel() = viewModel.get()

    override fun toolbar() = true

    override fun onMenuItemSelect(itemId: Int, position: Int, fromUser: Boolean) {
        when (itemId) {
            R.id.home -> {
                finish()
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
        viewModel.get().toolbarTitle.postValue(
            setBoldFontTypeSpan(fragment.getToolBarTile(), R.font.mark_pro_light)
        )
    }

    override fun onMenuItemReselect(itemId: Int, position: Int, fromUser: Boolean) {

    }

    private fun navigateToLogin() {
        AlertUtils.showSnackBarLongMessage(
            bottomNavigation, "Please log in to continue", "Login", {
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cart, menu)

        val menuItem = menu.findItem(R.id.cart)

        val actionView = MenuItemCompat.getActionView(menuItem)

        val cartBadge = actionView.findViewById(R.id.cart_badge) as TextView
        cartItemsCount.observe(this, Observer<Int> { value ->
            if (value > 0) {
                cartBadge.visibility = View.VISIBLE
                cartBadge.text = (value.toString())
                runAnimation(cartBadge)
            } else
                cartBadge.visibility = View.GONE
        })

        actionView.setOnClickListener { onOptionsItemSelected(menuItem) }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        cartItemsCount.removeObservers(this)
    }

    private fun runAnimation(cartBadge: TextView) {

    }

    override fun onOptionsItemSelected(item: MenuItem?) =
        when (item?.itemId) {
            R.id.cart -> {
                if (cartItemsCount.value!! > 0) {
                    navigatorHelper.startFragmentWithToolbar<CartFragment>(
                        CartFragment::class.java.name
                    )
                } else {
                    toast("Cart is empty")
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}
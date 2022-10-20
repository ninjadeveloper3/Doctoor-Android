package com.Doctoor.app.navigation


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseViewModelFragment
import com.Doctoor.app.ui.modules.frame.BottomNavigationFrameActivity
import com.Doctoor.app.ui.modules.frame.FrameActivity
import com.Doctoor.app.ui.modules.login.LoginFragment
import com.Doctoor.app.ui.modules.main.MainActivity
import com.Doctoor.app.utils.Constants
import com.Doctoor.app.utils.Constants.EXTRA
import com.Doctoor.app.utils.Constants.FRAGMENT_CLASS
import com.Doctoor.app.utils.Constants.INDEX
import com.Doctoor.app.utils.Constants.SHOW_CART_MENU
import com.Doctoor.app.utils.Constants.SHOW_TOOLBAR


open class NavigatorHelper(private val mNavigator: Navigator) {

    //    public void navigateLoginActivity(boolean clearAllPrevious) {
    //        mNavigator.startActivity(LoginActivity.class, intent -> {
    //            if (clearAllPrevious) {
    //                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
    //            }
    //        });
    //        if (clearAllPrevious) {
    //            mNavigator.finishAffinity();
    //        }
    //    }
    //

    fun <T : BaseViewModelFragment<*, *>> replaceFragment(
        @IdRes containerId: Int,
        fragment: BaseViewModelFragment<*, *>
    ) =
        mNavigator.replaceFragment(containerId, fragment)


    fun <T : Fragment> instantiateFragment(context: Context, fragmentName: String) =
        Fragment.instantiate(context, fragmentName)

    fun fragmentInstantiate(context: Context, fragmentName: String) =
        Fragment.instantiate(context, fragmentName)

    fun <T : Fragment> startFragment(fragmentName: String, clearAllPrevious: Boolean) {
        mNavigator.startActivity(FrameActivity::class.java, object : PlainConsumer<Intent> {
            override fun accept(t: Intent) {
                t.putExtra(FRAGMENT_CLASS, fragmentName)
                t.putExtra(SHOW_TOOLBAR, false)
            }
        })
        if (clearAllPrevious) {
            mNavigator.finishAffinity()
        }
    }

    fun <T : Fragment> startFragment(
        fragmentName: String,
        clearAllPrevious: Boolean,
        bundle: Bundle
    ) {
        mNavigator.startActivity(FrameActivity::class.java, object : PlainConsumer<Intent> {
            override fun accept(t: Intent) {
                t.putExtra(FRAGMENT_CLASS, fragmentName)
                t.putExtra(SHOW_TOOLBAR, false)
                t.putExtra(EXTRA, bundle)
            }
        })
        if (clearAllPrevious) {
            mNavigator.finishAffinity()
        }
    }


    fun <T : Fragment> startFragmentWithToolbar(
        fragmentName: String,
        showCartMenu: Boolean = true, bundle: Bundle = Bundle(), clearAllPrevious: Boolean = false
    ) {
        mNavigator.startActivity(FrameActivity::class.java, object : PlainConsumer<Intent> {
            override fun accept(t: Intent) {
                t.putExtra(FRAGMENT_CLASS, fragmentName)
                t.putExtra(SHOW_CART_MENU, showCartMenu)
                t.putExtra(SHOW_TOOLBAR, true)
                t.putExtra(EXTRA, bundle)
            }
        })
        if (clearAllPrevious) {
            mNavigator.finishAffinity()
        }
    }

    fun <T : Fragment> startFragmentToolbarWithResult(
        fragmentName: String,
        showCartMenu: Boolean = true,
        bundle: Bundle = Bundle(),
        clearAllPrevious: Boolean = false,
        requestCode: Int
    ) {
        mNavigator.startActivityForResult(
            FrameActivity::class.java,
            object : PlainConsumer<Intent> {
                override fun accept(t: Intent) {
                    t.putExtra(FRAGMENT_CLASS, fragmentName)
                    t.putExtra(SHOW_CART_MENU, showCartMenu)
                    t.putExtra(SHOW_TOOLBAR, true)
                    t.putExtra(EXTRA, bundle)
                }
            }, requestCode
        )
        if (clearAllPrevious) {
            mNavigator.finishAffinity()
        }
    }

    fun <T : Fragment> startFragmentWithBottomNavigation(
        fragmentName: String,
        selectedIndex: Int = 0,
        showCartMenu: Boolean = true,
        bundle: Bundle = Bundle()
    ) {
        mNavigator.startActivity(
            BottomNavigationFrameActivity::class.java,
            object : PlainConsumer<Intent> {
                override fun accept(t: Intent) {
                    t.putExtra(FRAGMENT_CLASS, fragmentName)
                    t.putExtra(SHOW_TOOLBAR, true)
                    t.putExtra(SHOW_CART_MENU, showCartMenu)
                    t.putExtra(INDEX, selectedIndex)
                    t.putExtra(EXTRA, bundle)
                }
            })
    }

    fun <T : Fragment> createFragmentInstance(fragment: T, bundle: Bundle = Bundle()): T {
        fragment.arguments = bundle
        mNavigator.replaceFragment(R.id.container, fragment, bundle)
        return fragment
    }

    fun <T : Fragment> createFragmentInstance(fragment: T): T {
        mNavigator.replaceFragment(R.id.container, fragment);
        return fragment
    }


    fun navigateMainActivity(clearAllPrevious: Boolean) {
        mNavigator.startActivity(MainActivity::class.java)
        if (clearAllPrevious) {
            mNavigator.finishAffinity()
        }
    }

    fun navigateMainActivity(clearAllPrevious: Boolean, isCheckVersion: Boolean) {

        val bundle = Bundle()
        if (isCheckVersion)
            bundle.putBoolean(Constants.SHOW_ALERT, true)

        mNavigator.startActivity(MainActivity::class.java, bundle)

        if (clearAllPrevious) {
            mNavigator.finishAffinity()
        }
    }

    fun navigateMainActivity(
        fragmentName: String,
        selectedIndex: Int = 0,
        clearAllPrevious: Boolean
    ) {

        mNavigator.startActivity(MainActivity::class.java, object : PlainConsumer<Intent> {
            override fun accept(t: Intent) {
                t.putExtra(FRAGMENT_CLASS, fragmentName)
                t.putExtra(SHOW_TOOLBAR, true)
                t.putExtra(INDEX, selectedIndex)
                if (clearAllPrevious) {
                    t.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                }
            }
        })

        if (clearAllPrevious) {
            mNavigator.finishAffinity()
        }
    }

    fun startActivity(activityClass: Class<out Activity>, clearAllPrevious: Boolean) {
        mNavigator.startActivity(activityClass);
        if (clearAllPrevious) {
            mNavigator.finishAffinity()
        }
    }

    fun startActivity(activityClass: Class<out Activity>) {
        mNavigator.startActivity(activityClass);
    }

    fun navigateLoginFragment() {
        mNavigator.replaceFragment(R.id.container, LoginFragment.newInstance());
    }

    fun finishActivity() = mNavigator.finishActivity()
    fun finishAffinity() = mNavigator.finishAffinity()

}
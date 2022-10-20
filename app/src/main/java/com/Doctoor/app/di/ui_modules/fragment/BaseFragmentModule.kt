package com.Doctoor.app.di.ui_modules.fragment

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.Doctoor.app.base.BaseActivity
import com.Doctoor.app.base.BaseViewModelFragment
import com.Doctoor.app.di.qualifiers.ActivityContext
import com.Doctoor.app.di.qualifiers.ActivityFragmentManager
import com.Doctoor.app.di.qualifiers.ChildFragmentManager
import com.Doctoor.app.navigation.ChildFragmentNavigator
import com.Doctoor.app.navigation.FragmentNavigator
import com.Doctoor.app.navigation.FragmentNavigatorHelper
import com.Doctoor.app.navigation.NavigatorHelper
import dagger.Module
import dagger.Provides

/**
 * Module for fragment component, modified by Duy Pham (reference: Patrick Löwenstein)
 *
 *
 * NOTE: all method must be public (since children module might not in same package,
 * thus dagger can't generate inherit method
 */
@Module
abstract class BaseFragmentModule<in T : Fragment> {

    @Provides
    @ActivityContext
    fun provideContext(fragment: T): Context? {
        return fragment.context
    }

    @Provides
    @ChildFragmentManager
    fun provideChildFragmentManager(fragment: T): FragmentManager {
        return fragment.childFragmentManager
    }

    @Provides
    @ActivityFragmentManager
    fun provideActivityFragmentManager(activity: FragmentActivity): FragmentManager {
        return activity.supportFragmentManager
    }

    @Provides
    fun provideFragmentNavigator(fragment: T): FragmentNavigator {
        return ChildFragmentNavigator(fragment)
    }

    fun provideChildFragmentNavigator(fragment: T): ChildFragmentNavigator {
        return ChildFragmentNavigator(fragment)
    }

    @Provides
    fun provideBaseActivity(fragment: T): BaseActivity {
        return (fragment as BaseViewModelFragment<*, *>).getBaseActivity()
    }

    @Provides
    fun provideActivity(fragment: T): FragmentActivity? {
        return fragment.activity
    }

    @Provides
    fun provideLifeCycleOwner(fragment: T): LifecycleOwner {
        return fragment
    }

    @Provides
    fun fragmentNavigatorHelper(navigator: FragmentNavigator): FragmentNavigatorHelper {
        return FragmentNavigatorHelper(navigator)
    }

    @Provides
    fun navigatorHelper(navigator: FragmentNavigator): NavigatorHelper {
        return NavigatorHelper(navigator)
    }

}

package com.Doctoor.app.di.ui_modules.activity


import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.Doctoor.app.di.qualifiers.ActivityContext
import com.Doctoor.app.di.qualifiers.ActivityFragmentManager
import com.Doctoor.app.navigation.ActivityNavigator
import com.Doctoor.app.navigation.Navigator
import com.Doctoor.app.navigation.NavigatorHelper

import dagger.Module
import dagger.Provides

@Module
abstract class BaseActivityModule<in T : AppCompatActivity> {

    @Provides
    @ActivityContext
    fun provideContext(activity: T): Context {
        return activity
    }

    @Provides
    @ActivityContext
    fun provideActivity(activity: T): Activity {
        return activity
    }

    @Provides
    @ActivityFragmentManager
    fun provideFragmentManager(activity: T): FragmentManager {
        return activity.supportFragmentManager
    }

    @Provides
    fun provideNavigator(activity: T): Navigator {
        return ActivityNavigator(activity)
    }

    @Provides
    fun provideNavigatorHelper(navigator: Navigator): NavigatorHelper {
        return NavigatorHelper(navigator)
    }

    @Provides
    fun provideLifeCycleOwner(activity: T): LifecycleOwner {
        return activity
    }
}

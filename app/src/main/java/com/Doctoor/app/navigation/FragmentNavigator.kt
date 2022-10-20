package com.Doctoor.app.navigation

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment

interface FragmentNavigator : Navigator {

    fun replaceChildFragment(
        @IdRes containerId: Int,
        fragment: Fragment,
        vararg transitionViews: View
    )

    fun replaceChildFragment(
        @IdRes containerId: Int,
        fragment: Fragment,
        args: Bundle,
        vararg transitionViews: View
    )

    fun replaceChildFragment(
        @IdRes containerId: Int, fragment: Fragment,
        fragmentTag: String,
        args: Bundle,
        vararg transitionViews: View
    )

    fun replaceChildFragmentAndAddToBackStack(
        @IdRes containerId: Int, fragment: Fragment,
        args: Bundle,
        backstackTag: String,
        vararg transitionViews: View
    )

    fun replaceChildFragmentAndAddToBackStack(
        @IdRes containerId: Int, fragment: Fragment,
        fragmentTag: String,
        args: Bundle,
        backstackTag: String,
        vararg transitionViews: View
    )

    fun <T : Fragment> findChildFragmentByTag(tag: String): T?

    fun <T : Fragment> findChildFragmentById(@IdRes containerId: Int): T?
}
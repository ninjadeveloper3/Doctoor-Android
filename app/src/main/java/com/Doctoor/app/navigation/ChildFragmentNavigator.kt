package com.Doctoor.app.navigation

import android.os.Bundle

import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment


class ChildFragmentNavigator(private val fragment: Fragment) :
    ActivityNavigator(fragment.activity!!), FragmentNavigator {

    override fun replaceChildFragment(
        @IdRes containerId: Int,
        fragment: Fragment,
        vararg transitionViews: View
    ) {
        replaceFragmentInternal(
            this.fragment.childFragmentManager,
            containerId,
            fragment,
            null,
            null,
            false,
            null
        )
    }

    override fun replaceChildFragment(
        @IdRes containerId: Int, fragment: Fragment,
        args: Bundle,
        vararg transitionViews: View
    ) {
        replaceFragmentInternal(
            this.fragment.childFragmentManager,
            containerId,
            fragment,
            null,
            args,
            false,
            null
        )
    }

    override fun replaceChildFragment(
        @IdRes containerId: Int, fragment: Fragment,
        fragmentTag: String,
        args: Bundle,
        vararg transitionViews: View
    ) {
        replaceFragmentInternal(
            this.fragment.childFragmentManager,
            containerId,
            fragment,
            fragmentTag,
            args,
            false,
            null
        )
    }

    override fun replaceChildFragmentAndAddToBackStack(
        @IdRes containerId: Int, fragment: Fragment,
        args: Bundle,
        backstackTag: String,
        vararg transitionViews: View
    ) {
        replaceFragmentInternal(
            this.fragment.childFragmentManager,
            containerId,
            fragment,
            null,
            args,
            true,
            backstackTag
        )
    }

    override fun replaceChildFragmentAndAddToBackStack(
        @IdRes containerId: Int, fragment: Fragment,
        fragmentTag: String,
        args: Bundle,
        backstackTag: String,
        vararg transitionViews: View
    ) {
        replaceFragmentInternal(
            this.fragment.childFragmentManager,
            containerId,
            fragment,
            fragmentTag,
            args,
            true,
            backstackTag
        )
    }

    override fun <T : Fragment> findChildFragmentByTag(tag: String): T? {

        return this.fragment.childFragmentManager.findFragmentByTag(tag) as T?
    }

    override fun <T : Fragment> findChildFragmentById(@IdRes containerId: Int): T? {

        return this.fragment.childFragmentManager.findFragmentById(containerId) as T?
    }
}

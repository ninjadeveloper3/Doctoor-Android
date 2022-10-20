package com.Doctoor.app.navigation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable

import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment

interface Navigator {

    fun finishActivity()
    fun finishAffinity()
    fun startActivity(intent: Intent)
    fun startActivity(action: String)
    fun startActivity(action: String, uri: Uri)
    fun startActivity(activityClass: Class<out Activity>)
    fun startActivity(activityClass: Class<out Activity>, setArgsAction: PlainConsumer<Intent>)
    fun startActivity(activityClass: Class<out Activity>, args: Bundle)
    fun startActivity(activityClass: Class<out Activity>, args: Parcelable)
    fun startActivity(activityClass: Class<out Activity>, arg: String)
    fun startActivity(activityClass: Class<out Activity>, arg: Int)
    fun startActivityWithTransition(
        activityClass: Class<out Activity>,
        setArgsAction: PlainConsumer<Intent>,
        includeNestedChildren: Boolean, includeStatusBar: Boolean, vararg views: View
    )

    fun startActivityForResult(activityClass: Class<out Activity>, requestCode: Int)
    fun startActivityForResult(
        activityClass: Class<out Activity>,
        setArgsAction: PlainConsumer<Intent>,
        requestCode: Int
    )

    fun startActivityForResult(
        activityClass: Class<out Activity>,
        arg: Parcelable,
        requestCode: Int
    )

    fun startActivityForResult(activityClass: Class<out Activity>, arg: String, requestCode: Int)
    fun startActivityForResult(activityClass: Class<out Activity>, arg: Int, requestCode: Int)

    fun replaceFragment(@IdRes containerId: Int, fragment: Fragment, vararg transitionViews: View)
    fun replaceFragment(
        @IdRes containerId: Int,
        fragment: Fragment,
        args: Bundle,
        vararg transitionViews: View
    )

    fun replaceFragment(
        @IdRes containerId: Int, fragment: Fragment,
        fragmentTag: String,
        args: Bundle,
        vararg transitionViews: View
    )

    fun replaceFragmentAndAddToBackStack(
        @IdRes containerId: Int, fragment: Fragment,
        args: Bundle,
        backstackTag: String,
        vararg transitionViews: View
    )

    fun replaceFragmentAndAddToBackStack(
        @IdRes containerId: Int, fragment: Fragment,
        fragmentTag: String,
        args: Bundle,
        backstackTag: String,
        vararg transitionViews: View
    )

    fun <T : Fragment> findFragmentByTag(tag: String): T?

    fun <T : Fragment> findFragmentById(@IdRes containerId: Int): T?

    companion object {
        val EXTRA_ARG = "_args"
    }
}

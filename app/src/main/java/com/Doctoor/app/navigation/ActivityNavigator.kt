package com.Doctoor.app.navigation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.transition.Fade
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

open class ActivityNavigator(protected val activity: FragmentActivity) : Navigator {
    override fun finishAffinity() {
        activity.finishAffinity()
    }

    private val EXTRA_ARG = "_args"

    override fun finishActivity() {
        activity.finish()
    }

    override fun startActivity(intent: Intent) {
        activity.startActivity(intent)
    }

    override fun startActivity(action: String) {
        activity.startActivity(Intent(action))
    }

    override fun startActivity(action: String, uri: Uri) {
        activity.startActivity(Intent(action, uri))
    }

    override fun startActivity(activityClass: Class<out Activity>) {
        startActivityInternal(activityClass, null, null)
    }

    override fun startActivity(
        activityClass: Class<out Activity>,
        setArgsAction: PlainConsumer<Intent>
    ) {
        startActivityInternal(activityClass, setArgsAction, null)
    }

    override fun startActivity(activityClass: Class<out Activity>, args: Bundle) {

        startActivityInternal(activityClass, object : PlainConsumer<Intent> {
            override fun accept(t: Intent) {
                t.putExtra(EXTRA_ARG, args)
            }
        }, null)
    }

    override fun startActivity(activityClass: Class<out Activity>, args: Parcelable) {
        startActivityInternal(activityClass, object : PlainConsumer<Intent> {
            override fun accept(t: Intent) {
                t.putExtra(EXTRA_ARG, args)
            }
        }, null)
    }

    override fun startActivity(activityClass: Class<out Activity>, arg: String) {
        startActivityInternal(activityClass, object : PlainConsumer<Intent> {
            override fun accept(intent: Intent) {
                intent.putExtra(EXTRA_ARG, arg)
            }
        }, null)
    }

    override fun startActivity(activityClass: Class<out Activity>, arg: Int) {
        startActivityInternal(activityClass, object : PlainConsumer<Intent> {
            override fun accept(t: Intent) {
                t.putExtra(EXTRA_ARG, arg)
            }
        }, null)
    }

    override fun startActivityForResult(activityClass: Class<out Activity>, requestCode: Int) {
        startActivityInternal(activityClass, null, requestCode)
    }

    override fun startActivityForResult(
        activityClass: Class<out Activity>,
        setArgsAction: PlainConsumer<Intent>,
        requestCode: Int
    ) {
        startActivityInternal(activityClass, setArgsAction, requestCode)
    }

    override fun startActivityForResult(
        activityClass: Class<out Activity>,
        arg: Parcelable,
        requestCode: Int
    ) {
        startActivityInternal(activityClass, object : PlainConsumer<Intent> {
            override fun accept(t: Intent) {
                t.putExtra(EXTRA_ARG, arg)
            }
        }, requestCode)

    }

    override fun startActivityForResult(
        activityClass: Class<out Activity>,
        arg: String,
        requestCode: Int
    ) {
        startActivityInternal(activityClass, object : PlainConsumer<Intent> {
            override fun accept(t: Intent) {
                t.putExtra(EXTRA_ARG, arg)
            }
        }, requestCode)
    }

    override fun startActivityForResult(
        activityClass: Class<out Activity>,
        arg: Int,
        requestCode: Int
    ) {
        startActivityInternal(activityClass, object : PlainConsumer<Intent> {
            override fun accept(t: Intent) {
                t.putExtra(EXTRA_ARG, arg)
            }
        }, requestCode)
    }

    private fun startActivityInternal(
        activityClass: Class<out Activity>,
        setArgsAction: PlainConsumer<Intent>?,
        requestCode: Int?
    ) {
        val intent = Intent(activity, activityClass)
        setArgsAction?.accept(intent)

        if (requestCode != null) {
            activity.startActivityForResult(intent, requestCode)
        } else {
            activity.startActivity(intent)
        }
    }

    override fun replaceFragment(
        @IdRes containerId: Int,
        fragment: Fragment,
        vararg transitionViews: View
    ) {
        replaceFragmentInternal(
            activity.supportFragmentManager,
            containerId,
            fragment,
            null,
            null,
            false,
            null,
            *transitionViews
        )
    }

    override fun replaceFragment(
        @IdRes containerId: Int, fragment: Fragment,
        args: Bundle,
        vararg transitionViews: View
    ) {
        replaceFragmentInternal(
            activity.supportFragmentManager,
            containerId,
            fragment,
            null,
            args,
            false,
            null,
            *transitionViews
        )
    }

    override fun replaceFragment(
        @IdRes containerId: Int, fragment: Fragment,
        fragmentTag: String,
        args: Bundle,
        vararg transitionViews: View
    ) {
        replaceFragmentInternal(
            activity.supportFragmentManager,
            containerId,
            fragment,
            fragmentTag,
            args,
            false,
            null,
            *transitionViews
        )
    }

    override fun replaceFragmentAndAddToBackStack(
        @IdRes containerId: Int, fragment: Fragment,
        args: Bundle,
        backstackTag: String,
        vararg transitionViews: View
    ) {
        replaceFragmentInternal(
            activity.supportFragmentManager,
            containerId,
            fragment,
            null,
            args,
            true,
            backstackTag,
            *transitionViews
        )
    }

    override fun replaceFragmentAndAddToBackStack(
        @IdRes containerId: Int, fragment: Fragment,
        fragmentTag: String,
        args: Bundle,
        backstackTag: String,
        vararg transitionViews: View
    ) {
        replaceFragmentInternal(
            activity.supportFragmentManager,
            containerId,
            fragment,
            fragmentTag,
            args,
            true,
            backstackTag,
            *transitionViews
        )
    }

    override fun <T : Fragment> findFragmentByTag(tag: String): T? {

        return activity.supportFragmentManager.findFragmentByTag(tag) as T?
    }

    override fun <T : Fragment> findFragmentById(@IdRes containerId: Int): T? {

        return activity.supportFragmentManager.findFragmentById(containerId) as T?
    }

    protected fun replaceFragmentInternal(
        fm: FragmentManager, @IdRes containerId: Int,
        fragment: Fragment,
        fragmentTag: String?,
        args: Bundle?,
        addToBackstack: Boolean,
        backstackTag: String?,
        vararg transitionViews: View
    ) {
        if (args != null) {
            fragment.arguments = args
        }
        val ft = fm.beginTransaction()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (transitionViews.isNotEmpty()) {
                fragment.sharedElementEnterTransition = DetailFragmentTransition()
                fragment.enterTransition = Fade()
                for (view in transitionViews) {
                    if (view.transitionName != null) {
                        ft.addSharedElement(view, view.transitionName)
                    }
                }
            }
        }
        ft.replace(containerId, fragment, fragmentTag)
        if (addToBackstack) {
            ft.addToBackStack(backstackTag).commit()
            fm.executePendingTransactions()
        } else {
            ft.commitNow()
        }
    }

    override fun startActivityWithTransition(
        activityClass: Class<out Activity>,
        setArgsAction: PlainConsumer<Intent>,
        includeNestedChildren: Boolean,
        includeStatusBar: Boolean,
        vararg views: View
    ) {

    }
}

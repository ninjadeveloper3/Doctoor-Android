package com.Doctoor.app.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager


class AutoClearedValue<T>(fragment: Fragment, private var value: T?) {

    init {
        val fragmentManager = fragment.fragmentManager
        fragmentManager?.registerFragmentLifecycleCallbacks(
            object : FragmentManager.FragmentLifecycleCallbacks() {
                override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
                    this@AutoClearedValue.value = null
                    fragmentManager.unregisterFragmentLifecycleCallbacks(this)
                }
            }, false
        )
    }

    fun get(): T? {
        return value
    }
}
package com.Doctoor.app.ui.lazy

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes

fun <ViewT : View> Activity.bindView(@IdRes idRes: Int): Lazy<ViewT> {
    return lazyUnsychronized {
        findViewById<ViewT>(idRes)
    }


}

fun <T : View> Activity.bind(@IdRes res: Int): T {
    @Suppress("UNCHECKED_CAST")
    return findViewById<T>(res)
}

fun <T : View> View.bind(@IdRes idRes: Int): Lazy<T> {
    @Suppress("UNCHECKED_CAST")
    return unsafeLazy { findViewById<T>(idRes) }
}

private fun <T> unsafeLazy(initializer: () -> T) = lazy(LazyThreadSafetyMode.NONE, initializer)
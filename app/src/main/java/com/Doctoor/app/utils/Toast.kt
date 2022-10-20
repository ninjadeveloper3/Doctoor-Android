package com.Doctoor.app.utils

import android.content.Context
import android.widget.Toast
import com.Doctoor.app.base.ProgressDialogFragment
import com.Doctoor.app.ui.modules.frame.FrameActivity
import es.dmoral.toasty.Toasty

fun Context?.toast(msg: String, duration: Int = Toast.LENGTH_LONG) = makeToast(this, msg, duration)

fun Context?.shortToast(msg: String) = makeToast(this, msg, Toast.LENGTH_SHORT)

fun Context?.longToast(msg: String) = makeToast(this, msg, Toast.LENGTH_LONG)

fun Context?.toastNow(msg: String, duration: Int = Toast.LENGTH_LONG) =
    cancelAndMakeToast(this, msg, duration)

fun Context?.shortToastNow(msg: String) = cancelAndMakeToast(this, msg, Toast.LENGTH_SHORT)

fun Context?.longToastNow(msg: String) = cancelAndMakeToast(this, msg, Toast.LENGTH_LONG)

fun cancelAllToasts() = ToastQueue.cancelToasts()

fun cancelAndMakeToast(ctx: Context?, msg: String, duration: Int): Toast? {
    ToastQueue.cancelToasts()
    return makeToast(ctx, msg, duration)
}

fun makeToast(ctx: Context?, msg: String, duration: Int): Toast? {


    return ctx?.let {
        val toast = Toasty.normal(ctx, msg, duration)
        toast.show()
        // remove from list after 4 seconds (longest toast time is 3.5 seconds)
        toast.view?.postDelayed({
            ToastQueue.removeToast(toast)
        }, 4000L)
        ToastQueue.toastQueue.add(toast)
        toast
    }
}

fun FrameActivity.show() {
    ProgressDialogFragment.show(this)
}

fun FrameActivity.show(title: String = "", cancelable: Boolean = false) {
    ProgressDialogFragment.show(this, title, cancelable = cancelable)
}

private object ToastQueue {
    val toastQueue = mutableListOf<Toast>()

    fun cancelToasts() {
        toastQueue.forEach { it.cancel() }
        toastQueue.clear()
    }

    fun removeToast(toast: Toast) = toastQueue.remove(toast)

}
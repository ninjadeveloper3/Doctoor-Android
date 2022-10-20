package com.Doctoor.app.utils

/**
 * This file contains the various extension functions from core library that requires context
 * That should also be called from the fragment directly without explicitly calling on context
 */

import android.widget.Toast
import androidx.fragment.app.Fragment


/* Functions for Conversions */

fun Fragment.pxToDp(px: Float) = context?.pxToDp(px)
fun Fragment.dpToPx(dp: Float) = context?.dpToPx(dp)
fun Fragment.spToPx(sp: Float) = context?.spToPx(sp)
fun Fragment.pxToSp(px: Float) = context?.pxToSp(px)
fun Fragment.dimen(resource: Int) = context?.dimen(resource)

/* Functions for toast */

fun Fragment?.toast(msg: String) = makeToast(this?.context, msg, Toast.LENGTH_LONG)
fun Fragment?.shortToast(msg: String) = makeToast(this?.context, msg, Toast.LENGTH_SHORT)
fun Fragment?.longToast(msg: String) = makeToast(this?.context, msg, Toast.LENGTH_LONG)
fun Fragment?.toastNow(msg: String) = cancelAndMakeToast(this?.context, msg, Toast.LENGTH_LONG)
fun Fragment?.shortToastNow(msg: String) =
    cancelAndMakeToast(this?.context, msg, Toast.LENGTH_SHORT)

fun Fragment?.longToastNow(msg: String) = cancelAndMakeToast(this?.context, msg, Toast.LENGTH_LONG)


/* Functions for Keyboard */

fun Fragment.hideKeyboard() = activity?.hideKeyboard()
fun Fragment.showKeyboard() = activity?.showKeyboard()

/* Functions for Network */

fun Fragment.isNetworkAvailable() = context?.isNetworkAvailable()

/* Functions for Share */

fun Fragment.openUrl(url: String, newTask: Boolean = false) = context?.openUrl(url, newTask)
fun Fragment.share(text: String, subject: String = "") = context?.share(text, subject)
fun Fragment.sendEmail(email: String, subject: String = "", text: String = "") =
    context?.sendEmail(email, subject, text)

fun Fragment.makeCall(number: String) = context?.makeCall(number)
fun Fragment.sendSms(number: String, text: String = "") = context?.sendSms(number, text)
fun Fragment.openPlayStore() = context?.openPlayStore()


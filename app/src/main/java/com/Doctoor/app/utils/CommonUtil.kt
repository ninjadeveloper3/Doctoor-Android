package com.Doctoor.app.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.text.TextUtils
import androidx.annotation.NonNull


fun getLongValue(value: String): Long {
    if (!TextUtils.isEmpty(value)) {
        try {
            return java.lang.Long.parseLong(value)
        } catch (e: NumberFormatException) {
        }

    }
    return 0
}

fun getIntValue(value: String): Int {
    if (!TextUtils.isEmpty(value)) {
        try {
            return Integer.parseInt(value)
        } catch (e: NumberFormatException) {

        }
    }
    return 0
}

fun getActivityFromContext(@NonNull context: Context): Activity? {
    var context = context
    while (context is ContextWrapper) {
        if (context is Activity) {
            return context
        }
        context = (context as ContextWrapper).baseContext
    }
    return null
}


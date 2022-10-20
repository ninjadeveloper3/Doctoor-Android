package com.Doctoor.app.utils

import android.content.Context
import android.content.res.Resources
import android.text.TextUtils
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import com.google.android.material.textfield.TextInputLayout

/**
 * Checks if a string is a valid email
 * @return a boolean representing true if email is valid else false
 */
fun String.isEmail() = android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

/**
 * Checks if String is Number.
 * Checks against regex `^[0-9]+$`
 * @return a boolean representing true if all the characters are numeric else false
 */
fun String.isNumeric(): Boolean {
    val p = "^[0-9]+$".toRegex()
    return matches(p)
}

fun Context?.string(@StringRes idRes: Int): String = Resources.getSystem().getString(idRes)

fun isWhiteSpaces(@Nullable s: String?) = s != null && s.matches("\\s+".toRegex())

fun isEmpty(@Nullable text: String?) =
    text == null || TextUtils.isEmpty(text) || isWhiteSpaces(text) || text.equals(null)

fun isEmpty(@Nullable text: Any?) = text == null || isEmpty(text.toString())

fun isEmpty(@Nullable text: EditText?) = text == null || isEmpty(text.text.toString())

fun isEmpty(@Nullable text: TextView?) = text == null || isEmpty(text.text.toString())

fun isEmpty(@Nullable txt: TextInputLayout?) = txt == null || isEmpty(txt!!.getEditText())

fun toString(@NonNull editText: EditText) = editText.text.toString()
package com.Doctoor.app.imagepicker

internal interface ResultListener<T> {

    fun onResult(t: T?)
}
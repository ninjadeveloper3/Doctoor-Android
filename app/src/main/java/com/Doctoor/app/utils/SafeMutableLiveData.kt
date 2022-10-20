package com.Doctoor.app.utils

import androidx.lifecycle.LiveData

class SafeMutableLiveData<T> : LiveData<T>() {

    public override fun setValue(value: T) {
        try {
            super.setValue(value)
        } catch (e: Exception) {
            super.postValue(value)
        }

    }

}
package com.Doctoor.app.base;

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.Doctoor.app.model.response.City
import com.Doctoor.app.preference.DataStoreManager

abstract class OrderBaseModal constructor(private val dataStoreManager: DataStoreManager) :
    BaseViewModel() {

    override fun onFirsTimeUiCreate(bundle: Bundle?) {
        prepareCityItems()
    }

    abstract fun prepareCityItems()

    var phone = MutableLiveData<String>()
        set(value) {
            notifyChange()
            field = value
        }
    var email = MutableLiveData<String>()
        set(value) {
            notifyChange()
            field = value
        }

    var address = MutableLiveData<String>()
        set(value) {
            notifyChange()
            field = value
        }

    var firstName = MutableLiveData<String>()
        set(value) {
            notifyChange()
            field = value
        }
    var lastName = MutableLiveData<String>()
        set(value) {
            notifyChange()
            field = value
        }
    var cityName = MutableLiveData<String>()
        set(value) {
            notifyChange()
            field = value
        }

    var province = MutableLiveData<String>()
        set(value) {
            notifyChange()
            field = value
        }
    val provincePosition = MutableLiveData<Int>().apply { value = 0 }

    var cityPosition = MutableLiveData<Int>().apply { value = -1 }

    var isProvinceError = MutableLiveData<Boolean>().apply { setValue(false) }

    var isCityError = MutableLiveData<Boolean>().apply { setValue(false) }

    var isSameAddress = MutableLiveData<Boolean>().apply { value = false }

    var cities = ArrayList<City>()

    var cityNames: MutableList<String> = ArrayList()

    var provinces: MutableList<String> = ArrayList()

    var provinceNames: MutableList<String> = ArrayList()

    fun getIsProvinceError(): LiveData<Boolean> {
        return this.isProvinceError
    }

    fun getIsCityError(): LiveData<Boolean> {
        return this.isCityError
    }

    fun getisSameAddress(): LiveData<Boolean> {
        return this.isSameAddress
    }

    fun onProvinceSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {

        if (pos >= 0) {
            provincePosition.value = parent.selectedItemPosition
            province.value = parent.selectedItem.toString()

        } else {
            provincePosition.value = 0
        }
    }

    fun onSelectCityItem(parent: AdapterView<*>, view: View, pos: Int, id: Long) {

        if (pos >= 0) {
            cityPosition.value = parent.selectedItemPosition
            cityName.value = parent.selectedItem.toString()
        } else {
            cityPosition.value = -1

        }
    }

    fun onCheckClick() {
        if (isSameAddress.value == true) {
            isSameAddress.postValue(false)
        } else {
            isSameAddress.postValue(true)
        }
    }
}



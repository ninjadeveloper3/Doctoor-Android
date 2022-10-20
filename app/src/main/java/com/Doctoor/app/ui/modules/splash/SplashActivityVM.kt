package com.Doctoor.app.ui.modules.splash

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.Doctoor.app.base.BaseViewModel
import com.Doctoor.app.data.remote.MedicineRestService
import com.Doctoor.app.data.remote.ServicesRestService
import com.Doctoor.app.model.response.City
import com.Doctoor.app.model.response.MedicalServices
import com.Doctoor.app.model.response.Medicines
import com.Doctoor.app.preference.DataStoreManager
import com.Doctoor.app.preference.UserDataStore
import com.Doctoor.app.rx.functions.PlainConsumer
import javax.inject.Inject

class SplashActivityVM @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val apiServices: MedicineRestService,
    private val dataStore: UserDataStore,
    private val service: ServicesRestService
) : BaseViewModel() {

    var isFirstLunchFinished = MutableLiveData<Boolean>()

    override fun onFirsTimeUiCreate(bundle: Bundle?) {
        isFirstLunchFinished.value = dataStoreManager.dataStore.isFirstLunchFinish()
        fetchCities()
        if (dataStoreManager.isUserSessionStarted) {
            fetchUserProfile()
        }
    }

    fun fetchServicesList() {
        execute(false, service.medicalServicesList(), PlainConsumer { t ->
            dataStoreManager.dataStore.saveAsList(t.data, MedicalServices.Service::class.java.name)
        })
    }

    private fun fetchUserProfile() {
        execute(false, dataStoreManager.apiService?.getProfile()!!, PlainConsumer { response ->

            when (response.responseHeader?.responseCode == 200) {
                true -> {
                    dataStore.setUser(response.user)
                }
                false -> {
                    dataStoreManager.reset()
                }
            }
        })

    }

    private fun fetchMedicineCategories() {
        execute(false, apiServices.medicinesCategories(page = -1), PlainConsumer { t ->
            dataStoreManager.dataStore.saveAsList(t.data, Medicines.Category::class.java.name)
            if (!isFirstLunchFinished.value!!) {
                isFirstLunchFinished.value = true
            }
            dataStoreManager.dataStore.setFirstTimeLunch(true)
        }, errorConsumer = PlainConsumer {
            isFirstLunchFinished.value = false

        })

    }

    private fun fetchCities() {
        execute(false, dataStoreManager.apiService?.getCities()!!, PlainConsumer { t ->
            dataStoreManager.dataStore.saveAsList(t.data, City::class.java.name)
            fetchMedicineCategories()
//            fetchServicesList()
        })

    }

}
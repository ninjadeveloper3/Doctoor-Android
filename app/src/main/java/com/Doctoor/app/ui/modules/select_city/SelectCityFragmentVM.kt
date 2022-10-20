package com.Doctoor.app.ui.modules.select_city

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.Doctoor.app.base.BaseViewModel
import com.Doctoor.app.model.response.City
import com.Doctoor.app.preference.DataStoreManager
import com.Doctoor.app.ui.modules.service_type.ServiceTypeFragment
import com.Doctoor.app.utils.Constants.ID
import com.Doctoor.app.utils.debug
import com.Doctoor.app.utils.validation.Validator
import javax.inject.Inject

class SelectCityFragmentVM @Inject constructor(private val dataStoreManager: DataStoreManager) :
    BaseViewModel() {
    internal var validator: Validator? = null

    var cities = ArrayList<City>()

    val cityNames = ArrayList<String>()
    var cityId = 0

    override fun onFirsTimeUiCreate(bundle: Bundle?) {
        prepareCityItems()
    }

    private fun prepareCityItems() {

        cities = dataStoreManager.dataStore.getCities(City::class.java.name)
        
        dataStoreManager.dataStore.getCities(City::class.java.name).forEach { city ->
            cityNames.add(city.cityName)
        }
    }

    fun onSubmit() {
        if (validator?.validate()!!) {
            if (cityId == 0) {
                return
            }
            val bundle = Bundle()
            bundle.putInt(ID, cityId)
            navigatorHelper?.startFragmentWithBottomNavigation<ServiceTypeFragment>(
                ServiceTypeFragment::class.java.name,
                bundle = bundle
            )
        }
    }

    fun onSelectCityItem(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        debug("position $pos")

        cityId = cities[pos].id
    }
}
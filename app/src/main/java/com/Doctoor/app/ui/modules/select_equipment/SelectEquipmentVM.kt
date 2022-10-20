package com.Doctoor.app.ui.modules.select_equipment

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.Doctoor.app.base.BaseRecyclerAdapterVM
import com.Doctoor.app.data.remote.ServicesRestService
import com.Doctoor.app.model.response.Equipments
import com.Doctoor.app.rx.functions.PlainConsumer
import com.Doctoor.app.ui.interfaces.OnCallApiDone
import com.Doctoor.app.utils.Constants
import javax.inject.Inject

class SelectEquipmentVM @Inject constructor(private val apiServices: ServicesRestService) :
    BaseRecyclerAdapterVM<Equipments.Equipment>() {
    var cityId = 0
    var query = ""
    var searchQuery = MutableLiveData<String>()
    var keyboardState = MutableLiveData<Boolean>()
    override fun onFirsTimeUiCreate(bundle: Bundle?) {
        super.onFirsTimeUiCreate(bundle)
        cityId = bundle?.getInt(Constants.ID, 0)!!
    }

    override fun callApi(page: Int, onCallApiDone: OnCallApiDone<Equipments.Equipment>) {
        getEquipments(page)
    }

    fun getEquipments(page: Int = 1) {
        if (query.isEmpty()) {
            execute(true, apiServices.medicalEquipments(page = page),
                PlainConsumer { t ->
                    if (t.data is ArrayList)
                    /*if page is 1 it means the data is loading first time or it's loading from pull to refresh*/
                        setData(t.data, page == 1)
                }
            )
        } else {
            search(this.query)
        }
    }

    private fun search(query: String?) {
        if (query?.isNotEmpty()!!) {
            execute(true,
                apiServices.searchEquipments(equipmentName = query.toString(), cityId = cityId),
                PlainConsumer { t ->
                    if (t.data is ArrayList)
                    /*if page is 1 it means the data is loading first time or it's loading from pull to refresh*/
                        setData(t.data, true)
                }
            )
        } else {
            getEquipments()
        }
    }

    fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        setData(ArrayList())
        query = s.toString()
        searchQuery.value = s.toString()
        search(s.toString())
    }

    fun onSearchSubmit() {
        keyboardState.postValue(true)
        search(this.query)
    }

    fun clearSearch() {
        this.query = ""
        searchQuery.value = ""
        clear()
        getEquipments()
    }

}
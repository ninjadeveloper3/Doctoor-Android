package com.Doctoor.app.ui.modules.select_equipment.popular_equipments

import com.Doctoor.app.base.BaseRecyclerAdapterVM
import com.Doctoor.app.data.remote.ServicesRestService
import com.Doctoor.app.model.response.Equipments
import com.Doctoor.app.rx.functions.PlainConsumer
import com.Doctoor.app.ui.interfaces.OnCallApiDone
import javax.inject.Inject

class PopularEquipmentsVM @Inject constructor(private val apiServices: ServicesRestService) :
    BaseRecyclerAdapterVM<Equipments.Equipment>() {

    override fun callApi(page: Int, onCallApiDone: OnCallApiDone<Equipments.Equipment>) {
        getEquipments(page)
    }

    fun getEquipments(page: Int = 1) {
        execute(true, apiServices.medicalEquipmentsInDemand(page = page),
            PlainConsumer { t ->
                if (t.data is ArrayList)
                /*if page is 1 it means the data is loading first time or it's loading from pull to refresh*/
                    setData(t.data, page == 1)
            }
        )
    }
}
package com.Doctoor.app.ui.modules.myorder.rental_history

import com.Doctoor.app.base.BaseRecyclerAdapterVM
import com.Doctoor.app.data.remote.ServicesRestService
import com.Doctoor.app.model.response.MedicalServices
import com.Doctoor.app.rx.functions.PlainConsumer
import com.Doctoor.app.ui.interfaces.OnCallApiDone
import javax.inject.Inject

class RentalEquipmentsHistoryVM @Inject constructor(private val apiServices: ServicesRestService) :
    BaseRecyclerAdapterVM<MedicalServices.RentalHistory>() {

    override fun callApi(page: Int, onCallApiDone: OnCallApiDone<MedicalServices.RentalHistory>) {

        execute(true, apiServices.rentalHistory(page = page),
            PlainConsumer { t ->
                if (t.data is ArrayList)
                /*if page is 1 it means the data is loading first time or it's loading from pull to refresh*/
                    setData(t.data, page == 1)
            }
        )
    }
}
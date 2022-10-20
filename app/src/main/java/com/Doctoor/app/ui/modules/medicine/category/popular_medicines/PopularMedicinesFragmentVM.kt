package com.Doctoor.app.ui.modules.medicine.category.popular_medicines

import com.Doctoor.app.base.BaseRecyclerAdapterVM
import com.Doctoor.app.data.remote.MedicineRestService
import com.Doctoor.app.model.response.Medicines
import com.Doctoor.app.rx.functions.PlainConsumer
import com.Doctoor.app.ui.interfaces.OnCallApiDone
import javax.inject.Inject

class PopularMedicinesFragmentVM @Inject constructor(private val apiServices: MedicineRestService) :
    BaseRecyclerAdapterVM<Medicines.Category>() {

    override fun callApi(page: Int, onCallApiDone: OnCallApiDone<Medicines.Category>) {

        execute(true, apiServices.medicinesPopularCategories(),
            PlainConsumer { t ->
                if (t.data is ArrayList)
                /*if page is 1 it means the data is loading first time or it's loading from pull to refresh*/
                    setData(t.data, true)
            }
        )
    }

}
package com.Doctoor.app.ui.modules.popular_services

import com.Doctoor.app.base.BaseRecyclerAdapterVM
import com.Doctoor.app.data.remote.TestsRestService
import com.Doctoor.app.model.response.Tests
import com.Doctoor.app.rx.functions.PlainConsumer
import com.Doctoor.app.ui.interfaces.OnCallApiDone
import javax.inject.Inject

class PopularServicesVM @Inject constructor(private val apiServices: TestsRestService) :
    BaseRecyclerAdapterVM<Tests.PopularService>() {

    override fun callApi(page: Int, onCallApiDone: OnCallApiDone<Tests.PopularService>) {
        execute(true, apiServices.homeMedicalServicesList(page = page),
            PlainConsumer { t ->
                if (t.data is ArrayList)
                /*if page is 1 it means the data is loading first time or it's loading from pull to refresh*/
                    setData(t.data, true)
            }
        )
    }
}
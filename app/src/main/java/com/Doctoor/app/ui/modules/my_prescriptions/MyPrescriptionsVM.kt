package com.Doctoor.app.ui.modules.my_prescriptions

import android.app.Application
import com.Doctoor.app.base.BaseRecyclerAdapterVM
import javax.inject.Inject
import com.Doctoor.app.base.BaseViewModel
import com.Doctoor.app.data.remote.TestsRestService
import com.Doctoor.app.model.response.Tests
import com.Doctoor.app.rx.functions.PlainConsumer
import com.Doctoor.app.ui.interfaces.OnCallApiDone

class MyPrescriptionsVM @Inject constructor(private val apiServices: TestsRestService) :
    BaseRecyclerAdapterVM<Tests.MyPrescription>() {
    override fun callApi(page: Int, onCallApiDone: OnCallApiDone<Tests.MyPrescription>) {
        execute(true, apiServices.myPrescriptions(),
            PlainConsumer { t ->
                if (t.data is ArrayList)
                /*if page is 1 it means the data is loading first time or it's loading from pull to refresh*/
                    setData(t.data, true)
            }
        )
    }
}
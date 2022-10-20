package com.Doctoor.app.ui.modules.lab_reports

import com.Doctoor.app.base.BaseRecyclerAdapterVM
import com.Doctoor.app.data.remote.TestsRestService
import com.Doctoor.app.model.response.Tests
import com.Doctoor.app.rx.functions.PlainConsumer
import com.Doctoor.app.ui.interfaces.OnCallApiDone
import javax.inject.Inject

class LabReportsFragmentVM @Inject constructor(private val apiServices: TestsRestService) : BaseRecyclerAdapterVM<Tests.LabReport>() {
    override fun callApi(page: Int, onCallApiDone: OnCallApiDone<Tests.LabReport>) {
        execute(true, apiServices.labReports(page = page),
            PlainConsumer { t ->
                if (t.data is ArrayList)
                /*if page is 1 it means the data is loading first time or it's loading from pull to refresh*/
                    setData(t.data, page == 1)
            }
        )
    }
}
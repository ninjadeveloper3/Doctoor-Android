package com.Doctoor.app.ui.modules.select_lab.test.popular_tests

import com.Doctoor.app.base.BaseRecyclerAdapterVM
import com.Doctoor.app.data.remote.TestsRestService
import com.Doctoor.app.model.response.Tests
import com.Doctoor.app.rx.functions.PlainConsumer
import com.Doctoor.app.ui.interfaces.OnCallApiDone
import javax.inject.Inject

class PopularTestsCategoryFragmentVM @Inject constructor(private val apiServices: TestsRestService) :
    BaseRecyclerAdapterVM<Tests.TestPopularCategory>() {

    override fun callApi(page: Int, onCallApiDone: OnCallApiDone<Tests.TestPopularCategory>) {
        execute(true, apiServices.testPopularCategoryList(),
            PlainConsumer { t ->
                if (t.data is ArrayList)
                /*if page is 1 it means the data is loading first time or it's loading from pull to refresh*/
                    setData(t.data, true)
            }
        )
    }
}
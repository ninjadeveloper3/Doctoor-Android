package com.Doctoor.app.ui.modules.select_lab

import android.os.Bundle
import com.Doctoor.app.base.BaseRecyclerAdapterVM
import com.Doctoor.app.data.remote.TestsRestService
import com.Doctoor.app.model.response.Tests
import com.Doctoor.app.rx.functions.PlainConsumer
import com.Doctoor.app.ui.interfaces.OnCallApiDone
import com.Doctoor.app.ui.modules.select_lab.test.SelectTestFragment
import com.Doctoor.app.utils.Constants.ID
import com.Doctoor.app.utils.Constants.IS_SEARCHE_ABLE
import javax.inject.Inject

class SelectLabCategoryFragmentVM @Inject constructor(private val apiServices: TestsRestService) :
    BaseRecyclerAdapterVM<Tests.TestCategory>() {

    private var labId: Int = 0
    override fun onFirsTimeUiCreate(bundle: Bundle?) {
        super.onFirsTimeUiCreate(bundle)
        labId = bundle?.getInt(ID, 0)!!

    }

    override fun callApi(page: Int, onCallApiDone: OnCallApiDone<Tests.TestCategory>) {
        execute(true, apiServices.testCategoryList(id = labId, page = -1),
            PlainConsumer { t ->
                if (t.data is ArrayList)
                /*if page is 1 it means the data is loading first time or it's loading from pull to refresh*/
                    setData(t.data, true)
            }
        )
    }

    public fun onSearch() {
        val bundle = Bundle()
        bundle.putBoolean(IS_SEARCHE_ABLE, true)
        navigatorHelper?.startFragmentWithBottomNavigation<SelectTestFragment>(
            SelectTestFragment::class.java.name,
            bundle = bundle
        )
    }
}
package com.Doctoor.app.ui.modules.select_lab

import android.view.View
import com.Doctoor.app.base.BaseRecyclerAdapterVM
import com.Doctoor.app.base.BaseViewModel
import com.Doctoor.app.data.remote.TestsRestService
import com.Doctoor.app.model.response.Tests
import com.Doctoor.app.rx.functions.PlainConsumer
import com.Doctoor.app.ui.interfaces.OnCallApiDone
import com.Doctoor.app.ui.modules.upload_prescription.UploadPrescriptionFragment
import javax.inject.Inject

class SelectLabFragmentVM @Inject constructor(private val apiServices: TestsRestService) :
    BaseRecyclerAdapterVM<Tests.Lab>() {

    override fun callApi(page: Int, onCallApiDone: OnCallApiDone<Tests.Lab>) {

        execute(true, apiServices.labList(),
            PlainConsumer { t ->
                if (t.data is ArrayList)
                /*if page is 1 it means the data is loading first time or it's loading from pull to refresh*/
                    setData(t.data, page == 1)
            }
        )
    }

    fun onPrescription(view: View) {
        if (BaseViewModel.isLogin.value!!) {
            navigatorHelper?.startFragmentWithToolbar<UploadPrescriptionFragment>(
                UploadPrescriptionFragment::class.java.name
            )
        } else {
            navigateToLoginWithRoot(view)
        }
    }

}
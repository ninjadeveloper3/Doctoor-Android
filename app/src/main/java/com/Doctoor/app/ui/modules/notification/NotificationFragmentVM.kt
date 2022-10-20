package com.Doctoor.app.ui.modules.notification

import com.Doctoor.app.base.BaseRecyclerAdapterVM
import com.Doctoor.app.data.remote.MedicineRestService
import com.Doctoor.app.model.response.Medicines
import com.Doctoor.app.rx.functions.PlainConsumer
import com.Doctoor.app.ui.interfaces.OnCallApiDone
import javax.inject.Inject

class NotificationFragmentVM @Inject constructor(private val apiService: MedicineRestService) :
    BaseRecyclerAdapterVM<Medicines.Notification>() {
    override fun callApi(page: Int, onCallApiDone: OnCallApiDone<Medicines.Notification>) {
        execute(true, apiService.getNotifications(page = page),
            PlainConsumer { t ->
                setData(t.data, page == 1)
            }
        )
    }
}
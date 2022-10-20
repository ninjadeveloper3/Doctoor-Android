package com.Doctoor.app.ui.modules.in_app_browser

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.Doctoor.app.base.BaseViewModel
import com.Doctoor.app.utils.Constants.CMS_END_POINT
import javax.inject.Inject

class InAppBrowserFragmentVM @Inject constructor() : BaseViewModel() {

    var endPoint = MutableLiveData<String>()
    override fun onFirsTimeUiCreate(bundle: Bundle?) {
        if (bundle != null) {
            if (bundle.containsKey(CMS_END_POINT)) {
                endPoint.value = bundle.getString(CMS_END_POINT)!!
            }
        }
    }


}
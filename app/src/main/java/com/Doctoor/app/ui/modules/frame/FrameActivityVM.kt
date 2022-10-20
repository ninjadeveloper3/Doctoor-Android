package com.Doctoor.app.ui.modules.frame

import android.os.Bundle
import android.text.Spannable
import androidx.lifecycle.MutableLiveData
import com.Doctoor.app.base.BaseViewModel
import javax.inject.Inject

class FrameActivityVM @Inject constructor() : BaseViewModel() {
    val toolbarTitle = MutableLiveData<Spannable>()
    override fun onFirsTimeUiCreate(bundle: Bundle?) {

    }

}
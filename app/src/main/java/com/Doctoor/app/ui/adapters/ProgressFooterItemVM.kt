package com.Doctoor.app.ui.adapters

import android.view.View
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseListDataViewModel
import com.Doctoor.app.model.Simple
import com.Doctoor.app.model.response.BaseModel

class ProgressFooterItemVM : BaseListDataViewModel<BaseModel>() {
    override fun setItem(item: BaseModel) {
    }

    override fun getItem() = Simple("", 0)

    override fun layoutRes() = R.layout.progress_layout

    override fun onItemClick(v: View, item: BaseModel) {
    }
}
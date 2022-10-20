package com.Doctoor.app.binding

import androidx.annotation.Nullable
import androidx.databinding.BindingAdapter
import com.Doctoor.app.data.source.Status
import com.Doctoor.app.widget.MaterialSpinner
import com.Doctoor.app.widget.progressbutton.CircularProgressButton

object BindingAdapters {
    @BindingAdapter("status")
    @JvmStatic
    fun changeState(view: CircularProgressButton, @Nullable status: Status = Status.IDEAL) {

    }

    @BindingAdapter("setSelection")
    @JvmStatic
    fun setSelection(materialSpinner: MaterialSpinner, position: Int) {
        if (materialSpinner.firstVisiblePosition > 0)
            materialSpinner.setSelection(position)
    }
}
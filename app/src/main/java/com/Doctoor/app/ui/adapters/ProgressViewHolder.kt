package com.Doctoor.app.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.Doctoor.app.BR
import com.Doctoor.app.model.response.BaseModel
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class ProgressViewHolder(
    itemView: View,
    viewModel: ProgressFooterItemVM,
    mDataBinding: ViewDataBinding
) : BaseViewHolder<BaseModel, ProgressFooterItemVM>(itemView, viewModel, mDataBinding) {
    companion object {

        fun createInstance(parent: ViewGroup, view: View?): ProgressViewHolder {

            val viewModel = ProgressFooterItemVM()
//            val view =
//                LayoutInflater.from(parent.context).inflate(R.layout.progress_layout, parent, false)
            val mDataBinding = DataBindingUtil.bind<ViewDataBinding>(view!!)
            mDataBinding?.setVariable(BR.progressFooterItemVM, viewModel)


            val kotlinClass: KClass<ProgressViewHolder> = ProgressViewHolder::class
            val ctor = kotlinClass.primaryConstructor
            val myObject = ctor?.call(view, viewModel, mDataBinding) as ProgressViewHolder
            return myObject

            // return ProgressViewHolder(view, viewModel, mDataBinding!!)
        }

    }

}
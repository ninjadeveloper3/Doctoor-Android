package com.Doctoor.app.ui.adapters;

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.Doctoor.app.base.BaseListDataViewModel


abstract class BaseViewHolder<ITEM, VM : BaseListDataViewModel<ITEM>>
    (view: View, viewModel: VM, private val mDataBinding: ViewDataBinding) :
    RecyclerView.ViewHolder(view) {
    private val mViewModel: VM = viewModel
    fun setItem(item: ITEM) {
        mViewModel.setItem(item)
        mDataBinding.executePendingBindings()
    }
}
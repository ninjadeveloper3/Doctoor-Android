package com.Doctoor.app.ui.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.Doctoor.app.base.BaseListDataViewModel
import com.Doctoor.app.navigation.NavigatorHelper
import com.Doctoor.app.ui.interfaces.OnItemClickListener

abstract class BaseRVFooterAdapter<BM, VM : BaseListDataViewModel<BM>, VH : BaseViewHolder<BM, VM>>
    (
    private val context: Context,
    internal var datas: MutableList<BM>,
    private val mNavidation: NavigatorHelper
) :
    BaseHeaderFooterAdapter<BM, VM, VH>(context) {


    @Nullable
    public var itemClickListener: OnItemClickListener<BM>? = null

    override fun itemCountExceptHeaderFooter() = datas.count()

    //    override fun getItemCount() = 10
    override fun onCreateContentViewHolder(
        parent: ViewGroup?,
        viewType: Int
    ): BaseViewHolder<*, *> {
        val viewModel = createViewModel()
        val view =
            LayoutInflater.from(parent?.context).inflate(getLayoutId(viewType), parent, false)
        val mDataBinding = DataBindingUtil.bind<ViewDataBinding>(view)
        mDataBinding?.setVariable(getVariableId(), viewModel)
        val holder: VH = getViewHolder(view, viewModel, mDataBinding!!, viewType)
        holder.itemView.setOnClickListener {
            val item = datas[holder.adapterPosition]
            if (item != null) {
                itemClickListener?.onItemClick(holder.itemView, item)
            }
        }
        return holder

    }

    override fun onBindViewHolder(holder: BaseViewHolder<*, *>, position: Int) {
//        holder as VH
        if (datas.size > 0)
            (holder as VH).setItem(datas[getRealItemPosition(holder)])

    }
    //    override fun onBindViewHolder(holder: VH, position: Int) {
//        holder.setItem(datas[position])
//    }

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
//        val viewModel = createViewModel()
//        val view = LayoutInflater.from(parent.context).inflate(getLayoutId(viewType), parent, false)
//        val mDataBinding = DataBindingUtil.bind<ViewDataBinding>(view)
//        mDataBinding?.setVariable(getVariableId(), viewModel)
//        val holder: VH = getViewHolder(view, viewModel, mDataBinding!!, viewType)
//        holder.itemView.setOnClickListener {
//            val item = datas[holder.adapterPosition]
//            if (item != null) {
//                itemClickListener?.onItemClick(holder.itemView, item)
//            }
//
//        }
//        return holder
//    }

    protected fun createViewModel(): VM {
        val viewModel: VM = getViewModel()
        viewModel.onCreate(Bundle(), mNavidation)
        // viewModel.onCreate(mNavidation)
        //viewModel.navigatorHelper = mNavidation
        itemClickListener = viewModel
        // viewModel.onCreate(Bundle(),mNavidation)
        return viewModel
    }

    @LayoutRes
    abstract fun getLayoutId(viewType: Int): Int

    abstract fun getViewHolder(
        view: View,
        viewModel: VM,
        mDataBinding: ViewDataBinding,
        viewType: Int
    ): VH

    abstract fun getViewModel(): VM
    abstract fun getVariableId(): Int

    fun addAll(datas: List<BM>) {
        this.datas.addAll(datas)
        notifyDataSetChanged()
    }

    fun setData(@Nullable newData: MutableList<BM>) {

        if (this.datas !== newData) {
            this.datas = newData
        }
        notifyDataSetChanged()
    }

    fun add(type: BM) {
        this.datas.add(type)
        notifyItemInserted(this.datas.size - 1)
        notifyDataSetChanged()
    }

    fun removeAll() {
        this.datas.clear()
        notifyDataSetChanged()
    }

    fun remove(type: BM) {
        val position = this.datas.indexOf(type)
        this.datas.remove(type)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    fun change(newItem: BM, oldItem: BM) {
        val position = this.datas.indexOf(oldItem)
        this.datas.set(position, newItem)
        notifyItemChanged(position)
        notifyDataSetChanged()
    }


}
package com.Doctoor.app.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseListDataViewModel
import com.Doctoor.app.ui.adapters.ProgressViewHolder.Companion.createInstance
import java.util.*

abstract class BaseHeaderFooterAdapter<BM, VM : BaseListDataViewModel<BM>, VH : BaseViewHolder<BM, VM>>(
    private val context: Context
) : RecyclerView.Adapter<BaseViewHolder<*, *>>() {

    protected val headers = ArrayList<View>()
    protected val footers = ArrayList<View>()
    var isProgressAdded = false
    private var progressView: View? = null

    /**
     * @return number items of adapter data except header and footer
     */
    abstract fun itemCountExceptHeaderFooter(): Int


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*, *> {
        return if (viewType == TYPE_FOOTER) {
            createInstance(parent, getProgressView())
        } else {
            onCreateContentViewHolder(parent, viewType)
        }
    }

    abstract fun onCreateContentViewHolder(
        parent: ViewGroup?,
        viewType: Int
    ): BaseViewHolder<*, *>

    override fun getItemCount(): Int {
        return headers.size + footers.count() + itemCountExceptHeaderFooter()
    }

    override fun getItemViewType(position: Int) =
        if (position >= headers.size + itemCountExceptHeaderFooter()) TYPE_FOOTER else TYPE_NON_FOOTER_HEADER

    fun getRealItemPosition(holder: BaseViewHolder<*, *>): Int {
        if (isFooter(holder.getItemViewType())) {
            return holder.getAdapterPosition() - footers.count()
        }
        return holder.getAdapterPosition()
    }

    protected fun isFooter(itemViewType: Int) = itemViewType == TYPE_FOOTER
    protected fun isHeader(itemViewType: Int) = itemViewType == TYPE_HEADER

    //add a header to the adapter
    fun addHeader(header: View?) {
        if (header != null && !headers.contains(header)) {
            headers.add(header)
            //animate
            notifyItemInserted(headers.size - 1)
        }
    }

    //add a header at top of adapter
    fun addHeaderFirst(header: View?) {
        if (header != null && !headers.contains(header)) {
            headers.add(0, header)
            //animate
            notifyItemInserted(0)
        }
    }

    /**
     * remove a header from the adapter
     */
    fun removeHeader(header: View?) {
        if (header != null && headers.contains(header)) {
            //animate
            notifyItemRemoved(headers.indexOf(header))
            headers.remove(header)
            if (header.parent != null) {
                (header.parent as ViewGroup).removeView(header)
            }
        }
    }

    /**
     * add a footer to the adapter
     *
     * @param footer view to add
     */
    fun addFooter(footer: View?) {
        if (footer != null && !footers.contains(footer)) {
            footers.add(footer)
            //animate
            notifyItemInserted(headers.count() + itemCountExceptHeaderFooter() + footers.count() - 1)
        }
    }

    /**
     * remove a footer from the adapter
     *
     * @param footer view to remove
     */
    fun removeFooter(footer: View?) {
        if (footer != null && footers.contains(footer)) {
            //animate
            notifyItemRemoved(headers.size + itemCountExceptHeaderFooter() + footers.indexOf(footer))
            footers.remove(footer)
            if (footer.parent != null) {
                (footer.parent as ViewGroup).removeView(footer)
            }
        }
    }

    fun addProgress() {
        /*isProgressAdded = true
        try {
            addFooter(getProgressView())
        } catch (ignored: Exception) {
            ignored.printStackTrace()
        }*/

    }

    fun removeProgress() {
        isProgressAdded = false
        try {
            removeFooter(getProgressView())
        } catch (ignored: Exception) {
        }

    }

    private fun getProgressView(): View? {
        if (progressView == null) {
            progressView = LayoutInflater.from(context).inflate(R.layout.progress_layout, null)
        }
        return progressView
    }

    companion object {
        private val TYPE_HEADER = 1
        private val TYPE_FOOTER = 2
        protected val TYPE_NON_FOOTER_HEADER = -1
    }

}

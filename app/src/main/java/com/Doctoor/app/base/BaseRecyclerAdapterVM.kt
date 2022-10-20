package com.Doctoor.app.base

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.annotation.NonNull
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.RecyclerView
import com.Doctoor.app.ui.adapters.BaseRVFooterAdapter
import com.Doctoor.app.ui.interfaces.OnCallApiDone
import com.Doctoor.app.ui.interfaces.PaginationListener
import com.Doctoor.app.ui.interfaces.Refreshable


abstract class BaseRecyclerAdapterVM<T> : BaseViewModel(), PaginationListener, Refreshable {
    val adapter = ObservableField<BaseRVFooterAdapter<T, *, *>>()

    var data: MutableList<T> = ArrayList()

    companion object {
        @JvmStatic
        @BindingAdapter("app:mAdapter")
        fun setAdapter(
            view: RecyclerView,
            adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>?
        ) {
            if (null == adapter)
                return
            view.adapter = adapter
        }
    }

    private var lastPage = Integer.MAX_VALUE

    fun refresh(delay: Int) {
        Handler(Looper.myLooper()!!).postDelayed({ this.refresh() }, delay.toLong())
    }

    override var currentPage: Int = 0
        set(value) {
            field = value
        }

    override var previousTotal: Int = 0
        set(value) {
            field = value
        }

    override fun onFirsTimeUiCreate(bundle: Bundle?) {
        refresh(0)
    }

    override fun onCallApi(page: Int): Boolean {
        if (page == 1) {
            lastPage = Integer.MAX_VALUE
        }
        if (page > lastPage || lastPage == 0) {
            return false
        }
        currentPage = page

        callApi(page, object : OnCallApiDone<T> {
            override fun onDone(last: Int, isRefresh: Boolean, response: MutableList<T>) {
                lastPage = last
                setData(response, isRefresh)
            }
        })
        return true
    }

    /**
     * Call this to fill data to [.adapter]
     * @param newData new data
     * @param refresh true if data come from refresh action (call remote api)
     */
    protected fun setData(@NonNull newData: MutableList<T>, refresh: Boolean = true) {
        if (data.isNullOrEmpty()) {
            data = newData
        } else {
            if (refresh) {
                data = ArrayList()
            }
            data.addAll(newData)
        }
        adapter.get()?.setData(data)
    }

    fun clear() {
        data = ArrayList()
        adapter.get()?.setData(data)
        adapter.notifyChange()
    }

    override fun refresh() {
        onCallApi(1)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    protected abstract fun callApi(page: Int, onCallApiDone: OnCallApiDone<T>)

}
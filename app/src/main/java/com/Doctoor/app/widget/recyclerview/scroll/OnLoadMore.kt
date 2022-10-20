package com.Doctoor.app.widget.recyclerview.scroll


import androidx.recyclerview.widget.RecyclerView

import com.Doctoor.app.ui.interfaces.PaginationListener

import javax.inject.Inject

class OnLoadMore @Inject
constructor() : InfiniteScroll() {

    private var listener: PaginationListener? = null

    override fun onLoadMore(page: Int, totalItemsCount: Int): Boolean {
        if (listener != null) {
            listener!!.previousTotal = totalItemsCount
            return listener!!.onCallApi(page + 1)
        }
        return true
    }

    fun init(recyclerView: RecyclerView, listener: PaginationListener?) {
        this.listener = listener
        if (listener != null) {
            this.initialize(listener.currentPage, listener.previousTotal)
        }
        recyclerView.addOnScrollListener(this)
    }

    fun unRegisterListener(recyclerView: RecyclerView) {
        recyclerView.removeOnScrollListener(this)
        this.listener = null
    }

    override fun onScrolled(isUp: Boolean) {
        super.onScrolled(isUp)
    }
}
package com.Doctoor.app.widget.recyclerview.scroll


import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.Doctoor.app.ui.adapters.BaseRVFooterAdapter

abstract class InfiniteScroll : RecyclerView.OnScrollListener() {

    private var visibleThreshold = 3
    private var currentPage = 0
    private var previousTotalItemCount = 0
    private var loading = true
    private val startingPageIndex = 0
    private var layoutManager: RecyclerView.LayoutManager? = null

    private var adapter: BaseRVFooterAdapter<*, *, *>? = null

    private var newlyAdded = true

    private fun initLayoutManager(layoutManager: RecyclerView.LayoutManager?) {
        this.layoutManager = layoutManager
        if (layoutManager is GridLayoutManager) visibleThreshold *= layoutManager.spanCount
    }

    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (newlyAdded) {
            newlyAdded = false
            return
        }
        onScrolled(dy > 0)
        if (layoutManager == null) {
            initLayoutManager(recyclerView.layoutManager)
        }
        if (adapter == null) {
            if (recyclerView.adapter is BaseRVFooterAdapter<*, *, *>) {
                adapter = recyclerView.adapter as BaseRVFooterAdapter<*, *, *>?
            }
        }
        if (adapter != null && adapter!!.isProgressAdded) return

        var lastVisibleItemPosition = 0
        val totalItemCount = layoutManager!!.itemCount
//        if (layoutManager is StaggeredGridLayoutManager) {
//            val lastVisibleItemPositions =
//                (layoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(null)
//            lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions)
//        } else
        if (layoutManager is GridLayoutManager) {
            lastVisibleItemPosition =
                (layoutManager as GridLayoutManager).findLastVisibleItemPosition()
        } else if (layoutManager is LinearLayoutManager) {
            lastVisibleItemPosition =
                (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        }
        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex
            this.previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                this.loading = true
            }
        }
        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }
        Log.d("OnScrolled DY>>", dy.toString())
        Log.d("OnScrolled total>>", totalItemCount.toString())
        // if current data is load from realm, don't perform load more event
        if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            currentPage++
            val isCallingApi = onLoadMore(currentPage, totalItemCount)
            loading = true
            if (adapter != null && isCallingApi) {
                //                recyclerView.post(() -> {
                adapter!!.addProgress()
                //                });
            }
        }
    }

    fun reset() {
        this.currentPage = this.startingPageIndex
        this.previousTotalItemCount = 0
        this.loading = true
    }

    fun initialize(page: Int, previousTotal: Int) {
        this.currentPage = page
        this.previousTotalItemCount = previousTotal
        this.loading = true
    }

    abstract fun onLoadMore(page: Int, totalItemsCount: Int): Boolean

    open fun onScrolled(isUp: Boolean) {
        
    }

}


package com.Doctoor.app.base

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.Doctoor.app.R
import com.Doctoor.app.data.source.State
import com.Doctoor.app.data.source.Status
import com.Doctoor.app.ui.adapters.BaseRVFooterAdapter
import com.Doctoor.app.ui.interfaces.Scrollable
import com.Doctoor.app.ui.interfaces.UiRefreshable
import com.Doctoor.app.ui.lazy.bindView
import com.Doctoor.app.utils.dimen
import com.Doctoor.app.widget.MultiStateView
import com.Doctoor.app.widget.recyclerview.SpaceGridItemDecoration
import com.Doctoor.app.widget.recyclerview.scroll.OnLoadMore
import javax.inject.Inject


abstract class BaseRecyclerViewFragment<out VB : ViewDataBinding, VM : BaseRecyclerAdapterVM<BM>,
        A : BaseRVFooterAdapter<BM, *, *>, BM>
    : BaseViewModelFragment<VB, VM>(), UiRefreshable, Scrollable, MultiStateView.OnReloadListener {

    @Inject
    protected lateinit var adapter: A

    @Inject
    lateinit var onLoadMore: OnLoadMore
    protected var isRefreshing: Boolean = false

    val recyclerView: RecyclerView? by bindView(R.id.recyclerView)
    val refreshLayout: SwipeRefreshLayout? by bindView(R.id.refreshLayout)
    val stateLayout: MultiStateView? by bindView(R.id.multiStateView)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getmViewModel().adapter.set(adapter)
        if (recyclerView?.layoutManager is GridLayoutManager) {
            recyclerView?.addItemDecoration(
                SpaceGridItemDecoration(dimen(R.dimen.padding)!!, 2, true)
            )
        } else {
            recyclerView?.addItemDecoration(spacesItemDecoration())
        }
        refreshLayout?.setColorSchemeResources(
            R.color.sky_blue, R.color.blue,
            R.color.light_pink, R.color.pink
        )
        refreshLayout?.setOnRefreshListener { refresh() }
        stateLayout?.reloadListener = this
    }

    override fun onReload(view: View) {
        refreshWithUi()
    }

    override fun onStart() {
        super.onStart()
        onLoadMore.init(recyclerView!!, getmViewModel())
    }

    override fun onStop() {
        super.onStop()
        onLoadMore.unRegisterListener(recyclerView!!)
    }

    override fun doneRefresh() {

        refreshLayout?.isRefreshing = false

        isRefreshing = false
    }

    override fun refreshWithUi() {
        refreshWithUi(0)
    }

    override fun refreshWithUi(delay: Int) {
        if (refreshLayout != null) {
            refreshLayout?.postDelayed(Runnable {
                refreshUi()
                refresh()
            }, delay.toLong())
        }
    }

    override fun setRefreshEnabled(enabled: Boolean) {
        refreshLayout?.isEnabled = enabled
    }

    override fun refresh() {
        if (!isRefreshing) {
            getmViewModel().refresh()
            onLoadMore.reset()
            isRefreshing = true
        }
    }

    private fun refreshUi() {
        refreshLayout?.isRefreshing = true
    }

    override fun scrollTop(animate: Boolean) {
        if (animate) {
            recyclerView?.smoothScrollToPosition(0)
        } else {
            recyclerView?.scrollToPosition(0)
        }
    }

    override fun setLoading(loading: Boolean) {
        if (!loading) {
            doneRefresh()
            //adapter.removeProgress()
        } else {
            if (!adapter.isProgressAdded) {
//                refreshUi()

                //un comment this to enable both loaders
            }
        }
    }

    override fun handleState(state: State?) {
        super.handleState(state)
        when {
            state?.status == Status.LOADING -> stateLayout?.viewState =
                MultiStateView.ViewState.LOADING
            state?.status == Status.EMPTY -> {
                stateLayout?.viewState = MultiStateView.ViewState.EMPTY
            }
            state?.status == Status.ERROR -> {
                stateLayout?.viewState = MultiStateView.ViewState.ERROR
            }
            state?.status == Status.SUCCESS ->
                stateLayout?.viewState =
                    if (adapter.datas.count() > 0) MultiStateView.ViewState.CONTENT else MultiStateView.ViewState.EMPTY

        }
    }
}
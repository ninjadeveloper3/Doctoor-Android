package com.Doctoor.app.ui.interfaces

interface OnCallApiDone<E> {

    /**
     * Called after success response come
     * @param last last page
     * @param isRefresh true if refreshed
     * @param response response data
     */
    fun onDone(last: Int, isRefresh: Boolean, response: MutableList<E>)
}
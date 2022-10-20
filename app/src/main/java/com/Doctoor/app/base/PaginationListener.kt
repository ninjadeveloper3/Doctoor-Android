package com.Doctoor.app.base

interface PaginationListener {
    var currentPage: Int

    var previousTotal: Int

    fun onCallApi(page: Int): Boolean
}
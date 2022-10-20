package com.Doctoor.app.ui.interfaces

interface PaginationListener {
    var currentPage: Int

    var previousTotal: Int

    fun onCallApi(page: Int = 0): Boolean
}
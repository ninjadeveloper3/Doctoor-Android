package com.Doctoor.app.data.repository

import com.Doctoor.app.data.remote.HomeRestService
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val apiService: HomeRestService
) : BaseRepository() {
}
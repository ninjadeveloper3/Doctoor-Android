package com.Doctoor.app.data.repository

import com.Doctoor.app.data.remote.TestsRestService
import javax.inject.Inject

class TestsRepository @Inject constructor(
    private val apiService: TestsRestService
) : BaseRepository() {
}
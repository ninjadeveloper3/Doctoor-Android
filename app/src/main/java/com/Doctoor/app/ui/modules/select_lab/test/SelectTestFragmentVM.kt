package com.Doctoor.app.ui.modules.select_lab.test

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.Doctoor.app.base.BaseRecyclerAdapterVM
import com.Doctoor.app.data.remote.TestsRestService
import com.Doctoor.app.model.response.Tests
import com.Doctoor.app.rx.functions.PlainConsumer
import com.Doctoor.app.ui.interfaces.OnCallApiDone
import com.Doctoor.app.utils.Constants
import com.Doctoor.app.utils.Constants.CAT_ID
import com.Doctoor.app.utils.Constants.LAB_ID
import com.Doctoor.app.utils.debug
import javax.inject.Inject

class SelectTestFragmentVM @Inject constructor(private val apiServices: TestsRestService) :
    BaseRecyclerAdapterVM<Tests.Test>() {
    private var categoryId: Int = 0
    private var labId: Int = 0
    var isSearchEnable: MutableLiveData<Boolean> = MutableLiveData()
    var query = ""
    var searchQuery = MutableLiveData<String>()
    var keyboardState = MutableLiveData<Boolean>()
    override fun onFirsTimeUiCreate(bundle: Bundle?) {
        super.onFirsTimeUiCreate(bundle)
        if (bundle?.containsKey(Constants.IS_SEARCHE_ABLE)!!) {
            isSearchEnable.value = true
        } else {
            isSearchEnable.value = false
            categoryId = bundle.getInt(CAT_ID, 0)
            labId = bundle.getInt(LAB_ID, 0)
        }
    }

    override fun callApi(page: Int, onCallApiDone: OnCallApiDone<Tests.Test>) {
        getLabTests(page)
    }

    private fun getLabTests(page: Int) {
        if (!isSearchEnable.value!!) {
            if (query.isEmpty()) {
                execute(true,
                    apiServices.TestList(lab_id = labId, category_id = categoryId, page = page),
                    PlainConsumer { t ->
                        debug(page)
                        if (t.data is ArrayList)
                        /*if page is 1 it means the data is loading first time or it's loading from pull to refresh*/
                            setData(t.data, page == 1)
                    }
                )
            } else {
                search(this.query)
            }
        } else if (query.isNotEmpty()) {
            search(this.query)
        }
    }


    private fun search(query: String?) {
        if (query?.isNotEmpty()!!) {
            execute(true, apiServices.searchTest(query.toString(), labId.toString()),
                PlainConsumer { t ->
                    setData(t.data, true)
                }
            )
        } else {
            this.query = "a"
            search(this.query)
        }
    }

    fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        setData(ArrayList())

        query = s.toString()
        searchQuery.value = s.toString()
        search(s.toString())
    }

    fun onSearchSubmit() {
        keyboardState.postValue(true)
        search(this.query)
    }

    fun clearSearch() {
        this.query = ""
        searchQuery.value = ""
        clear()
        this.query = "a"
        search(this.query)
    }
}
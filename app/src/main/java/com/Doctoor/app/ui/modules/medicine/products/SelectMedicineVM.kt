package com.Doctoor.app.ui.modules.medicine.products

import android.os.Bundle
import android.view.KeyEvent
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.Doctoor.app.base.BaseRecyclerAdapterVM
import com.Doctoor.app.data.remote.MedicineRestService
import com.Doctoor.app.model.response.Medicines
import com.Doctoor.app.rx.functions.PlainConsumer
import com.Doctoor.app.ui.interfaces.OnCallApiDone
import com.Doctoor.app.ui.interfaces.OnOkInSoftKeyboardListener
import com.Doctoor.app.utils.Constants.ALL_CATEGORY
import com.Doctoor.app.utils.Constants.ID
import com.Doctoor.app.utils.Constants.IS_SEARCHE_ABLE
import com.Doctoor.app.widget.EditTextRichDrawable
import javax.inject.Inject


class SelectMedicineVM @Inject constructor(
    private val apiServices: MedicineRestService
) :
    BaseRecyclerAdapterVM<Medicines.Product>() {

    private var categoryId: Int = 0
    var isSearchEnable = false
    var query = ""
    var searchQuery = MutableLiveData<String>()
    private var isMiscellaneous = false
    var keyboardState = MutableLiveData<Boolean>()
    override fun onFirsTimeUiCreate(bundle: Bundle?) {
        super.onFirsTimeUiCreate(bundle)
        if (bundle?.containsKey(IS_SEARCHE_ABLE)!!) {
            isSearchEnable = true
        } else if (bundle.containsKey(ID)) {
            categoryId = bundle.getInt(ID, 0)
        } else if (bundle.containsKey(ALL_CATEGORY)) {
            isMiscellaneous = true
        }
    }

    override fun callApi(page: Int, onCallApiDone: OnCallApiDone<Medicines.Product>) {
        getMedicineData(page)
    }

    private fun getMedicineData(page: Int) {
        if (!isSearchEnable) {
            if (isMiscellaneous) {

                if (query.isEmpty()) {
                    execute(true, apiServices.otherMedicinesList(page = page),
                        PlainConsumer { t ->
                            if (t.data is ArrayList)
                            /*if page is 1 it means the data is loading first time or it's loading from pull to refresh*/
                                setData(t.data, page == 1)
                        }
                    )
                } else {
                    search(this.query)
                }
            } else {
                if (query.isEmpty()) {

                    execute(true, apiServices.medicinesList(id = categoryId, page = page),
                        PlainConsumer { t ->
                            if (t.data is ArrayList)
                            /*if page is 1 it means the data is loading first time or it's loading from pull to refresh*/
                                setData(t.data, page == 1)
                        }
                    )
                } else {
                    search(this.query)
                }
            }

        } else if (query.isNotEmpty()) {
            search(this.query)
        }
    }

    private fun search(query: String?) {
        if (query?.isEmpty()!!) {
            if (isMiscellaneous || categoryId == 0) {
                this.query = "a"
                search(this.query)
            } else {
                getMedicineData(1)
            }
        } else {
            if (isMiscellaneous) {
                execute(true,
                    apiServices.searchOtherMedicine(query.toString()),
                    PlainConsumer { t ->
                        setData(t.data, true)
                    }
                )
            } else {
                if (categoryId == 0) {
                    execute(true,
                        apiServices.searchMedicine(medicineName = query.toString()),
                        PlainConsumer { t ->
                            setData(t.data.medicines, true)
                            setData(t.data.miscellaneous, false)
                        }
                    )
                } else {
                    execute(true,
                        apiServices.searchMedicineWithCategory(
                            medicineName = query.toString(),
                            categoryId = categoryId.toString()
                        ),
                        PlainConsumer { t ->
                            setData(t.data, true)
                        }
                    )
                }
            }
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
        if (isMiscellaneous || categoryId == 0) {
            this.query = "a"
            search(this.query)
        } else {
            getMedicineData(1)
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("app:onSearchSubmit") // I like it to match the listener method name
        fun setOnOkInSoftKeyboardListener(
            view: EditTextRichDrawable,
            listener: OnOkInSoftKeyboardListener?
        ) {
            if (listener == null) {
                view.setOnEditorActionListener(null)
            } else {
                view.setOnEditorActionListener(object : TextView.OnEditorActionListener {
                    override fun onEditorAction(
                        v: TextView,
                        actionId: Int,
                        event: KeyEvent?
                    ): Boolean {
                        // ... solution to receiving event
                        listener.onOkInSoftKeyboard()
                        return true
                    }
                })
            }
        }
    }
}
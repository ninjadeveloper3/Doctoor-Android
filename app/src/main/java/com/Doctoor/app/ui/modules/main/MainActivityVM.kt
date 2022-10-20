package com.Doctoor.app.ui.modules.main

import android.os.Bundle
import android.text.Spannable
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.Doctoor.app.DoctoorApp.Companion.string
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseViewModel
import com.Doctoor.app.data.database.CartManager
import com.Doctoor.app.data.database.EquipmentDao
import com.Doctoor.app.data.database.LabTestDao
import com.Doctoor.app.data.database.MedicineDao
import com.Doctoor.app.data.remote.MedicineRestService
import com.Doctoor.app.data.repository.UserRepository
import com.Doctoor.app.model.request.UserRequest
import com.Doctoor.app.model.response.City
import com.Doctoor.app.model.response.Medicines
import com.Doctoor.app.preference.DataStoreManager
import com.Doctoor.app.rx.functions.PlainConsumer
import com.Doctoor.app.ui.modules.landing.LandingFragment
import javax.inject.Inject


class MainActivityVM @Inject constructor(
    private val apiServices: MedicineRestService,
    private val dataStoreManager: DataStoreManager,
    private val userRepo: UserRepository,
    private var equipmentDao: EquipmentDao,
    private var medicineDao: MedicineDao,
    private var labTestDao: LabTestDao,
    private var cartManager: CartManager

) : BaseViewModel() {
    val toolbarTitle = MutableLiveData<Spannable>()
    val adapter = ObservableField<MainActivity.Adapter>()
    var categor = MutableLiveData<MutableList<Medicines.Category>>()
    var androidApplicationVersion: String = ""

    init {
        isLogin.value = dataStoreManager.isUserSessionStarted
    }

    override fun onFirsTimeUiCreate(bundle: Bundle?) {
        storeManager = dataStoreManager
        mUserLiveData?.value = storeManager?.currentUser
        prepareNavigationItems()
        fetchMedicineCategories()
        getVersion()
//        execute(true, userRepo.updatePlayerId(UserRequest.PlayId("3535-3535-3535-3535-3535")), PlainConsumer {
//        })
    }

    private fun getVersion() {
        execute(false, dataStoreManager.apiService?.getVersion()!!, PlainConsumer { t ->
            t.appVersions?.androidApplicationVersion?.let {
                androidApplicationVersion = it
            }
        })
    }

    companion object {
        @JvmStatic
        @BindingAdapter("app:mAdapter")
        fun setAdapter(
            view: RecyclerView,
            adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>?
        ) {
            if (null == adapter)
                return
            view.adapter = adapter
        }
    }

    private fun fetchMedicineCategories() {
        execute(false, apiServices.medicinesCategories(page = -1), PlainConsumer { t ->
            dataStoreManager.dataStore.saveAsList(t.data, Medicines.Category::class.java.name)
        })
    }

    private fun prepareNavigationItems() {
//        val categories: MutableList<Medicines.Category> =
//            dataStoreManager.dataStore.getMedicinesCategories(
//                Medicines.Category::class.java.name
//            )

        var categories: MutableList<Medicines.Category> = ArrayList()

        categories.add(
            0,
            Medicines.Category(
                string(R.string.my_lab_reports),
                "",
                0,
                "",
                "",
                R.drawable.ic_online_lab_test
            )
        )

        categories.add(
            1,
            Medicines.Category(
                string(R.string.my_prescriptions),
                "",
                0,
                "",
                "",
                R.drawable.ic_prescription
            )
        )
        categories.add(
            2,
            Medicines.Category(string(R.string.about_us), "", 0, "", "", R.drawable.ic_about_us)
        )
        categories.add(
            3,
            Medicines.Category(
                string(R.string.shop_by_categories),
                "",
                0,
                "",
                "",
                R.drawable.ic_about_us
            )
        )

        categories.add(
            4,
            Medicines.Category(
                string(R.string.stomach_ache),
                "",
                16,
                "",
                "",
                R.drawable.ic_stomach_ache
            )
        )

        categories.add(
            5,
            Medicines.Category(
                string(R.string.fever),
                "",
                9,
                "",
                "",
                R.drawable.ic_fever
            )
        )
        categories.add(
            6,
            Medicines.Category(
                "High Blood Pressure",
                "",
                14,
                "",
                "",
                R.drawable.ic_high_blood_preasure
            )
        )
        categories.add(
            7,
            Medicines.Category(
                "Cough & Cold",
                "",
                8,
                "",
                "",
                R.drawable.ic_cough_cold
            )
        )
        categories.add(
            8,
            Medicines.Category(
                "Female Health",
                "",
                3,
                "",
                "",
                R.drawable.ic_feminine_care
            )
        )
        categories.add(
            9,
            Medicines.Category(
                "Skin, Hair & Nail care",
                "",
                7,
                "",
                "",
                R.drawable.ic_hair_and_skin
            )
        )
        categories.add(
            10,
            Medicines.Category(
                "Diabetes",
                "",
                11,
                "",
                "",
                R.drawable.ic_diabetes
            )
        )

        categories.add(
            Medicines.Category(
                string(R.string.other_categories),
                "",
                0,
                "",
                "",
                R.drawable.ic_others_categories
            )
        )
        categor.value = categories
//        adapter.get()?.addAll(categories)

    }


    public fun onLogin() {
        if (!dataStoreManager.isUserSessionStarted)
            navigatorHelper?.startFragment<LandingFragment>(LandingFragment::class.java.name, false)
        else {
            /*save backup of categories*/

            val categories: MutableList<Medicines.Category> =
                dataStoreManager.dataStore.getMedicinesCategories(
                    Medicines.Category::class.java.name
                )

            val cities: MutableList<City> =
                dataStoreManager.dataStore.getCities(
                    City::class.java.name
                )

            dataStoreManager.reset()

            cartManager.emptyCart(labTestDao, medicineDao, equipmentDao)

            mUserLiveData?.value = null

            /*again save categories */
            dataStoreManager.dataStore.saveAsList(categories, Medicines.Category::class.java.name)
            dataStoreManager.dataStore.saveAsList(cities, City::class.java.name)

            dataStoreManager.dataStore.setFirstTimeLunch(true)
        }
    }

    fun updatePlayId(playerId: String) {

        if (dataStoreManager.isUserSessionStarted) {
            execute(true, userRepo.updatePlayerId(UserRequest.PlayId(playerId)), PlainConsumer {
            })
        }

    }
}
package com.Doctoor.app.ui.modules.main

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.core.view.MenuItemCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.Doctoor.app.BR
import com.Doctoor.app.BuildConfig
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseFragment
import com.Doctoor.app.base.BaseViewModel.Companion.cartItemsCount
import com.Doctoor.app.base.BaseViewModelActivity
import com.Doctoor.app.base.BaseViewModelFragment
import com.Doctoor.app.data.database.EquipmentDao
import com.Doctoor.app.data.database.LabTestDao
import com.Doctoor.app.data.database.MedicineDao
import com.Doctoor.app.databinding.ActivityMainBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.model.response.Equipments
import com.Doctoor.app.model.response.Medicines
import com.Doctoor.app.model.response.Tests
import com.Doctoor.app.navigation.Navigator.Companion.EXTRA_ARG
import com.Doctoor.app.navigation.NavigatorHelper
import com.Doctoor.app.preference.DataStoreManager
import com.Doctoor.app.preference.UserDataStore
import com.Doctoor.app.rx.Task
import com.Doctoor.app.ui.adapters.BaseRVAdapter
import com.Doctoor.app.ui.adapters.BaseViewHolder
import com.Doctoor.app.ui.modules.cart.CartFragment
import com.Doctoor.app.ui.modules.dashboard.HomeFragment
import com.Doctoor.app.utils.AlertUtils
import com.Doctoor.app.utils.Constants.FRAGMENT_CLASS
import com.Doctoor.app.utils.Constants.INDEX
import com.Doctoor.app.utils.Constants.SHOW_ALERT
import com.Doctoor.app.utils.debug
import com.Doctoor.app.utils.setBoldFontTypeSpan
import com.Doctoor.app.utils.toast
import com.onesignal.OneSignal
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor


class MainActivity : BaseViewModelActivity<ActivityMainBinding, MainActivityVM>() {
    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<MainActivityVM>

    @Inject
    lateinit var drawerHolder: MainDrawerHolderV2

    @Inject
    lateinit var bottomNavigationHolder: BottomNavigationHolder

    @Inject
    lateinit var adapter: Adapter

    @Inject
    lateinit var equipmentDao: EquipmentDao

    @Inject
    lateinit var medicineDao: MedicineDao

    @Inject
    lateinit var labTestDao: LabTestDao

    @Inject
    lateinit var dataStore: UserDataStore

    var medicines: MutableList<Medicines.Product> = ArrayList()
    var tests: MutableList<Tests.Test> = ArrayList()
    var equipments: MutableList<Equipments.Equipment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);

        drawerHolder.init(drawerLayout, toolbar, this)
        bottomNavigationHolder.init(bottomNavigation)
        getMyViewModel().adapter.set(adapter)
        var fragmentName: String = HomeFragment::class.java.name
        if (intent.hasExtra(FRAGMENT_CLASS)) {
            bottomNavigationHolder.setDefaultSelectedIndex(intent.getIntExtra(INDEX, 0))
            fragmentName = intent.getStringExtra(FRAGMENT_CLASS)!!

        } else {
            if (savedInstanceState == null) {
                fragmentName = HomeFragment::class.java.name
            }
        }
        val fragment: BaseViewModelFragment<*, *> =
            navigatorHelper.fragmentInstantiate(this, fragmentName) as BaseViewModelFragment<*, *>
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commitNow()
        getMyViewModel().toolbarTitle.postValue(
            setBoldFontTypeSpan(
                fragment.getToolBarTile(),
                R.font.mark_pro_light
            )
        )

        getMyViewModel().categor.observe(this, Observer { t -> adapter.addAll(t) })

        /*Initialize One Signal */
//        OneSignal.startInit(this).setNotificationOpenedHandler(NotificationOpenedHandler(this))

        /*Getting player ID*/
        OneSignal.idsAvailable { userId, registrationId ->
            getMyViewModel().updatePlayId(userId)
            debug("User id $userId")
            debug("registrationId $registrationId")

        }
        fab.setOnClickListener {
            openDialer()
        }

        //// set observer to observe data change in shared preferences and update it everywhere in side menu
        dataStore.mUserLiveData.observe(this, Observer {
            getMyViewModel().mUserLiveData?.value = it
            if (it == null) {
                bottomNavigationHolder.onMenuItemSelect(R.id.home, 0, false)
                bottomNavigationHolder.setSelectedIndex(0)
            }
        })


        val versionCode = BuildConfig.VERSION_CODE
        val versionName = BuildConfig.VERSION_NAME
        val versionText = "Version: $versionName  Build: $versionCode"
        getViewDataBinding().versionName.text = versionText

        intent.extras?.let {

            val isShowAlert = it.getBundle(EXTRA_ARG)?.getBoolean(SHOW_ALERT, false)

            if (BuildConfig.DEBUG) {
                if (isShowAlert != null && isShowAlert) {
                    val title = "Application Information"
                    val message =
                        "You are currently using\nApp Version $versionName with  Build no:$versionCode"
                    AlertUtils.showAlertDialog(
                        this@MainActivity,
                        title,
                        message
                    ) { _, i ->
                        if ("$versionName.0" != getMyViewModel().androidApplicationVersion) {
                            AlertUtils.showAlertDialog(
                                this@MainActivity,
                                "Version mismatch",
                                "Please update your application to the latest version"
                            ) { _, _ ->
                                navigatorHelper.finishActivity()
                            }
                        }
                    }
                }

            }
        }
    }

    override fun onResume() {
        super.onResume()
        getCartCount()
    }

    private fun getCartCount() {
        cartItemsCount.value = 0
        Task.runSafely({
            equipments = equipmentDao.getAllAllEquipments()
            tests = labTestDao.getAllAllTests()
            medicines = medicineDao.getAllAllMedicinesProduct()
        }, {
            cartItemsCount.value = 0
            cartItemsCount.value = equipments.count() + tests.count() + medicines.count()
        }, true)
    }

    override fun onDestroy() {
        super.onDestroy()
        cartItemsCount.removeObservers(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cart, menu)

        val menuItem = menu.findItem(R.id.cart)

        val actionView = MenuItemCompat.getActionView(menuItem)

        val cartBadge = actionView.findViewById(R.id.cart_badge) as TextView

        cartItemsCount.observe(this, Observer<Int> { value ->
            if (value > 0) {
                cartBadge.visibility = View.VISIBLE
                cartBadge.text = (value.toString())
            } else
                cartBadge.visibility = View.GONE
        })

        actionView.setOnClickListener { onOptionsItemSelected(menuItem) }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) =
        when (item?.itemId) {
            R.id.cart -> {
                if (cartItemsCount.value!! > 0) {
                    navigatorHelper.startFragmentWithToolbar<CartFragment>(
                        CartFragment::class.java.name
                    )
                } else {
                    toast("Cart is empty")

                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState);
        drawerHolder.onPostCreate(savedInstanceState)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        drawerHolder.onConfigurationChanged(newConfig)
    }

    override fun layoutRes() = R.layout.activity_main
    override fun toolbar() = true
    override fun getHomeAsUpIndicator() = R.drawable.ic_drawer

    override fun onBackPressed() {
        if (!drawerHolder.closeDrawer()) {
            super.onBackPressed()
        }
    }

    override fun getBindingVariable() = BR.mainActivityVM

    override fun getMyViewModel() = viewModel.get()

    class Adapter(
        mValue: MutableList<Medicines.Category>,
        mNavidation: NavigatorHelper,
        var dataStoreManager: DataStoreManager,
        var activity: MainActivity
    ) :
        BaseRVAdapter<Medicines.Category, NavigationItemVM, Adapter.ViewHolder>(
            mValue,
            mNavidation
        ) {
        companion object {
            private const val TYPE_ITEM = 0
            private const val TYPE_SECTION = 1
        }

        override fun getLayoutId(viewType: Int): Int {
            return when (viewType) {
                TYPE_SECTION -> R.layout.item_navigation_view_section
                else -> getViewModel().layoutRes()
            }
        }

        override fun getViewHolder(
            view: View,
            viewModel: NavigationItemVM,
            mDataBinding: ViewDataBinding, viewType: Int
        ): ViewHolder {
            val kotlinClass: KClass<ViewHolder> = ViewHolder::class
            val ctor = kotlinClass.primaryConstructor
            val myObject = ctor?.call(view, viewModel, mDataBinding) as ViewHolder
            return myObject
        }

        override fun getViewModel() = NavigationItemVM(dataStoreManager, activity)

        override fun getVariableId() = BR.navigationItemVM

        override fun getItemViewType(position: Int): Int {
            return when (position) {
                3 -> TYPE_SECTION
                else -> TYPE_ITEM
            }
        }

        class ViewHolder(view: View, viewModel: NavigationItemVM, mDataBinding: ViewDataBinding) :
            BaseViewHolder<Medicines.Category, NavigationItemVM>(view, viewModel, mDataBinding)
    }

}

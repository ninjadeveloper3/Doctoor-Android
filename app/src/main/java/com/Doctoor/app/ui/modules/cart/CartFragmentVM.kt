package com.Doctoor.app.ui.modules.cart

import android.os.Bundle
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.Doctoor.app.base.BaseViewModel
import com.Doctoor.app.data.database.CartManager
import com.Doctoor.app.data.database.EquipmentDao
import com.Doctoor.app.data.database.LabTestDao
import com.Doctoor.app.data.database.MedicineDao
import com.Doctoor.app.data.remote.ServicesRestService
import com.Doctoor.app.model.response.BaseModel
import com.Doctoor.app.model.response.Equipments
import com.Doctoor.app.model.response.Medicines
import com.Doctoor.app.model.response.Tests
import com.Doctoor.app.preference.DataStoreManager
import com.Doctoor.app.rx.Task
import com.Doctoor.app.rx.functions.PlainConsumer
import com.Doctoor.app.ui.modules.checkout.shipping.ShippingFragment
import com.Doctoor.app.ui.modules.upload_prescription.UploadPrescriptionFragment
import com.Doctoor.app.utils.Constants
import com.Doctoor.app.utils.Constants.IS_FROM_CART
import com.Doctoor.app.utils.Constants.IS_UPLOADED
import com.Doctoor.app.utils.validation.Validator
import javax.inject.Inject

class CartFragmentVM @Inject constructor(
    private var equipmentDao: EquipmentDao,
    private var medicineDao: MedicineDao,
    private var labTestDao: LabTestDao,
    private val apiService: ServicesRestService,
    private var cartManager: CartManager,
    private val dataStoreManager: DataStoreManager
) : BaseViewModel() {

    override fun onFirsTimeUiCreate(bundle: Bundle?) {
        if (bundle != null) {
            if (bundle.containsKey(IS_UPLOADED)) {
                val isFromPrescription = bundle.getBoolean(IS_UPLOADED)
                val id = bundle.getInt(Constants.PRESCRIPTION_ID, 0)
                if (isFromPrescription) {
                    onShipping(true, id)
                }
            }
        }
    }

    val adapter = ObservableField<CartFragment.Adapter>()

    var medicines: MutableList<Medicines.Product> = ArrayList()
    var tests: MutableList<Tests.Test> = ArrayList()
    var equipments: MutableList<Equipments.Equipment> = ArrayList()

    var allItems: MutableList<BaseModel> = ArrayList()

    var couponCode = MutableLiveData<String>()

    var onCouponApply = MutableLiveData<String>()
    var showDeliveryCharges = MutableLiveData<Boolean>()
    var isPrescriptionRequired = MutableLiveData<Boolean>()

    internal var validator: Validator? = null

    init {
        isPrescriptionRequired.value = false
        showDeliveryCharges.value = false
    }

    public fun checkOut(view: View) {
        /*check is user logged in*/
        if (isLogin()) {
            /*check if the order price is greater than 0*/
            if (totalOrderPrice.value!! > 0)
            /*check is there any item with prescription required or not.*/
                if (!isPrescriptionRequired.value!!)
                    onShipping()
                else
                    onPrescription()

        } else {
            navigateToLogin(view)
        }
    }


    public fun onPrescription() {
        val bundle = Bundle()
        bundle.putBoolean(IS_FROM_CART, true)
        navigatorHelper?.startFragmentWithToolbar<UploadPrescriptionFragment>(
            UploadPrescriptionFragment::class.java.name, bundle = bundle
        )
    }

    fun onShipping(isFinish: Boolean = false, prescriptionId: Int = 0) {
        val bundle = Bundle()
        bundle.putInt(Constants.PRESCRIPTION_ID, prescriptionId)
        navigatorHelper?.startFragmentWithToolbar<ShippingFragment>(
            ShippingFragment::class.java.name,
            bundle = bundle
        )
        if (isFinish) {
            navigatorHelper?.finishActivity()
        }
    }


    fun continueShopping() =
        navigatorHelper?.navigateMainActivity(true)

    fun fetchCartItems() {
        Task.runSafely({
            getItemsFromDB()
        }, {
            adapter.get()?.addAll(allItems)
            notifyChange()
            setCartPrices()
        }, true)
    }

    private fun getItemsFromDB() {
        Task.runSafely {
            //            OrderX = ArrayList()
//            equipments = ArrayList()
//            tests = ArrayList()
//            medicines = ArrayList()
            allItems = ArrayList()

            equipments = equipmentDao.getAllAllEquipments()
            tests = labTestDao.getAllAllTests()
            medicines = medicineDao.getAllAllMedicinesProduct()

            allItems.addAll(tests)
            allItems.addAll(equipments)
            allItems.addAll(medicines)
        }
    }


    fun setCartPrices() {
        var havePrescriptionHaveCount = 0
        totalOrderPrice.value = 0.0
        totalOrderPriceIncludesDelivery.value = 0.0
        allItems.forEach { item ->
            if (item is Equipments.Equipment) {
                if (item.isPrescriptionReq == 1) {
                    havePrescriptionHaveCount += 1
                }
                updateCartPrice(item.price, item.orderQuantity, true)
            }
            if (item is Medicines.Product) {
                if (item.isPrescriptionReq == 1) {
                    havePrescriptionHaveCount += 1
                }
                updateCartPrice(item.price, item.orderQuantity, true)
            }
            if (item is Tests.Test) {
                if (item.isPrescriptionReq == 1) {
                    havePrescriptionHaveCount += 1
                }

                updateCartPrice(item.price, item.orderQuantity, true)
            }
        }

        isPrescriptionRequired.value = havePrescriptionHaveCount > 0
        notifyChange()
    }

    fun onApply(v: View) {
        if (validator?.validate()!!) {

            if (!isLogin()) {
                navigateToLogin(v)
                return
            }

            if (discountPercentage.value!! > 0) {
                onCouponApply.value = "Discount already applied"
                return
            }

            if (totalOrderPrice.value!! > 0) {    /// check either price is 0
                execute(
                    true,
                    apiService.checkPromotion(couponCode = couponCode.value!!),
                    PlainConsumer { response ->
                        val responseMessage = response.responseHeader?.responseMessage

                        if (response.responseHeader?.responseCode == 200) {
                            couponCode.value = ""
                            discountPercentage.value = response.data.percentage
                            onCouponApply.value = responseMessage
                            dataStoreManager.setDiscount(response.data.percentage)
                        } else {
                            onCouponApply.value = responseMessage
                        }
                    })
            }
        }
    }

    fun onEmptyCart() {
        cartManager.emptyCart(labTestDao, medicineDao, equipmentDao)
        allItems = ArrayList()
        adapter.get()?.removeAll()
        adapter.get()?.notifyDataSetChanged()
        navigatorHelper?.finishActivity()
    }

    private fun isLogin(): Boolean {
        return BaseViewModel.isLogin.value!!
    }
}
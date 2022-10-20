package com.Doctoor.app.ui.modules.checkout.payment

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.Doctoor.app.base.BaseViewModel
import com.Doctoor.app.data.database.EquipmentDao
import com.Doctoor.app.data.database.LabTestDao
import com.Doctoor.app.data.database.MedicineDao
import com.Doctoor.app.data.remote.ServicesRestService
import com.Doctoor.app.data.repository.ServicesRepository
import com.Doctoor.app.data.source.State
import com.Doctoor.app.model.request.ServiceRequest
import com.Doctoor.app.model.response.BaseModel
import com.Doctoor.app.model.response.Equipments
import com.Doctoor.app.model.response.Medicines
import com.Doctoor.app.model.response.Tests
import com.Doctoor.app.rx.Task
import com.Doctoor.app.rx.functions.PlainConsumer
import com.Doctoor.app.ui.modules.checkout.complete_order.CompleteOrderFragment
import com.Doctoor.app.ui.modules.easypaisa.EasypaisaPaymentFragment
import com.Doctoor.app.utils.Constants
import com.Doctoor.app.utils.Constants.AMOUNT
import com.Doctoor.app.utils.Constants.EASY_PAISA_MA_METHOD
import com.Doctoor.app.utils.Constants.ID
import com.Doctoor.app.utils.Constants.IS_EXCEEDED
import com.Doctoor.app.utils.Constants.IS_RENTAL_PAYMENT
import com.Doctoor.app.utils.Constants.IS_SERVICE_PAYMENT
import com.Doctoor.app.utils.Constants.ORDER_NUMBER
import javax.inject.Inject

class PaymentFragmentVM @Inject constructor(
    private var labTestDao: LabTestDao,
    private var medicineDao: MedicineDao,
    private var equipmentDao: EquipmentDao,
    private val apiServices: ServicesRestService,
    private val servicesRepository: ServicesRepository
) : BaseViewModel() {
    var isExceeded = MutableLiveData<Boolean>().apply { value = false }
    var isServicePayment = false
    var isRentalPayment = false

    var totalAmount = "0.0"
    var orderNumber = ""

    lateinit var order: ServiceRequest.Order
    var medicines: MutableList<Medicines.Product> = ArrayList()
    var tests: MutableList<Tests.Test> = ArrayList()
    var equipments: MutableList<Equipments.Equipment> = ArrayList()

    var OrderX: ArrayList<ServiceRequest.OrderX> = ArrayList()

    var allItems: MutableList<BaseModel> = ArrayList()

    override fun onFirsTimeUiCreate(bundle: Bundle?) {

        isServicePayment = bundle?.getBoolean(IS_SERVICE_PAYMENT, false)!!
        isRentalPayment = bundle.getBoolean(IS_RENTAL_PAYMENT, false)

        totalAmount = bundle.getString(AMOUNT, "0.0")
        orderNumber = bundle.getString(ORDER_NUMBER, "")

        if (bundle.getBoolean(IS_EXCEEDED, false)) {
            isExceeded.value = bundle.getBoolean(IS_EXCEEDED, false)
        } else {
            val totalAmount: Double = if (discountPercentage.value!! > 0) {
                discountedOrderPrice.value!!
            } else {
                totalOrderPriceIncludesDelivery.value!!
            }
            if (totalAmount > 50000) {
                isExceeded.value = true
            }
        }

        prescriptionId = bundle.getInt(Constants.PRESCRIPTION_ID, 0)

//
//        val bundles = Bundle()
//        bundles.putBoolean(IS_SERVICE_PAYMENT, isServicePayment)
//        bundles.putBoolean(IS_EXCEEDED, isExceeded.value!!)
//        bundles.putString(ORDER_NUMBER, order_number)
//        bundles.putDouble(AMOUNT, amount)
    }

    fun onPayment(paymentMethodId: Int) {

        val bundle = Bundle()
        bundle.putInt(ID, paymentMethodId)
        bundle.putBoolean(IS_SERVICE_PAYMENT, isServicePayment)
        bundle.putBoolean(IS_RENTAL_PAYMENT, isRentalPayment)
        bundle.putBoolean(IS_EXCEEDED, isExceeded.value!!)
        bundle.putString(ORDER_NUMBER, orderNumber)
        bundle.putString(AMOUNT, totalAmount)
        bundle.putInt(Constants.PRESCRIPTION_ID, prescriptionId)
        navigatorHelper?.startFragmentWithToolbar<CompleteOrderFragment>(
            CompleteOrderFragment::class.java.name,
            bundle = bundle,
            showCartMenu = false
        )
    }

    fun onEasyPaisaPayment() {

        if (totalAmount !== "0.0" && orderNumber.isNotEmpty()) {
            navigateToEasyPaisa(orderNumber, totalAmount)
        } else {

            totalAmount = if (discountPercentage.value!! > 0) {
                discountedOrderPrice.value!!.toString()
            } else {
                totalOrderPriceIncludesDelivery.value!!.toString()
            }
            OrderX = ArrayList()
            Task.runSafely({
                allItems = ArrayList()

                equipments = equipmentDao.getAllAllEquipments()
                tests = labTestDao.getAllAllTests()
                medicines = medicineDao.getAllAllMedicinesProduct()

                allItems.addAll(tests)
                allItems.addAll(equipments)
                allItems.addAll(medicines)
            }, {

                Task.runSafely({
                    for (item in allItems) {
                        if (item is Equipments.Equipment) {

                            OrderX.add(
                                ServiceRequest.OrderX(
                                    item.id,
                                    "Equipments",
                                    item.orderQuantity
                                )
                            )

                        }
                        if (item is Medicines.Product) {

                            val productType = if (item.medicineImage.isNullOrEmpty()) {
                                "OtherMedicines"
                            } else {
                                "Medicines"
                            }
                            OrderX.add(
                                ServiceRequest.OrderX(
                                    item.id,
                                    productType,
                                    item.orderQuantity
                                )
                            )

                        }
                        if (item is Tests.Test) {

                            OrderX.add(
                                ServiceRequest.OrderX(
                                    item.id,
                                    "LabsTest",
                                    item.orderQuantity
                                )
                            )

                        }
                    }
                }, {

                    placeNewOrder()

                }, true)
            }, true)
        }
    }

    private fun placeNewOrder() {

        order =

            ServiceRequest.Order(
                billing,
                OrderX,
                EASY_PAISA_MA_METHOD.toString(),
                shipping,
                totalAmount.toDouble(),
                prescriptionId = prescriptionId
            )


        execute(true, servicesRepository.newOrder(order), PlainConsumer { response ->
            val responseMessage = response.responseHeader?.responseMessage
            when (response.data.userId > 0) {
                true -> {
                    orderNumber = response.data.orderNumber
                    navigateToEasyPaisa(orderNumber, totalAmount)
                }
                else -> {
                    publishState(State.error(responseMessage.toString()))
                }
            }
        })
    }


    private fun navigateToEasyPaisa(
        orderNumber: String,
        totalAmount: String
    ) {

        val bundle = Bundle()
        bundle.putInt(Constants.PRESCRIPTION_ID, prescriptionId)
        bundle.putBoolean(IS_SERVICE_PAYMENT, isServicePayment)
        bundle.putBoolean(IS_RENTAL_PAYMENT, isRentalPayment)
        bundle.putBoolean(IS_EXCEEDED, isExceeded.value!!)
        bundle.putString(ORDER_NUMBER, orderNumber)
        bundle.putString(AMOUNT, totalAmount)
        navigatorHelper?.startFragmentWithToolbar<EasypaisaPaymentFragment>(
            EasypaisaPaymentFragment::class.java.name,
            bundle = bundle,
            clearAllPrevious = false,
            showCartMenu = false
        )
    }
}
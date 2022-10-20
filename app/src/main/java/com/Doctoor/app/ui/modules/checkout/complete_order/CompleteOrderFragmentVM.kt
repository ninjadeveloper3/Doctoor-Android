package com.Doctoor.app.ui.modules.checkout.complete_order

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.Doctoor.app.BuildConfig.BASE_URL
import com.Doctoor.app.DoctoorApp
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseViewModel
import com.Doctoor.app.data.database.CartManager
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
import com.Doctoor.app.ui.interfaces.ConfirmOrder
import com.Doctoor.app.utils.Constants
import com.Doctoor.app.utils.Constants.AMOUNT
import com.Doctoor.app.utils.Constants.COD_PAYMENT_METHOD
import com.Doctoor.app.utils.Constants.COD_PAYMENT_METHOD_EXCEED
import com.Doctoor.app.utils.Constants.EASY_PAISA_CC_METHOD
import com.Doctoor.app.utils.Constants.ID
import com.Doctoor.app.utils.Constants.NOT_PAID
import com.Doctoor.app.utils.Constants.ORDER_NUMBER
import com.Doctoor.app.utils.debug
import javax.inject.Inject


class CompleteOrderFragmentVM @Inject constructor(
    private val apiServices: ServicesRestService,
    private val servicesRepository: ServicesRepository,
    private var labTestDao: LabTestDao,
    private var medicineDao: MedicineDao,
    private var equipmentDao: EquipmentDao,
    private var cartManager: CartManager
) : BaseViewModel() {
    var paymentMethodId = 0
    var currentUrl = ""
    lateinit var order: ServiceRequest.Order
    var medicines: MutableList<Medicines.Product> = ArrayList()
    var tests: MutableList<Tests.Test> = ArrayList()
    var equipments: MutableList<Equipments.Equipment> = ArrayList()

    var OrderX: ArrayList<ServiceRequest.OrderX> = ArrayList()

    var allItems: MutableList<BaseModel> = ArrayList()

    var paymentUrl: MutableLiveData<String>? = MutableLiveData()
    var isWebViewStarted: Boolean = false

    var isExceeded = MutableLiveData<Boolean>()

    var isServicePayment = MutableLiveData<Boolean>()
    var isRentalPayment = MutableLiveData<Boolean>()

    var isProgressShow = MutableLiveData<Boolean>().apply { value = false }

    var orderNumber: String = ""
    var totalAmount = 0.0

    lateinit var confirmOrder: ConfirmOrder

    override fun onFirsTimeUiCreate(bundle: Bundle?) {

        paymentMethodId = bundle?.getInt(ID)!!

        isExceeded.value = bundle.getBoolean(Constants.IS_EXCEEDED, false)

        isServicePayment.value = bundle.getBoolean(Constants.IS_SERVICE_PAYMENT, false)

        isRentalPayment.value = bundle.getBoolean(Constants.IS_RENTAL_PAYMENT, false)

        totalAmount = bundle.getString(AMOUNT, "0.0").toDouble()
        orderNumber = bundle.getString(ORDER_NUMBER, "")

        prescriptionId = bundle.getInt(Constants.PRESCRIPTION_ID, 0)
    }

    fun onOrder() {

        /*check either the page is navigated from the notification for service payment or it is navigating from the previous payment method from cart*/
        if ((isServicePayment.value!! || isRentalPayment.value!!) && totalAmount > 0 && orderNumber.isNotEmpty()) {
            if (isExceeded.value!! || isCOD()) {
                if (isServicePayment.value!!) {
                    confirmOrder(
                        orderNumber = orderNumber,
                        status = NOT_PAID,
                        message = DoctoorApp.instance?.getString(R.string.order_placed)!!,
                        isService = 1
                    )
                } else if (isRentalPayment.value!!) {
                    confirmOrder(
                        orderNumber = orderNumber,
                        status = NOT_PAID,
                        message = DoctoorApp.instance?.getString(R.string.order_placed)!!,
                        isRental = 1
                    )
                }
            } else if (isEasyPaisaCC()) {
                paymentUrl?.value =
                    BASE_URL + "easyPasia?orderRefNum=${orderNumber}&amount={$totalAmount}"
            } else {
                debug("totalAmount $totalAmount orderNumber $orderNumber")
                getPaymentUrl(totalAmount, orderNumber)
            }

        } else {
            totalAmount = if (discountPercentage.value!! > 0) {
                discountedOrderPrice.value!!
            } else {
                totalOrderPriceIncludesDelivery.value!!
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

        if (isCOD()) {
            paymentMethodId = COD_PAYMENT_METHOD
        }

        order = ServiceRequest.Order(
            billing,
            OrderX,
            paymentMethodId.toString(),
            shipping,
            totalAmount,
            prescriptionId = prescriptionId
        )

        execute(true, servicesRepository.newOrder(order), PlainConsumer { response ->
            val responseMessage = response.responseHeader?.responseMessage
            when (response.data.userId > 0) {
                true -> {
                    orderNumber = response.data.orderNumber
                    when {
                        isCOD() -> /*NOTIFY USER AND EMPTY CART*/
                            confirmOrder(
                                orderNumber = orderNumber,
                                status = NOT_PAID,
                                message = DoctoorApp.instance?.getString(R.string.order_placed)!!
                            )
                        isEasyPaisaCC() -> {
                            paymentUrl?.value =
                                BASE_URL + "easyPasia?orderRefNum=${orderNumber}&amount=${totalAmount}"
                        }
                        else -> getPaymentUrl(totalAmount, response.data.orderNumber)
                    }
                }
                else -> {
                    publishState(State.error(responseMessage.toString()))
                }
            }
        })
    }

    fun isCOD(): Boolean {
        return paymentMethodId == COD_PAYMENT_METHOD || paymentMethodId == COD_PAYMENT_METHOD_EXCEED
    }

    private fun isEasyPaisaCC(): Boolean {
        return paymentMethodId == EASY_PAISA_CC_METHOD
    }
/*
* @param status:I -> The status of the order this method will update the status of order
*
* */

    /**
     * Call this to update the status of order
     * @param status The status of the order this method will update the status of order
     * @param isSuccess true if order payment done and user need to go back to main false if payment failed and user need to retry the payment
     */
    fun confirmOrder(
        orderNumber: String,
        status: Int,
        message: String,
        isSuccess: Boolean = true,
        isRental: Int = 0,
        isService: Int = 0,
        paymentMethodId: Int = COD_PAYMENT_METHOD

    ) {

        val request = ServiceRequest.ConfirmOrder(
            orderNumber = orderNumber,
            status = status,
            isRental = isRental,
            isService = isService,
            paymentMethodId = paymentMethodId
        )

        execute(true, servicesRepository.confirmOrder(request), PlainConsumer { response ->
            if (isSuccess) {
                confirmOrder.onConfirm(message, isRental == 1, isService == 1)
            }
        })
    }


    fun confirmFromEasyPaisaServer() {
        val request = ServiceRequest.ConfirmEasyPaisaCC(orderId = orderNumber)
        execute(true, servicesRepository.inquiryPayment(request), PlainConsumer { response ->
            when (response.responseCode) {
                Constants.EASYPAISA_SUCCESS -> {
                    successPayment(DoctoorApp.string(R.string.transaction_completed))
                }
                else -> {
                    publishState(State.error(DoctoorApp.string(R.string.transaction_Failed)))
                }
            }
        })
    }

    fun successPayment(message: String) {
        val isService: Int
        val isRental: Int
        when {
            isServicePayment.value!! -> {
                isService = 1
                isRental = 0
            }
            isRentalPayment.value!! -> {
                isService = 0
                isRental = 1
            }
            else -> {
                isService = 0
                isRental = 0
            }
        }
        confirmOrder(
            orderNumber = orderNumber,
            status = Constants.CASH_PAID,
            message = message,
            paymentMethodId = paymentMethodId,
            isService = isService,
            isRental = isRental
        )

    }

    fun getPaymentUrl(totalAmount: Double, orderNumber: String) {
        execute(true,
            apiServices.doPayment(
                paymentMethod = paymentMethodId,
                totalAmount = totalAmount,
                orderNumber = orderNumber
            ),
            PlainConsumer { t ->
                if (t.data.isNotEmpty()) {
                    paymentUrl?.value = t.data
                }
            }
        )
    }

    fun gotoMainActivity() {
        navigatorHelper?.navigateMainActivity(true)
    }

    fun emptyCart() {
        cartManager.emptyCart(labTestDao, medicineDao, equipmentDao)
    }
}
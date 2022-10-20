package com.Doctoor.app.ui.modules.easypaisa

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.Doctoor.app.base.BaseViewModel
import com.Doctoor.app.data.database.CartManager
import com.Doctoor.app.data.database.EquipmentDao
import com.Doctoor.app.data.database.LabTestDao
import com.Doctoor.app.data.database.MedicineDao
import com.Doctoor.app.data.remote.ServicesRestService
import com.Doctoor.app.data.repository.ServicesRepository
import com.Doctoor.app.data.source.State
import com.Doctoor.app.model.request.ServiceRequest
import com.Doctoor.app.model.response.MedicalServices
import com.Doctoor.app.rx.functions.PlainConsumer
import com.Doctoor.app.ui.interfaces.ConfirmOrder
import com.Doctoor.app.utils.Constants
import com.Doctoor.app.utils.Constants.AMOUNT
import com.Doctoor.app.utils.Constants.EASYPAISA_SUCCESS
import com.Doctoor.app.utils.Constants.IS_RENTAL_PAYMENT
import com.Doctoor.app.utils.Constants.IS_SERVICE_PAYMENT
import com.Doctoor.app.utils.Constants.ORDER_NUMBER
import com.Doctoor.app.utils.validation.Validator
import javax.inject.Inject

class EasypaisaPaymentVM @Inject constructor(
    private val apiServices: ServicesRestService,
    private val servicesRepository: ServicesRepository,
    private var labTestDao: LabTestDao,
    private var medicineDao: MedicineDao,
    private var equipmentDao: EquipmentDao,
    private var cartManager: CartManager
) : BaseViewModel() {
    internal var validator: Validator? = null

    var isCCPayment = MutableLiveData<Boolean>().apply { value = false }
    var isProgressShow = MutableLiveData<Boolean>().apply { value = false }
    var isServicePayment = false
    var isRentalPayment = false

    var amount = "0.0"
    var orderNumber = ""
    var easypaisaResponse: MedicalServices.EasyPaisaResponseResponse? = null
    var pendingTask: Unit? = null
    var phone = MutableLiveData<String>()
        set(value) {
            notifyChange()
            field = value
        }

    var email = MutableLiveData<String>()
        set(value) {
            notifyChange()
            field = value
        }
    lateinit var confirmOrder: ConfirmOrder

    override fun onFirsTimeUiCreate(bundle: Bundle?) {
        phone.value = "+92"
        isServicePayment = bundle?.getBoolean(IS_SERVICE_PAYMENT, false)!!
        isRentalPayment = bundle.getBoolean(IS_RENTAL_PAYMENT, false)
        amount = bundle.getString(AMOUNT, "0.0")
        orderNumber = bundle.getString(ORDER_NUMBER, "")
    }

    fun onCancelTransaction() {
        navigatorHelper?.finishActivity()
    }

    fun onEasyPaisaPayment() {
        if (validator?.validate()!!) {

            var phone = phone.value!!
            if (phone.startsWith("+92")) {
                phone = phone.replaceFirst("+92", "0")
            }
            val request = ServiceRequest.EasyPaisaPayment(
                orderId = orderNumber,
                transactionAmount = amount.toDouble(),
                mobileAccountNo = phone,
                emailAddress = email.value!!
            )

            execute(true, apiServices.easyPaisaPayment(request), PlainConsumer { response ->
                val responseMessage = response.responseHeader?.responseMessage
                if (response.data != null) {
                    easypaisaResponse = response
                    when (response.data!!.responseCode) {
                        EASYPAISA_SUCCESS -> {
                            successPayment(responseMessage.toString())


//                            if (isInBackground) {
////                                debug("onAppBackgrounded")
//                                pendingTask = successPayment(responseMessage.toString())
//                            } else {
//                                debug("onAppForegrounded")
//                                pendingTask = null
//                                successPayment(responseMessage.toString())
//                            }

                        }
                        else -> {
                            publishState(State.error(responseMessage.toString()))
//                            if (isInBackground) {
//                                debug("onAppBackgrounded")
//                                pendingTask = publishState(State.error(responseMessage.toString()))
//                            } else {
//                                debug("onAppForegrounded")
//                                pendingTask = null
//                                publishState(State.error(responseMessage.toString()))
//                            }
                        }
                    }
                }

            })
        }
    }

    fun successPayment(message: String) {
        val isService: Int
        val isRental: Int
        when {
            isServicePayment -> {
                isService = 1
                isRental = 0
            }
            isRentalPayment -> {
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
            paymentMethodId = Constants.EASY_PAISA_MA_METHOD,
            isService = isService,
            isRental = isRental
        )

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
    private fun confirmOrder(
        orderNumber: String,
        status: Int,
        message: String,
        isSuccess: Boolean = true,
        isRental: Int = 0,
        isService: Int = 0,
        paymentMethodId: Int = Constants.COD_PAYMENT_METHOD

    ) {

        val request = ServiceRequest.ConfirmOrder(
            orderNumber = orderNumber,
            status = status,
            isRental = isRental,
            isService = isService,
            paymentMethodId = paymentMethodId
        )

        execute(true, servicesRepository.confirmOrder(request), PlainConsumer { response ->
            easypaisaResponse = null
            if (isSuccess) {
                confirmOrder.onConfirm(message, isRental == 1, isService == 1)
            }
        })
    }

    fun gotoMainActivity() {
        navigatorHelper?.navigateMainActivity(true)
    }

    fun emptyCart() {
        cartManager.emptyCart(labTestDao, medicineDao, equipmentDao)
    }

}
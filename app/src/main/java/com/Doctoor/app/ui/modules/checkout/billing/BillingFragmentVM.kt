package com.Doctoor.app.ui.modules.checkout.billing

import android.os.Bundle
import com.google.gson.Gson
import com.Doctoor.app.base.OrderBaseModal
import com.Doctoor.app.data.remote.ServicesRestService
import com.Doctoor.app.model.request.ServiceRequest
import com.Doctoor.app.model.response.City
import com.Doctoor.app.preference.DataStoreManager
import com.Doctoor.app.rx.functions.PlainConsumer
import com.Doctoor.app.ui.modules.checkout.payment.PaymentFragment
import com.Doctoor.app.utils.Constants
import com.Doctoor.app.utils.debug
import com.Doctoor.app.utils.validation.Validator
import javax.inject.Inject

class BillingFragmentVM @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val apiService: ServicesRestService
) : OrderBaseModal(dataStoreManager) {
    internal var validator: Validator? = null

    override fun onFirsTimeUiCreate(bundle: Bundle?) {
        super.onFirsTimeUiCreate(bundle)

        prescriptionId = bundle?.getInt(Constants.PRESCRIPTION_ID, 0)!!
        debug(prescriptionId)

        phone.value = "+92"

        if (shipping.isSameAddress) {
            address.value = shipping.homeAddress
            email.value = shipping.email
            firstName.value = shipping.firstName
            lastName.value = shipping.lastName
            phone.value = shipping.phone
            province.value = shipping.province
            cityName.value = shipping.city

            provincePosition.value = bundle.getInt("provincePosition")
            cityPosition.value = bundle.getInt("cityPosition")

        } else {
            execute(true, apiService.getBillingDetails(), PlainConsumer { response ->
                val jsonString = Gson().toJson(response)
                debug("response $jsonString")
                try {
                    if (response.data.id > 0) {
                        cityName.value = response.data.city
                        email.value = response.data.email
                        firstName.value = response.data.firstName
                        lastName.value = response.data.lastName
                        address.value = response.data.homeAddress
                        phone.value = "+" + response.data.phone
                        isSameAddress.value = false

                        val jsonStrings = Gson().toJson(shipping)
                        debug("shipping item $jsonStrings")
                    }
                } catch (e: Exception) {
                    debug("error" + e.localizedMessage)
                }
            })
        }
    }

    override fun prepareCityItems() {
        cities = dataStoreManager.dataStore.getCities(City::class.java.name)
        dataStoreManager.dataStore.getCities(City::class.java.name).forEach { city ->
            cityNames.add(city.cityName)
        }
    }

    public fun onCheckout() {
        if (validator?.validate()!!) {

            isProvinceError.postValue(provincePosition.value == 0)
            isCityError.postValue(cityPosition.value == -1)

            if (provincePosition.value == 0) {
                return
            }
            if (cityPosition.value == -1) {
                return
            }


            billing = ServiceRequest.Billing(
                cityName.value!!,
                firstName.value!!,
                address.value!!,
                lastName.value!!,
                phone.value!!,
                province.value!!
            )

            val bundle = Bundle()
            bundle.putInt(Constants.PRESCRIPTION_ID, prescriptionId)
            navigatorHelper?.startFragmentWithToolbar<PaymentFragment>(
                PaymentFragment::class.java.name,
                bundle = bundle
            )
        }
    }
}
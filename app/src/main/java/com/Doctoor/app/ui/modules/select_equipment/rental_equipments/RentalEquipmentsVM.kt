package com.Doctoor.app.ui.modules.select_equipment.rental_equipments

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.Doctoor.app.DoctoorApp
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseViewModel
import com.Doctoor.app.data.remote.ServicesRestService
import com.Doctoor.app.data.repository.ServicesRepository
import com.Doctoor.app.data.source.State
import com.Doctoor.app.model.request.ServiceRequest
import com.Doctoor.app.model.response.Equipments
import com.Doctoor.app.rx.functions.PlainConsumer
import com.Doctoor.app.ui.interfaces.SpinnerHandler
import com.Doctoor.app.utils.Constants.COMMENTS
import com.Doctoor.app.utils.Constants.EQUIPMENT_ID
import com.Doctoor.app.utils.Constants.ID
import com.Doctoor.app.utils.validation.Validator
import javax.inject.Inject

class RentalEquipmentsVM @Inject constructor(
    private val service: ServicesRestService,
    private val servicesRepository: ServicesRepository
) : BaseViewModel() {
    var cityId = 0

    override fun onFirsTimeUiCreate(bundle: Bundle?) {

        equipmentId = bundle?.getInt(EQUIPMENT_ID, 0)!!
        comment.value = bundle.getString(COMMENTS)
        cityId = bundle.getInt(ID, 0)
        this.fetchRentalServices()
    }

    var isRentaError = MutableLiveData<Boolean>().apply { setValue(false) }

    internal var validator: Validator? = null

    internal var spinnerHandler: SpinnerHandler? = null

    var equipments: MutableList<Equipments.RentalEquipment> = ArrayList()

    val serviceNames = ArrayList<String>()
    var equipmentId = 0

    val position = MutableLiveData<Int>().apply { setValue(-1) }

    var comment = MutableLiveData<String>()
        set(value) {
            field = value
            notifyChange()
        }

    fun getisRentaError(): LiveData<Boolean> {
        return this.isRentaError
    }

    private fun fetchRentalServices() {

        execute(false, service.rentalMedicalEquipments(cityId = cityId), PlainConsumer { t ->
            equipments = t.data

            equipments.forEach { equipment ->
                serviceNames.add(equipment.equipment_name!!)
            }

            if (equipmentId > 0) {
                val equipment =
                    equipments.single { equipment -> equipment.rentalEquipmentId == equipmentId }
                val index = equipments.indexOf(equipment)
                if (index != -1)
                    spinnerHandler?.onResponse(index + 1)
            }

        })
    }

    fun onServiceSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {

        if (equipments.size > 0 && pos > -1) {
//            val psitions = parent.selectedItemPosition
            position.postValue(pos)
            equipmentId = equipments[pos].rentalEquipmentId!!
        } else {
            position.postValue(-1)
        }
    }

    fun onSubmit(v: View) {
        if (validator?.validate()!!) {
            isRentaError.postValue(position.value == -1)

            if (position.value == -1) {
                isRentaError.postValue(true)
                return
            }

            /*check is user logged in*/
            if (isLogin.value!!) {

                val request = ServiceRequest.RentalEquipment(
                    equipmentId,
                    comment.value.toString(),
                    cityId = cityId
                )

                execute(true, servicesRepository.orderRental(request), PlainConsumer { response ->
                    val responseMessage = response.responseHeader?.responseMessage

                    if (response.data.id > 0) {
                        publishState(State.success(DoctoorApp.string(R.string.service_placed_message)))
                    } else {
                        publishState(State.error(responseMessage!!))
                    }
                })
            } else {
                navigateToLogin(v)
            }
        }
    }
}
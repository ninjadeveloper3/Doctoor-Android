package com.Doctoor.app.ui.modules.service_type

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
import com.Doctoor.app.model.response.MedicalServices
import com.Doctoor.app.rx.Task
import com.Doctoor.app.rx.functions.PlainConsumer
import com.Doctoor.app.ui.interfaces.SpinnerHandler
import com.Doctoor.app.utils.Constants
import com.Doctoor.app.utils.Constants.COMMENTS
import com.Doctoor.app.utils.Constants.ID
import com.Doctoor.app.utils.Constants.SERVICE_ID
import com.Doctoor.app.utils.validation.Validator
import javax.inject.Inject

class MedicalServicesFragmentVM @Inject constructor(
    private val service: ServicesRestService,
    private val servicesRepository: ServicesRepository
) : BaseViewModel() {

    override fun onFirsTimeUiCreate(bundle: Bundle?) {

        cityId = bundle?.getInt(ID, 0)!!
        serviceId = bundle.getInt(SERVICE_ID, 0)
        comment.value = bundle.getString(COMMENTS)
        IS_POPULAR_SERVICES.value = bundle.getBoolean(Constants.IS_POPULAR_SERVICES, false)

        this.fetchServicesList(cityId, IS_POPULAR_SERVICES.value!!)
    }

    var isServiceError = MutableLiveData<Boolean>().apply { setValue(false) }

    internal var validator: Validator? = null

    internal var spinnerHandler: SpinnerHandler? = null

    var services: MutableList<MedicalServices.Service> = ArrayList()

    val serviceNames = ArrayList<String>()
    private var cityId = 0
    var serviceId = 0
    var IS_POPULAR_SERVICES = MutableLiveData<Boolean>().apply { setValue(false) }
    val position = MutableLiveData<Int>().apply { setValue(-1) }

    var comment = MutableLiveData<String>()
        set(value) {
            field = value
            notifyChange()
        }

    fun getIsServiceError(): LiveData<Boolean> {
        return this.isServiceError
    }

    private fun fetchServicesList(cityId: Int, IS_POPULAR_SERVICES: Boolean) {
        if (IS_POPULAR_SERVICES) {
            execute(false, service.medicalPopularServicesList(), PlainConsumer { t ->
                services = t.data
                if (services.size > 0) {
                    Task.run({
                        services.forEach { service ->
                            serviceNames.add(service.serviceName)
                        }
                    }, {
                        if (serviceId > 0) {
                            val service =
                                services.single { service -> service.serviceId == serviceId }
                            val index = services.indexOf(service)
                            if (index != -1)
                                spinnerHandler?.onResponse(index + 1)
                        }
                    }, true)
                }
            })
        } else {
            execute(false, service.medicalServicesList(cityId = cityId), PlainConsumer { t ->
                services = t.data
                if (services.size > 0) {
                    Task.run({
                        services.forEach { service ->
                            serviceNames.add(service.serviceName)
                        }
                    }, {
                        if (serviceId > 0) {
                            val service =
                                services.single { service -> service.serviceId == serviceId }
                            val index = services.indexOf(service)
                            if (index != -1)
                                spinnerHandler?.onResponse(index + 1)
                        }
                    }, true)
                }

            })
        }
    }

    fun onServiceSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {

        if (services.size > 0 && pos > -1) {
            position.postValue(pos)
            serviceId = services[pos].serviceId
        } else {
            position.postValue(-1)
        }
    }

    fun onSubmit(view: View) {
        if (validator?.validate()!!) {
            if (position.value == -1) {
                isServiceError.postValue(true)
                return
            }
            /*check is user logged in*/
            if (isLogin.value!!) {
                val request =
                    ServiceRequest.OrderService(serviceId, cityId, comment.value.toString())

                execute(true, servicesRepository.orderService(request), PlainConsumer { response ->
                    val responseMessage = response.responseHeader?.responseMessage

                    if (response.data.id > 0) {
                        publishState(State.success(DoctoorApp.string(R.string.service_placed_message)))
                    } else {
                        publishState(State.error(responseMessage!!))
                    }
                })
            } else {
                navigateToLogin(view)
            }
        }
    }
}
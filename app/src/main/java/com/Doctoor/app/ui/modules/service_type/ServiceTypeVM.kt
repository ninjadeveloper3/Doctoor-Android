package com.Doctoor.app.ui.modules.service_type

import android.os.Bundle
import com.Doctoor.app.base.BaseViewModel
import com.Doctoor.app.ui.modules.select_equipment.SelectEquipmentFragment
import com.Doctoor.app.ui.modules.select_equipment.rental_equipments.RentalEquipmentsFragment
import com.Doctoor.app.ui.modules.upload_prescription.UploadPrescriptionFragment
import com.Doctoor.app.utils.Constants.ID
import javax.inject.Inject

class ServiceTypeVM @Inject constructor() : BaseViewModel() {
    var cityId = 0

    override fun onFirsTimeUiCreate(bundle: Bundle?) {
        cityId = bundle?.getInt(ID, 0)!!
    }

    fun onPrescription() =
        navigatorHelper?.startFragmentWithBottomNavigation<UploadPrescriptionFragment>(
            UploadPrescriptionFragment::class.java.name
        )

    fun gotoMedicalServices() {

        val bundle = Bundle()
        bundle.putInt(ID, cityId)
        navigatorHelper?.startFragmentWithBottomNavigation<MedicalServicesFragment>(
            MedicalServicesFragment::class.java.name,
            bundle = bundle
        )
    }

    fun gotoSelectEquipment() {
        val bundle = Bundle()
        bundle.putInt(ID, cityId)
        navigatorHelper?.startFragmentWithBottomNavigation<SelectEquipmentFragment>(
            SelectEquipmentFragment::class.java.name,
            bundle = bundle
        )
    }

    fun gotoRentalEquipments() {
        val bundle = Bundle()
        bundle.putInt(ID, cityId)
        navigatorHelper?.startFragmentWithBottomNavigation<RentalEquipmentsFragment>(
            RentalEquipmentsFragment::class.java.name,
            bundle = bundle
        )
    }
}
package com.Doctoor.app.ui.modules.medicine.products

import android.os.Bundle
import android.view.View
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseListDataViewModel
import com.Doctoor.app.data.database.CartManager
import com.Doctoor.app.data.database.MedicineDao
import com.Doctoor.app.model.response.Medicines
import com.Doctoor.app.rx.Task
import com.Doctoor.app.ui.modules.medicine.details.MedicineDetailsFragment
import com.Doctoor.app.utils.Constants.DATA

class SelectMedicinesItemVM(private val dao: MedicineDao, private var cartManager: CartManager) :
    BaseListDataViewModel<Medicines.Product>() {
    private var simple: Medicines.Product? = null
    override fun setItem(item: Medicines.Product) {
        this.simple = item


        Task.runSafely {
            isInCart = cartManager.isInCart(medicineDao = dao, item = simple)
            notifyChange()
        }
    }

    override fun getItem() = simple!!

    override fun layoutRes() = R.layout.item_select_medicine
    override fun onItemClick(v: View, item: Medicines.Product) {
        val bundle = Bundle()
        bundle.putParcelable(DATA, item)
        navigatorHelper?.startFragmentWithBottomNavigation<MedicineDetailsFragment>(
            MedicineDetailsFragment::class.java.name,
            bundle = bundle, showCartMenu = true
        )
    }

    fun onCartClick(item: Medicines.Product) {
        isInCart = cartManager.insertOrDeleteMedicine(dao, item, !isInCart)
        notifyChange()
    }

}
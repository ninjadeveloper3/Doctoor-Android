package com.Doctoor.app.data.database

import com.google.gson.Gson
import com.Doctoor.app.base.BaseViewModel
import com.Doctoor.app.base.BaseViewModel.Companion.cartItemsCount
import com.Doctoor.app.base.BaseViewModel.Companion.discountPercentage
import com.Doctoor.app.base.BaseViewModel.Companion.discountedOrderPrice
import com.Doctoor.app.base.BaseViewModel.Companion.totalOrderPrice
import com.Doctoor.app.model.response.BaseModel
import com.Doctoor.app.model.response.Equipments
import com.Doctoor.app.model.response.Medicines
import com.Doctoor.app.model.response.Tests
import com.Doctoor.app.rx.Task
import com.Doctoor.app.utils.debug

open class CartManager() {
    fun insertOrDeleteMedicine(
        dao: MedicineDao,
        item: Medicines.Product,
        isInsert: Boolean
    ): Boolean {
        debug(Gson().toJson(item))
        return if (isInsert) {
            Task.runSafely {
                if (item.orderQuantity == 0) {
                    item.orderQuantity = 1
                }
                dao.insertMedicinesProduct(item)
            }

            plusCartItems()

            true
        } else {
            Task.runSafely {
                dao.delete(item)
            }
            minusCartItems()
            false
        }
    }

    fun insertOrDeleteEquipment(
        dao: EquipmentDao,
        item: Equipments.Equipment,
        isInsert: Boolean
    ): Boolean {
        debug("inserted item $item")
        return if (isInsert) {
            Task.runSafely {
                if (item.orderQuantity == 0) {
                    item.orderQuantity = 1
                }
                dao.insertEquipment(item)
            }
            plusCartItems()

            true
        } else {
            Task.runSafely {
                dao.delete(item)
            }
            minusCartItems()
            false
        }
    }

    fun insertOrDeleteLabTest(dao: LabTestDao, item: Tests.Test, isInsert: Boolean): Boolean {
        return if (isInsert) {
            Task.runSafely {
                if (item.orderQuantity == 0) {
                    item.orderQuantity = 1
                }
                dao.insertTest(item)
            }
            plusCartItems()

            true
        } else {
            Task.runSafely {
                dao.delete(item)
            }
            minusCartItems()
            false
        }
    }

    private fun plusCartItems() {
        cartItemsCount.value = cartItemsCount.value?.plus(1)
    }

    private fun minusCartItems() {
        cartItemsCount.value = cartItemsCount.value?.minus(1)
    }

    fun isInCart(
        labTestDao: LabTestDao? = null,
        medicineDao: MedicineDao? = null,
        equipmentDao: EquipmentDao? = null,
        item: BaseModel?
    ): Boolean {
        var isInCart = false
        try {
            if (item is Equipments.Equipment) {
                val result = equipmentDao?.isInCart(id = item.id)
                isInCart = result?.id!! > 0
            }
            if (item is Medicines.Product) {
                val result = medicineDao?.isInCart(id = item.id, medicineName = item.medicineName)
                isInCart = result?.id!! > 0
            }
            if (item is Tests.Test) {
                val result = labTestDao?.isInCart(id = item.id)
                isInCart = result?.id!! > 0
            }
        } catch (e: Exception) {
            isInCart = false
        }

        return isInCart
    }

    fun emptyCart(labTestDao: LabTestDao, medicineDao: MedicineDao, equipmentDao: EquipmentDao) {
        Task.runSafely({
            labTestDao.empty()
            medicineDao.empty()
            equipmentDao.empty()
        }, {
            totalOrderPrice.value = 0.0
            BaseViewModel.totalOrderPriceIncludesDelivery.value = 0.0
            discountedOrderPrice.value = 0.0
            discountPercentage.value = 0.0
            cartItemsCount.value = 0
            debug("done")
        }, true
        )
    }
}
package com.Doctoor.app.ui.modules.cart

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseListDataViewModel
import com.Doctoor.app.data.database.CartManager
import com.Doctoor.app.data.database.EquipmentDao
import com.Doctoor.app.data.database.LabTestDao
import com.Doctoor.app.data.database.MedicineDao
import com.Doctoor.app.model.response.BaseModel
import com.Doctoor.app.model.response.Equipments
import com.Doctoor.app.model.response.Medicines
import com.Doctoor.app.model.response.Tests
import com.Doctoor.app.rx.Task
import com.Doctoor.app.widget.QuantityView
import javax.inject.Inject


class CartItemVM @Inject constructor(
    private var equipmentDao: EquipmentDao,
    private var medicineDao: MedicineDao,
    private var labTestDao: LabTestDao,
    private var cartManager: CartManager,
    private var adapter: CartFragment.Adapter
) : BaseListDataViewModel<BaseModel>(), QuantityView.OnQuantityChangeListenerWithData {
    override fun onLimitReached() {
    }

    override fun onQuantityChanged(
        oldQuantity: Int,
        newQuantity: Int,
        programmatically: Boolean,
        item: BaseModel
    ) {
        orderQuantity.value = newQuantity

        updateQuantity(item, newQuantity)
    }

    /**
     * it will update the quantity if the item in the database
     * it will update the price of the item based in
     * @param newQuantity
     * */
    private fun updateQuantity(item: BaseModel, newQuantity: Int) {

        if (item is Equipments.Equipment) {
            updateCartPrice(item.price, 1, newQuantity > item.orderQuantity)
        }

        if (item is Medicines.Product) {
            updateCartPrice(item.price, 1, newQuantity > item.orderQuantity)
        }

        if (item is Tests.Test) {
            updateCartPrice(item.price, 1, newQuantity > item.orderQuantity)
        }

        Task.runSafely {

            if (item is Equipments.Equipment) {
                item.orderQuantity = newQuantity
                equipmentDao.update(item)
//                price.value = item.price.times(item.orderQuantity).toString()
                setItem(item)
            }
            if (item is Medicines.Product) {
                item.orderQuantity = newQuantity
                medicineDao.update(item)
//                price.value = item.price.times(item.orderQuantity).toString()
                setItem(item)

            }
            if (item is Tests.Test) {
                item.orderQuantity = newQuantity
                labTestDao.update(item)
//                price.value = item.price.times(item.orderQuantity).toString()
                setItem(item)
            }
        }

        notifyChange()
        adapter.notifyDataSetChanged()
    }

    private lateinit var cartItem: BaseModel

    var title = MutableLiveData<String>()
    var description = MutableLiveData<String>()
    var url = MutableLiveData<String>()
    var brand = MutableLiveData<String>()
    var price = MutableLiveData<Double>()
    var unit = MutableLiveData<String>()
    var orderQuantity = MutableLiveData<Int>()

    var isMedicine = MutableLiveData<Boolean>().apply { value = false }
    var isLabTest = MutableLiveData<Boolean>().apply { value = false }
    var isEquipment = MutableLiveData<Boolean>().apply { value = false }

    override fun setItem(item: BaseModel) {
        this.cartItem = item

        if (item is Equipments.Equipment) {
            isEquipment.value = true
            isMedicine.value = false
            isLabTest.value = false

            title.value = item.equipment_name?.capitalize()
            brand.value = item.brand
            url.value = item.image
            price.value = item.price.times(item.orderQuantity)
            orderQuantity.value = item.orderQuantity
        }
        if (item is Medicines.Product) {
            isEquipment.value = false
            isMedicine.value = true
            isLabTest.value = false

            title.value = item.medicineName
            url.value = item.medicineImage
            price.value = item.price.times(item.orderQuantity)
            unit.value = item.unit
            orderQuantity.value = item.orderQuantity
        }

        if (item is Tests.Test) {
            isEquipment.value = false
            isMedicine.value = false
            isLabTest.value = true

            title.value = item.test_name
            description.value = item.description
            url.value = item.logo
            price.value = item.price.times(item.orderQuantity)
            orderQuantity.value = item.orderQuantity
        }

        notifyChange()
    }

    override fun getItem() = cartItem

    override fun layoutRes() = R.layout.item_cart
    override fun onItemClick(v: View, item: BaseModel) {
    }

    fun onCrossClick(item: BaseModel) {

        adapter.onItemRemoved(item)

        if (item is Equipments.Equipment) {
            isEquipment.value = false
            updateCartPrice(item.price, item.orderQuantity, false)
        }

        if (item is Medicines.Product) {
            isMedicine.value = false
            updateCartPrice(item.price, item.orderQuantity, false)
        }

        if (item is Tests.Test) {
            isLabTest.value = false
            updateCartPrice(item.price, item.orderQuantity, false)
        }


        if (item is Equipments.Equipment) {
            cartManager.insertOrDeleteEquipment(equipmentDao, item, false)
        }
        if (item is Medicines.Product) {
            cartManager.insertOrDeleteMedicine(medicineDao, item, false)
        }
        if (item is Tests.Test) {
            cartManager.insertOrDeleteLabTest(labTestDao, item, false)
        }

        adapter.fragment.getViewModel().allItems.remove(item)
        notifyChange()

        //// on remove an item from cart. update cart prices and all validations like prescription required option
        adapter.fragment.getViewModel().setCartPrices()

    }

}
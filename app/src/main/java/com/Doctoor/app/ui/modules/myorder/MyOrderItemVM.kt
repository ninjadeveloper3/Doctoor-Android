package com.Doctoor.app.ui.modules.myorder

import android.view.View
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseListDataViewModel
import com.Doctoor.app.data.database.CartManager
import com.Doctoor.app.data.database.EquipmentDao
import com.Doctoor.app.data.database.LabTestDao
import com.Doctoor.app.data.database.MedicineDao
import com.Doctoor.app.model.response.MedicalServices
import com.Doctoor.app.rx.Task
import com.Doctoor.app.ui.modules.cart.CartFragment
import javax.inject.Inject

/*Accept bundle from Adapter to assure the item is related to cart order or service*/
class MyOrderItemVM @Inject constructor(
    private var equipmentDao: EquipmentDao,
    private var medicineDao: MedicineDao,
    private var labTestDao: LabTestDao,
    private var cartManager: CartManager
) : BaseListDataViewModel<MedicalServices.MyOrder>() {
    private lateinit var simple: MedicalServices.MyOrder
    lateinit var paymentType: String

    override fun setItem(item: MedicalServices.MyOrder) {
        this.simple = item

        paymentType = PaymentTypeEnum.getPaymentType(item.paymentMethodId).paymentType

        notifyChange()
    }

    override fun getItem() = simple

    override fun layoutRes() = R.layout.item_my_order
    override fun onItemClick(v: View, item: MedicalServices.MyOrder) {
    }

    private fun _reOrderProduct(item: MedicalServices.MyOrder) {
        for (order in item.orderDetails) {

            if (order.productType == "Equipments") {
                Task.runSafely({
                    isInCart =
                        cartManager.isInCart(equipmentDao = equipmentDao, item = order.equipments)
                }, {
                    if (!isInCart) {
                        order.equipments?.let {
                            it.orderQuantity = order.quantity
                            cartManager.insertOrDeleteEquipment(equipmentDao, it, true)
                        }

                    }

                }, true)

            } else if (order.productType == "Medicines") {

                Task.runSafely({
                    isInCart =
                        cartManager.isInCart(medicineDao = medicineDao, item = order.medicines)
                }, {
                    if (!isInCart) {
                        order.medicines?.let {
                            it.orderQuantity = order.quantity
                            cartManager.insertOrDeleteMedicine(medicineDao, it, true)
                        }

                    }

                }, true)

            } else if (order.productType == "OtherMedicines") {

                Task.runSafely({
                    isInCart =
                        cartManager.isInCart(medicineDao = medicineDao, item = order.otherMedicines)
                }, {
                    if (!isInCart) {
                        order.otherMedicines?.let {
                            it.orderQuantity = order.quantity
                            cartManager.insertOrDeleteMedicine(medicineDao, it, true)
                        }

                    }

                }, true)

            } else {

                Task.runSafely({
                    isInCart = cartManager.isInCart(labTestDao = labTestDao, item = order.labsTest)
                }, {
                    order.labsTest?.let {
                        it.orderQuantity = order.quantity
                        cartManager.insertOrDeleteLabTest(labTestDao, it, true)
                    }

                }, true)

            }
        }
        navigatorHelper?.startFragmentWithToolbar<CartFragment>(
            CartFragment::class.java.name
        )
    }

    fun onReorderClick() {
        val item = getItem()
        _reOrderProduct(item)

    }
}
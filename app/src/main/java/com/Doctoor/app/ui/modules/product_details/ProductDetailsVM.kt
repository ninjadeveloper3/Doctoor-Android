package com.Doctoor.app.ui.modules.product_details

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.Doctoor.app.base.BaseViewModel
import com.Doctoor.app.data.database.CartManager
import com.Doctoor.app.data.database.EquipmentDao
import com.Doctoor.app.model.response.Equipments
import com.Doctoor.app.rx.Task
import com.Doctoor.app.utils.Constants.DATA
import com.Doctoor.app.widget.QuantityView
import javax.inject.Inject

class ProductDetailsVM @Inject constructor(
    private var dao: EquipmentDao,
    private var cartManager: CartManager
) : BaseViewModel(), QuantityView.OnQuantityChangeListener {
    override fun onLimitReached() {
    }

    override fun onQuantityChanged(oldQuantity: Int, newQuantity: Int, programmatically: Boolean) {
        product?.orderQuantity = newQuantity

        updateQuantity()
    }

    var product: Equipments.Equipment? = null
    var totalPrice: MutableLiveData<Double> = MutableLiveData()

    override fun onFirsTimeUiCreate(bundle: Bundle?) {
        this.bundle = bundle
        populateDetails()
    }

    fun populateDetails() {
        product = bundle?.getParcelable(DATA)
        totalPrice.value = product?.price

        Task.runSafely {
            isInCart = cartManager.isInCart(equipmentDao = dao, item = product)
            /*
            * show quantity of the item if is exist in the cart
            * */

            if (isInCart) product = dao.isInCart(product?.id!!)
        }

        Task.run(
            {
                isInCart = cartManager.isInCart(equipmentDao = dao, item = product)
                if (isInCart) product = dao.isInCart(product?.id!!)
            },
            {
                if (isInCart) {
                    totalPrice.value = getCalculatedPrice(product!!.price, product?.orderQuantity)
                } else {
                    product?.orderQuantity = 1
                }
                notifyChange()
            }, true
        )
    }

    fun onCartClick(item: Equipments.Equipment) {
        isInCart = cartManager.insertOrDeleteEquipment(dao, item, !isInCart)
        notifyChange()
    }

    private fun updateQuantity() {
        totalPrice.value = getCalculatedPrice(product!!.price, product?.orderQuantity)

        if (isInCart) {
            Task.runSafely {
                dao.update(product!!)
            }
            notifyChange()
        }
    }

    public fun continueShopping() =
        navigatorHelper?.navigateMainActivity(true)
}
package com.Doctoor.app.ui.modules.medicine.details

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.Doctoor.app.base.BaseViewModel
import com.Doctoor.app.data.database.CartManager
import com.Doctoor.app.data.database.MedicineDao
import com.Doctoor.app.model.response.Medicines
import com.Doctoor.app.rx.Task
import com.Doctoor.app.ui.modules.cart.CartFragment
import com.Doctoor.app.utils.Constants.DATA
import com.Doctoor.app.widget.QuantityView
import javax.inject.Inject

class MedicineDetailsVM @Inject constructor(
    private var dao: MedicineDao,
    private var cartManager: CartManager
) : BaseViewModel(), QuantityView.OnQuantityChangeListener {
    var showIndications: Boolean = true
    var showContradictions: Boolean = true
    var totalPrice: MutableLiveData<Double> = MutableLiveData()
    override fun onLimitReached() {
    }

    override fun onQuantityChanged(oldQuantity: Int, newQuantity: Int, programmatically: Boolean) {
        product?.orderQuantity = newQuantity

        updateQuantity()
    }

    var product: Medicines.Product? = null

    override fun onFirsTimeUiCreate(bundle: Bundle?) {
        this.bundle = bundle
//        populateDetails()
    }

    fun populateDetails() {
        product = bundle?.getParcelable(DATA)
        totalPrice.value = product?.price

        if (product?.warnings === null || product?.warnings!!.size == 0) {
            showContradictions = false
        }
        if (product?.primaryUse === null || product?.primaryUse!!.isEmpty()) {
            showIndications = false
        }

        Task.run(
            {
                isInCart = cartManager.isInCart(medicineDao = dao, item = product)
                if (isInCart) {
                    product = dao.isInCart(product?.id!!, medicineName = product!!.medicineName)
                }

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


    fun onCartClick(item: Medicines.Product) {

        isInCart = cartManager.insertOrDeleteMedicine(dao, item, !isInCart)
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


    public fun checkOut() =
        navigatorHelper?.startFragmentWithToolbar<CartFragment>(
            CartFragment::class.java.name
        )

    public fun continueShopping() =
        navigatorHelper?.navigateMainActivity(true)

}
package com.Doctoor.app.ui.modules.test_details

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.Doctoor.app.base.BaseViewModel
import com.Doctoor.app.data.database.CartManager
import com.Doctoor.app.data.database.LabTestDao
import com.Doctoor.app.model.response.Tests
import com.Doctoor.app.rx.Task
import com.Doctoor.app.utils.Constants.DATA
import com.Doctoor.app.widget.QuantityView
import javax.inject.Inject

class TestDetailsVM @Inject constructor(
    private var dao: LabTestDao,
    private var cartManager: CartManager
) : BaseViewModel(), QuantityView.OnQuantityChangeListener {
    override fun onLimitReached() {
    }

    override fun onQuantityChanged(oldQuantity: Int, newQuantity: Int, programmatically: Boolean) {
        test?.orderQuantity = newQuantity

        updateQuantity()
    }

    var test: Tests.Test? = null

    var totalPrice: MutableLiveData<Double> = MutableLiveData()

    override fun onFirsTimeUiCreate(bundle: Bundle?) {
        this.bundle = bundle
    }

    fun populateDetails() {
        test = bundle?.getParcelable(DATA)
        totalPrice.value = test?.price

        Task.run(
            {
                isInCart = cartManager.isInCart(labTestDao = dao, item = test)
                if (isInCart) test = dao.isInCart(test?.id!!)

            },
            {
                if (isInCart) {
                    totalPrice.value = getCalculatedPrice(test!!.price, test?.orderQuantity)
                } else {
                    test?.orderQuantity = 1
                }
                notifyChange()
            }, true
        )
    }

    private fun updateQuantity() {

        totalPrice.value = getCalculatedPrice(test!!.price, test?.orderQuantity)

        if (isInCart) {
            Task.runSafely {
                dao.update(test!!)
            }
            notifyChange()
        }
    }

    fun onCartClick(item: Tests.Test) {
        isInCart = cartManager.insertOrDeleteLabTest(dao, item, !isInCart)
        notifyChange()
    }

    public fun continueShopping() =
        navigatorHelper?.navigateMainActivity(true)


}
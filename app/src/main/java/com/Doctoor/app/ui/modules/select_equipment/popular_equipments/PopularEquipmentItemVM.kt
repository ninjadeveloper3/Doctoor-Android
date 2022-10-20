package com.Doctoor.app.ui.modules.select_equipment.popular_equipments

import android.os.Bundle
import android.view.View
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseListDataViewModel
import com.Doctoor.app.data.database.CartManager
import com.Doctoor.app.data.database.EquipmentDao
import com.Doctoor.app.model.response.Equipments
import com.Doctoor.app.rx.Task
import com.Doctoor.app.ui.modules.product_details.ProductDetailsFragment
import com.Doctoor.app.utils.Constants.DATA

class PopularEquipmentItemVM(private var dao: EquipmentDao, private var cartManager: CartManager) :
    BaseListDataViewModel<Equipments.Equipment>() {

    private lateinit var item: Equipments.Equipment

    override fun setItem(item: Equipments.Equipment) {
        this.item = item
        Task.runSafely {
            isInCart = cartManager.isInCart(equipmentDao = dao, item = item)
            notifyChange()
        }
    }

    override fun getItem() = item

    override fun layoutRes() = R.layout.item_popular_equipment

    override fun onItemClick(v: View, item: Equipments.Equipment) {
        val bundle: Bundle = Bundle()
        bundle.putParcelable(DATA, item)
        navigatorHelper?.startFragmentWithBottomNavigation<ProductDetailsFragment>(
            ProductDetailsFragment::class.java.name,
            bundle = bundle
        )
    }

    fun onCartClick(equipment: Equipments.Equipment) {
        isInCart = cartManager.insertOrDeleteEquipment(dao, equipment, !isInCart)
        notifyChange()
    }
}
package com.Doctoor.app.ui.modules.cart

import android.app.Activity
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.Doctoor.app.BR
import com.Doctoor.app.DoctoorApp.Companion.color
import com.Doctoor.app.R
import com.Doctoor.app.base.BaseViewModel.Companion.discountPercentage
import com.Doctoor.app.base.BaseViewModel.Companion.discountedOrderPrice
import com.Doctoor.app.base.BaseViewModel.Companion.totalOrderPrice
import com.Doctoor.app.base.BaseViewModel.Companion.totalOrderPriceIncludesDelivery
import com.Doctoor.app.base.BaseViewModelFragment
import com.Doctoor.app.data.database.CartManager
import com.Doctoor.app.data.database.EquipmentDao
import com.Doctoor.app.data.database.LabTestDao
import com.Doctoor.app.data.database.MedicineDao
import com.Doctoor.app.databinding.FragmentConfirmSelectionMedicineBinding
import com.Doctoor.app.di.ViewModelInjectionField
import com.Doctoor.app.di.qualifiers.ViewModelInjection
import com.Doctoor.app.model.response.BaseModel
import com.Doctoor.app.navigation.NavigatorHelper
import com.Doctoor.app.preference.DataStoreManager
import com.Doctoor.app.ui.adapters.BaseRVAdapter
import com.Doctoor.app.ui.adapters.BaseViewHolder
import com.Doctoor.app.utils.AlertUtils
import com.Doctoor.app.utils.Constants.ID
import com.Doctoor.app.utils.Constants.IS_UPLOADED
import com.Doctoor.app.utils.debug
import com.Doctoor.app.utils.validation.Validator
import kotlinx.android.synthetic.main.fragment_confirm_selection_medicine.*
import kotlinx.android.synthetic.main.fragment_main.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import javax.inject.Inject
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class CartFragment :
    BaseViewModelFragment<FragmentConfirmSelectionMedicineBinding, CartFragmentVM>() {

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<CartFragmentVM>

    @Inject
    lateinit var adapter: Adapter

    @Inject
    lateinit var dataStoreManager: DataStoreManager

    override fun layoutRes() = R.layout.fragment_confirm_selection_medicine

    companion object {
        fun newInstance(): CartFragment {
            return CartFragment()
        }
    }

    @Inject

    override fun getToolBarTile() = getString(R.string.confirm_selection)

    override fun toolbarColor() = 0

    override fun getViewModel() = viewModel.get();

    override fun getBindingVariable() = BR.cartFragmentVM

    override fun onStart() {
        super.onStart()
        totalOrderPrice.value = 0.0
        totalOrderPriceIncludesDelivery.value = 0.0
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getViewModel().validator = Validator(getViewDataBinding())

        getViewModel().adapter.set(adapter)

        refreshLayout?.setColorSchemeResources(
            R.color.sky_blue, R.color.blue,
            R.color.light_pink, R.color.pink
        )
        refreshLayout?.setOnRefreshListener {
            getmViewModel().fetchCartItems()
        }


        cartRecyclerView.addItemDecoration(spacesItemDecoration())

        getmViewModel().fetchCartItems()

        setObservers()

        val fm = fragmentManager

        for (entry in 0 until fm?.backStackEntryCount!!) {
            debug("Found fragment: " + fm.getBackStackEntryAt(entry).id)
        }

        getViewModel().onCouponApply.observe(this, Observer { message ->
            AlertUtils.showAlertDialog(getBaseActivity(), message)
        })
        representativeFab.setOnClickListener {
            openDialer()
        }
    }

    private fun setObservers() {
        totalOrderPrice.observe(this, Observer<Double> { value ->
            debug("updated Value $value")


            if (value > 0.0) {

                getViewModel().showDeliveryCharges.value = value < 3000
                if (getViewModel().showDeliveryCharges.value!!) {
                    totalOrderPriceIncludesDelivery.value = totalOrderPrice.value?.plus(200)
                } else {
                    totalOrderPriceIncludesDelivery.value = totalOrderPrice.value
                }

                if (discountPercentage.value!! > 0) {
                    applyDiscount()
                } else {
                    removeDiscount()
                }
            } else {
                totalOrderPriceIncludesDelivery.value = 0.0
                getViewModel().showDeliveryCharges.value = false

                removeDiscount()
            }
        })

        discountPercentage.observe(this, Observer<Double> { value ->
            //            total_price.text = ((Html.fromHtml(totalOrderPrice.value.toString())))
            if (value > 0) {
                applyDiscount()
            } else {
                removeDiscount()
            }
        })
    }

    private fun TextView.showStrikeThrough(show: Boolean) {
        paintFlags =
            if (show) {
                paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
    }

    private fun applyDiscount() {
        dataStoreManager.setDiscount(discountPercentage.value!!)
        val discount =
            totalOrderPrice.value?.times(discountPercentage.value!!)?.div(100)
        debug("discount price  $discount")

        discountedOrderPrice.value = totalOrderPrice.value?.minus(discount!!)

        total_price.showStrikeThrough(true)
        total_price.setTextColor(color(R.color.grey))
        /// round price

        totalOrderPriceIncludesDelivery.value =
            Math.round(totalOrderPriceIncludesDelivery.value!! * 100.0) / 100.0

        total_price.text = totalOrderPriceIncludesDelivery.value.toString()

        if (totalOrderPrice.value!! < 3000.0) {
            discountedOrderPrice.value = discountedOrderPrice.value?.plus(200)
        } else {
            discountedOrderPrice.value = discountedOrderPrice.value
        }
        /// round price

        discountedOrderPrice.value = Math.round(discountedOrderPrice.value!! * 100.0) / 100.0

        discounted_price.visibility = View.VISIBLE
        discounted_price.text = discountedOrderPrice.value.toString()
    }

    private fun removeDiscount() {
        dataStoreManager.setDiscount(0.0)
        discountedOrderPrice.value = 0.0
        /// round price
        totalOrderPriceIncludesDelivery.value =
            Math.round(totalOrderPriceIncludesDelivery.value!! * 100.0) / 100.0

        total_price.showStrikeThrough(false)
        total_price.setTextColor(color(R.color.design_default_color_primary))
        total_price.text = (totalOrderPriceIncludesDelivery.value.toString())
        discounted_price.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        setObservers()
        if (getmViewModel().allItems.size > 0)
            getmViewModel().setCartPrices()
        KeyboardVisibilityEvent.setEventListener(
            getBaseActivity()
        ) {
            if (it) {
                shoppingLayout.visibility = View.GONE
                subTotalLayout.visibility = View.GONE

            } else {
                shoppingLayout.visibility = View.VISIBLE
                subTotalLayout.visibility = View.VISIBLE
            }
        }
    }

    class Adapter(
        mValue: MutableList<BaseModel>,
        var equipmentDao: EquipmentDao,
        var medicineDao: MedicineDao,
        var labTestDao: LabTestDao,
        var cartManager: CartManager,
        mNavidation: NavigatorHelper,
        var fragment: CartFragment
    ) :
        BaseRVAdapter<BaseModel, CartItemVM, Adapter.ViewHolder>(mValue, mNavidation) {

        override fun getLayoutId(viewType: Int) = getViewModel().layoutRes()
        override fun getViewHolder(
            view: View,
            viewModel: CartItemVM,
            mDataBinding: ViewDataBinding, viewType: Int
        ): ViewHolder {
            val kotlinClass: KClass<ViewHolder> = ViewHolder::class
            val ctor = kotlinClass.primaryConstructor
            val myObject = ctor?.call(view, viewModel, mDataBinding) as ViewHolder
            return myObject
        }

        override fun getViewModel() =
            CartItemVM(equipmentDao, medicineDao, labTestDao, cartManager, this)

        override fun getVariableId() = BR.cartItemVM

        class ViewHolder(view: View, viewModel: CartItemVM, mDataBinding: ViewDataBinding) :
            BaseViewHolder<BaseModel, CartItemVM>(view, viewModel, mDataBinding)

        fun onItemRemoved(item: BaseModel) {
            remove(item)
            notifyItemRangeRemoved(0, itemCount)
        }
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (data?.getBooleanExtra(IS_UPLOADED, false)!!) {
                var id = data.getIntExtra(ID, 0)
                getViewModel().onShipping(prescriptionId = id)
            }
        }
    }*/
}
package com.Doctoor.app.model.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.Doctoor.app.BuildConfig
import com.Doctoor.app.BuildConfig.ACCOUNT_NUMBER
import com.Doctoor.app.BuildConfig.STORE_ID
import com.Doctoor.app.model.response.BaseModel
import com.Doctoor.app.model.response.BaseResponse
import kotlinx.android.parcel.Parcelize

object ServiceRequest {
    data class OrderService(
        @SerializedName("service_id")
        var serviceId: Int,
        @SerializedName("city_id")
        var cityId: Int,
        @SerializedName("comment")
        var comment: String
    ) : BaseModel

    data class RentalEquipment(
        @SerializedName("equipment_id")
        var equipmentId: Int,
        @SerializedName("comment")
        var comment: String,
        @SerializedName("city_id")
        var cityId: Int
    ) : BaseModel

    @Parcelize
    data class Order(
        @SerializedName("billing")
        var billing: Billing,

        @SerializedName("order")
        var order: ArrayList<OrderX>,

        @SerializedName("payment_method_id")
        var paymentMethodId: String,

        @SerializedName("shipping")
        var shipping: Shipping,

        @SerializedName("total_amount")
        var totalAmount: Double,

        @SerializedName("prescription_id")
        var prescriptionId: Int = 0

    ) : BaseModel, Parcelable

    @Parcelize
    data class Billing(
        @SerializedName("city")
        var city: String,

        @SerializedName("first_name")
        var firstName: String,

        @SerializedName("home_address")
        var homeAddress: String,

        @SerializedName("last_name")
        var lastName: String,

        @SerializedName("phone")
        var phone: String,

        @SerializedName("province")
        var province: String
    ) : BaseModel, Parcelable

    @Parcelize
    data class OrderX(
        @SerializedName("product_id")
        var productId: Int,

        @SerializedName("product_type")
        var productType: String,

        @SerializedName("quantity")
        var quantity: Int
    ) : BaseModel, Parcelable

    @Parcelize
    data class Shipping(

        @SerializedName("city")
        var city: String,

        @SerializedName("email")
        var email: String,

        @SerializedName("first_name")
        var firstName: String,

        @SerializedName("home_address")
        var homeAddress: String,

        @SerializedName("last_name")
        var lastName: String,

        @SerializedName("phone")
        var phone: String,

        @SerializedName("province")
        var province: String,

        @SerializedName("is_sameAddress")
        var isSameAddress: Boolean
    ) : BaseModel, Parcelable

    @Parcelize
    data class ShippingBillingDetails(
        @SerializedName("data")
        var data: ShippingBilling
    ) : BaseResponse(), BaseModel

    @Parcelize
    data class ShippingBilling(
        @SerializedName("city")
        val city: String,
        @SerializedName("email")
        val email: String,
        @SerializedName("first_name")
        val firstName: String,
        @SerializedName("home_address")
        val homeAddress: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("last_name")
        val lastName: String,
        @SerializedName("phone")
        val phone: String,
        @SerializedName("province")
        val province: String
    ) : BaseModel, BaseResponse()

    @Parcelize
    data class ConfirmOrder(
        @SerializedName("order_number")
        var orderNumber: String,
        @SerializedName("status")
        var status: Int,
        @SerializedName("rental")
        var isRental: Int = 0,
        @SerializedName("service")
        var isService: Int = 0,
        @SerializedName("payment_method_id")
        var paymentMethodId: Int = 0
    ) : BaseModel, Parcelable


    @Parcelize
    data class ConfirmEasyPaisaCC(
        @SerializedName("orderId")
        var orderId: String,
        @SerializedName("storeId")
        var storeId: Int = STORE_ID,
        @SerializedName("accountNum")
        var accountNum: Int = ACCOUNT_NUMBER
    ) : BaseModel, Parcelable

    @Parcelize
    data class EasyPaisaPayment(
        @SerializedName("orderId")
        var orderId: String,
        @SerializedName("storeId")
        var storeId: Int = BuildConfig.STORE_ID,
        @SerializedName("transactionAmount")
        var transactionAmount: Double,
        @SerializedName("transactionType")
        var transactionType: String = "MA",
        @SerializedName("mobileAccountNo")
        var mobileAccountNo: String,
        @SerializedName("emailAddress")
        var emailAddress: String
    ) : BaseModel, Parcelable

}
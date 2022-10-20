package com.Doctoor.app.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


class MedicalServices {
    @Parcelize
    data class Service(
        @SerializedName("id")
        var id: Int, // 6
        @SerializedName("service_id")
        var serviceId: Int, // 6
        @SerializedName("service_name")
        var serviceName: String // chef
    ) : BaseModel, Parcelable

    data class OrderService(
        @SerializedName("data")
        var data: ServiceData
    ) : BaseResponse() {
        data class ServiceData(
            @SerializedName("comments")
            var comments: String,
            @SerializedName("created_at")
            var createdAt: String,
            @SerializedName("id")
            var id: Int,
            @SerializedName("order_from_user")
            var orderFromUser: Int,
            @SerializedName("service_id")
            var serviceId: String,
            @SerializedName("status")
            var status: Int,
            @SerializedName("updated_at")
            var updatedAt: String
        ) : BaseModel
    }

    data class RentalEquipmentResponse(
        @SerializedName("data")
        var data: RentalEquipment
    ) : BaseResponse() {
        data class RentalEquipment(
            @SerializedName("comment")
            val comment: String,
            @SerializedName("created_at")
            val createdAt: String,
            @SerializedName("equipment_id")
            val equipmentId: String,
            @SerializedName("id")
            val id: Int,
            @SerializedName("order_number")
            val orderNumber: String,
            @SerializedName("status")
            val status: Int,
            @SerializedName("updated_at")
            val updatedAt: String,
            @SerializedName("user_id")
            val userId: Int
        ) : BaseModel
    }


    data class CouponCodeResponse(
        @SerializedName("data")
        var data: CouponCodeData
    ) : BaseResponse() {
        data class CouponCodeData(
            @SerializedName("percentage")
            var percentage: Double
        ) : BaseModel
    }

    data class OrderResponse(
        @SerializedName("data")
        val data: Data
    ) : BaseModel, BaseResponse()


    data class Data(
        @SerializedName("billing_id")
        val billingId: Int,

        @SerializedName("created_at")
        val createdAt: String,

        @SerializedName("delivery_type_id")
        val deliveryTypeId: Int,

        @SerializedName("id")
        val id: Int,

        @SerializedName("order_number")
        val orderNumber: String,

        @SerializedName("payment_method_id")
        val paymentMethodId: Int,

        @SerializedName("shipping_id")
        val shippingId: Int,

        @SerializedName("status")
        val status: Int,

        @SerializedName("user_id")
        val userId: Int
    ) : BaseModel

    data class OrderHistory(

        @SerializedName("total_amount")
        val totalAmount: String,

        @SerializedName("id")
        val id: Int,

        @SerializedName("order_number")
        val orderNumber: String,

        @SerializedName("product_id")
        val productId: Int,

        @SerializedName("product_type")
        val productType: String,

        @SerializedName("quantity")
        val quantity: Int,

        @SerializedName("status")
        val status: Int
    ) : BaseModel

    data class MyOrder(
        @SerializedName("id")
        val id: Int,
        @SerializedName("order_details")
        val orderDetails: ArrayList<OrderDetail>,
        @SerializedName("order_number")
        val orderNumber: String,
        @SerializedName("comments")
        val comments: String,
        @SerializedName("total_amount")
        val totalAmount: Double = 0.0,
        @SerializedName("service_id")
        val serviceId: Int,
        @SerializedName("city_id")
        val cityId: Int,
        @SerializedName("payment_status")
        val paymentStatus: Int,
        @SerializedName("payment_method_id")
        val paymentMethodId: Int? = 0,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("status")
        val status: Int
    ) : BaseModel

    data class RentalHistory(
        @SerializedName("by_admin")
        val byAdmin: Any,
        @SerializedName("comment")
        val comment: String,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("equipment_id")
        val equipmentId: Int,
        @SerializedName("equipment_name")
        val equipmentName: String,
        @SerializedName("equipments")
        val equipments: Equipments.Equipment,
        @SerializedName("id")
        val id: Int,
        @SerializedName("order_number")
        val orderNumber: String,
        @SerializedName("payment_method_id")
        val paymentMethodId: Int?,
        @SerializedName("status")
        val status: Int,
        @SerializedName("updated_at")
        val updatedAt: String,
        @SerializedName("user_id")
        val userId: Int,
        @SerializedName("price")
        val totalAmount: Double = 0.0,
        @SerializedName("city_id")
        val cityId: Int
    ) : BaseModel

    data class OrderDetail(
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("equipments")
        val equipments: Equipments.Equipment?,
        @SerializedName("id")
        val id: Int,
        @SerializedName("labs_test")
        val labsTest: Tests.Test?,
        @SerializedName("medicines")
        val medicines: Medicines.Product?,
        @SerializedName("other_medicines")
        val otherMedicines: Medicines.Product?,
        @SerializedName("order_number")
        val orderNumber: String,
        @SerializedName("product_id")
        val productId: Int,
        @SerializedName("product_type")
        val productType: String,
        @SerializedName("quantity")
        val quantity: Int,
        @SerializedName("status")
        val status: Int
    ) : BaseModel

    data class PaymentResponse(
        @SerializedName("data")
        var data: String
    ) : BaseResponse()

    data class EasyPaisaResponseResponse(
        @SerializedName("data")
        var data: EasyPaisaResponse?
    ) : BaseResponse()

    data class EasyPaisaResponse(
        @SerializedName("orderId")
        val orderId: String,
        @SerializedName("responseCode")
        val responseCode: String?,
        @SerializedName("responseDesc")
        val responseDesc: String,
        @SerializedName("storeId")
        val storeId: String,
        @SerializedName("transactionDateTime")
        val transactionDateTime: String,
        @SerializedName("transactionId")
        val transactionId: String
    )

    data class EasyPaisaCCPResponse(
        @SerializedName("accountNum")
        val accountNum: String,
        @SerializedName("msisdn")
        val msisdn: String,
        @SerializedName("orderId")
        val orderId: String,
        @SerializedName("paymentMode")
        val paymentMode: String,
        @SerializedName("responseCode")
        val responseCode: String,
        @SerializedName("responseDesc")
        val responseDesc: String,
        @SerializedName("storeId")
        val storeId: Int,
        @SerializedName("storeName")
        val storeName: String,
        @SerializedName("transactionAmount")
        val transactionAmount: Double,
        @SerializedName("transactionDateTime")
        val transactionDateTime: String,
        @SerializedName("transactionStatus")
        val transactionStatus: String
    )
}

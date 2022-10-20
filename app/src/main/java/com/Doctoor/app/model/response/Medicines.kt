package com.Doctoor.app.model.response

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.Doctoor.app.data.database.MEDICINES
import kotlinx.android.parcel.Parcelize


class Medicines {

    @Parcelize
    @Entity(tableName = MEDICINES)
    data class Product(
        @SerializedName("composition")
        val composition: String?, // HCL
        @SerializedName("how_to_take")
        val howToTake: String?, // with water
        @PrimaryKey
        @SerializedName("id")
        val id: Int, // 2
        @SerializedName("is_prescription_req")
        val isPrescriptionReq: Int, // 1
        @SerializedName("unit")
        val unit: String? = "", // null
        @SerializedName("medicine_image")
        val medicineImage: String? = "medicines_images/2.png", // null
        @SerializedName("medicine_name")
        val medicineName: String, // surbax
        @SerializedName("price")
        val price: Double = 0.0, // 5
        @SerializedName("primary_use")
        val primaryUse: String?, // Very nice
        @ColumnInfo(name = "order_quantity", collate = 1) var orderQuantity: Int,
        @SerializedName("warnings")
        val warnings: ArrayList<Warning>?
    ) : BaseModel, Parcelable

    @Parcelize
    data class Warning(
        @SerializedName("detail")
        val detail: String? = "", // detail
        @SerializedName("id")
        val id: Int = 0, // 2
        @SerializedName("medicine_id")
        val medicineId: Int = 0, // 2
        @SerializedName("title")
        val title: String? = "" // title
    ) : BaseModel, Parcelable

    @Parcelize
    data class Category(
        @SerializedName("category_name")
        val categoryName: String, // Sedative
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("id")
        val id: Int, // 1
        @SerializedName("image")
        val image: String, // 123.png
        @SerializedName("updated_at")
        val updatedAt: String,
        @Transient val iconRes: Int
    ) : BaseModel, Parcelable

    @Parcelize
    data class Notification(
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("is_paid")
        val isPaid: Int,
        @SerializedName("note_body")
        val noteBody: String,
        @SerializedName("note_decsription")
        val noteDescription: String,
        @SerializedName("note_heading")
        val noteHeading: String,
        @SerializedName("total_amount")
        val totalAmount: Double = 0.0,
        @SerializedName("note_title")
        val noteTitle: String,
        @SerializedName("note_type")
        val noteType: Int = 0,
        @SerializedName("status_id")
        val statusId: Int,
        @SerializedName("updated_at")
        val updatedAt: String,
        @SerializedName("user_id")
        val userId: Int
    ) : BaseModel, Parcelable

    data class PrescriptionResponse(
        @SerializedName("data")
        val data: Prescription
    ) : BaseModel, BaseResponse()

    data class Prescription(
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("email")
        val email: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("mobile_number")
        val mobileNumber: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("ordered_by_user")
        val orderedByUser: Int,
        @SerializedName("prescription_for")
        val prescriptionFor: String,
        @SerializedName("prescription_path")
        val prescriptionPath: String,
        @SerializedName("updated_at")
        val updatedAt: String
    )

    data class SearchResponse(
        @SerializedName("data")
        val `data`: Data,
        @SerializedName("ResponseHeader")
        val responseHeader: ResponseHeader
    )

    data class Data(
        @SerializedName("medicines")
        val medicines: ArrayList<Product>,
        @SerializedName("miscellaneous")
        val miscellaneous: ArrayList<Product>
    ) : BaseModel

    data class ResponseHeader(
        @SerializedName("ResponseCode")
        val responseCode: Int,
        @SerializedName("ResponseMessage")
        val responseMessage: String
    )
}
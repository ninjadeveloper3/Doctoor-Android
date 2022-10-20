package com.Doctoor.app.model.response

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.Doctoor.app.data.database.EQUIPMENT
import kotlinx.android.parcel.Parcelize

class Equipments {
    @Entity(tableName = EQUIPMENT)
    @Parcelize
    data class Equipment(
        @PrimaryKey
        @SerializedName("id") val id: Int,
        @SerializedName("equipment_name") val equipment_name: String?,
        @SerializedName("description") val description: String?,
        @SerializedName("brand") val brand: String?,
        @SerializedName("image") val image: String?,
        @SerializedName("price") val price: Double = 0.0,
        @SerializedName("is_rental") val isRental: String?,
        @SerializedName("quantity") val availableQuantity: Int,
        @SerializedName("status") val status: String?,
        @ColumnInfo(name = "order_quantity", collate = 1) var orderQuantity: Int,
        @SerializedName("is_prescription_req") val isPrescriptionReq: Int // 1
    ) : BaseModel, Parcelable

    @Parcelize
    data class RentalEquipment(
        @SerializedName("rental_equipment_id") val rentalEquipmentId: Int?,
        @SerializedName("city_id") val cityId: Int?,
        @SerializedName("equipment_name") val equipment_name: String?,
        @SerializedName("price") val price: String,
        @SerializedName("is_rental") val isRental: String?,
        @SerializedName("is_prescription_req") val isPrescriptionReq: Int // 1
    ) : BaseModel, Parcelable
}
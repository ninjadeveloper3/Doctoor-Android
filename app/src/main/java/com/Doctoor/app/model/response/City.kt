package com.Doctoor.app.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(
    @SerializedName("city_name")
    val cityName: String, // lahore
    @SerializedName("province")
    val province: String, // Punjab
    @SerializedName("created_at")
    val createdAt: String, // -0001-11-30 00:00:00
    @SerializedName("id")
    val id: Int // 1
) : BaseModel, Parcelable
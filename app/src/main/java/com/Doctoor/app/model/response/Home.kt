package com.Doctoor.app.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

class Home {
    data class BannerImage(
        @SerializedName("id") val id: Int,
        @SerializedName("file") val file: String,
        @SerializedName("media_type") val mediaType: String
    ) : BaseModel

    data class InDemand(
        @SerializedName("data")
        val data: Data,
        @SerializedName("ResponseHeader")
        val responseHeader: ResponseHeader
    ) : BaseModel {
        @Parcelize
        data class Data(
            @SerializedName("medicines")
            val equipments: List<Medicines.Product>,
            @SerializedName("equipments")
            val medicines: List<Equipments.Equipment>,
            @SerializedName("tests")
            val tests: List<Tests.Test>
        ) : BaseModel, Parcelable
    }

    data class PlayId(
        @SerializedName("ResponseHeader")
        val responseHeader: ResponseHeader
    )

    data class ResponseHeader(
        @SerializedName("ResponseCode")
        val responseCode: Int,
        @SerializedName("ResponseMessage")
        val responseMessage: String
    )
}
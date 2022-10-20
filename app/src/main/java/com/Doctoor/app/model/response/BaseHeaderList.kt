package com.Doctoor.app.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class BaseHeaderList<E : BaseModel>(

) : BaseResponse(), Parcelable {
    @SerializedName("data")
    val data: MutableList<E> = ArrayList()
}
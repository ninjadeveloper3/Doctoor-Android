package com.Doctoor.app.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
open class BaseResponse : BaseModel, Parcelable {
    @SerializedName("ResponseHeader")
    var responseHeader: ResponseHeader? = null
}

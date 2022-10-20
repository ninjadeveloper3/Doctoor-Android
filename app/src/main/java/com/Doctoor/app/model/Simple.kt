package com.Doctoor.app.model

import com.google.gson.annotations.SerializedName
import com.Doctoor.app.model.response.BaseModel

data class Simple(
    @SerializedName("message")
    val message: String, // text
    @SerializedName("starus")
    val starus: Int // 1
) : BaseModel
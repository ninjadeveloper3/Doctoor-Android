package com.Doctoor.app.model.response

import com.google.gson.annotations.SerializedName

data class ResponseHeader(
    @SerializedName("ResponseCode")
    val responseCode: Int, // 200
    @SerializedName("ResponseMessage")
    val responseMessage: String //  All Categories are found
)

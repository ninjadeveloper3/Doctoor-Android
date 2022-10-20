package com.Doctoor.app.model.response

import com.google.gson.annotations.SerializedName

data class ApiErrorResponse(@SerializedName("ResponseHeader") var responseHeader: ResponseHeader)

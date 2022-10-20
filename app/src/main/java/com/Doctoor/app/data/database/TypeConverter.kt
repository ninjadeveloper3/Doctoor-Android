package com.Doctoor.app.data.database

import com.google.gson.Gson
import com.Doctoor.app.model.response.Medicines

class TypeConverter {

    @androidx.room.TypeConverter
    fun listToJson(value: ArrayList<Medicines.Warning>?): String {

        return Gson().toJson(value)
    }

    @androidx.room.TypeConverter
    fun jsonToList(value: String?): ArrayList<Medicines.Warning>? {

        return if (value != null) {
            if (value.run { isNotEmpty() }) {
                ArrayList()
            } else {
                ArrayList()
            }
        } else {
            ArrayList()
        }
    }
}
package com.Doctoor.app.data.remote

import com.google.gson.*
import java.io.Reader
import java.lang.reflect.Modifier
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object GsonProvider {

    val gson = makeGson()

    @JvmStatic
    fun makeGson(): Gson {
        return makeDefaultGsonBuilder().create()
    }


    fun makeDefaultGsonBuilder(): GsonBuilder {
        return GsonBuilder()
            // .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC)
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .registerTypeAdapter(Date::class.java, DateDeserializer())
    }

    private class DateDeserializer : JsonDeserializer<Date> {
        @Throws(JsonParseException::class)
        override fun deserialize(
            element: JsonElement,
            arg1: Type,
            arg2: JsonDeserializationContext
        ): Date? {
            val date = element.asString
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            formatter.timeZone = TimeZone.getTimeZone("UTC")
            try {
                return formatter.parse(date)
            } catch (e: ParseException) {
                e.printStackTrace()
                return null
            }

        }
    }

    fun <T> fromJson(json: Reader, classOfT: Class<T>): T {
        return gson.fromJson(json, classOfT)
    }

    fun <T> fromJson(json: String, classOfT: Class<T>): T {
        return gson.fromJson(json, classOfT)
    }

    fun <T> fromJson(json: String, typeOfT: Type): T {
        return gson.fromJson(json, typeOfT)
    }

    fun toJson(src: Any): String {
        return gson.toJson(src)
    }

}

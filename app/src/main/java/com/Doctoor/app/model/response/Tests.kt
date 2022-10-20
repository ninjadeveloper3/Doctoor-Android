package com.Doctoor.app.model.response

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.Doctoor.app.data.database.LABTEST
import kotlinx.android.parcel.Parcelize

class Tests {
    data class Lab(
        @SerializedName("lab_name")
        var labName: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("logo")
        val logo: String
    ) : BaseModel

    data class TestCategory(
        @SerializedName("id") val id: Int,
        @SerializedName("lab_id") val lab_id: Int,
        @SerializedName("testcategory_id") val testcategory_id: Int,
        @SerializedName("lab_name") val lab_name: String,
        @SerializedName("lab_logo") val lab_logo: String,
        @SerializedName("test_category_name") val test_category_name: String,
        @SerializedName("category_logo") val category_logo: String
    ) : BaseModel


    data class TestPopularCategory(
        @SerializedName("id") val id: Int,
        @SerializedName("lab_id") val labId: Int,
        @SerializedName("testcategory_id") val testcategoryId: Int,
        @SerializedName("test_category") val testCategory: PopularTestCategory
    ) : BaseModel

    data class PopularService(
        @SerializedName("city")
        val city: City,
        @SerializedName("city_id")
        val cityId: Int,
        @SerializedName("created_at")
        val createdAt: Any,
        @SerializedName("id")
        val id: Int,
        @SerializedName("in_demand")
        val inDemand: Int,
        @SerializedName("service_id")
        val serviceId: Int,
        @SerializedName("service_name")
        val serviceName: String,
        @SerializedName("image") val image: String?,
        @SerializedName("service_types")
        val serviceTypes: ServiceTypes
    ) : BaseModel

    data class City(
        @SerializedName("city_name")
        val cityName: String,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("is_show")
        val isShow: Int,
        @SerializedName("province")
        val province: String
    )

    data class ServiceTypes(
        @SerializedName("id")
        val id: Int,
        @SerializedName("in_demand")
        val inDemand: Int,
        @SerializedName("service_name")
        val serviceName: String
    )

    data class PopularTestCategory(
        @SerializedName("category_name") val testCategoryName: String,
        @SerializedName("logo") val categoryLogo: String
    )

    @Parcelize
    @Entity(tableName = LABTEST)
    data class Test(
        @PrimaryKey
        @SerializedName("id") val id: Int,
        @SerializedName("test_name") val test_name: String,
        @SerializedName("logo") val logo: String?,
        @SerializedName("description") val description: String?,
        @SerializedName("lab_id") val lab_id: Int,
        @SerializedName("testcategory_id") val testcategory_id: Int,
        @SerializedName("price") val price: Double = 0.0,
        @SerializedName("is_prescription_req") val isPrescriptionReq: Int,
        @ColumnInfo(name = "order_quantity", collate = 1) var orderQuantity: Int
    ) : BaseModel, Parcelable

    data class LabReport(

        @SerializedName("created_at")
        val createdAt: String,

        @SerializedName("deleted_at")
        val deletedAt: Any,

        @SerializedName("file")
        val `file`: String = "",

        @SerializedName("id")
        val id: Int,

        @SerializedName("test")
        val test: Test,

        @SerializedName("test_id")
        val testId: Int,

        @SerializedName("updated_at")
        val updatedAt: Any,

        @SerializedName("user_id")
        val userId: Int
    ) : BaseModel

    data class MyPrescription(
        @SerializedName("id")
        val id: Int,
        @SerializedName("mobile_number")
        val mobileNumber: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("ordered_by_user")
        val orderedByUser: Int,
        @SerializedName("prescription_for")
        val prescriptionFor: String,
        @SerializedName("prescription_path")
        val file: String,
        @SerializedName("updated_at")
        val updatedAt: String,
        @SerializedName("created_at")
        val createdAt: String
    ) : BaseModel
}
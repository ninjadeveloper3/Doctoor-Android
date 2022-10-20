package com.Doctoor.app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.Doctoor.app.BuildConfig
import com.Doctoor.app.model.response.Equipments
import com.Doctoor.app.model.response.Medicines
import com.Doctoor.app.model.response.Tests

@Database(
    entities = [(Medicines.Product::class), Equipments.Equipment::class, Tests.Test::class],
    version = BuildConfig.DB_VERION
)
//@Singleton
@TypeConverters(TypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun medicineDao(): MedicineDao
    abstract fun labTestDao(): LabTestDao
    abstract fun equipmentDao(): EquipmentDao
}
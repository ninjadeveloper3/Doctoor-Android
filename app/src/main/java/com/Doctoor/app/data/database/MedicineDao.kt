package com.Doctoor.app.data.database

import androidx.room.*
import com.Doctoor.app.model.response.Medicines

@Dao
interface MedicineDao {
    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    fun insertAllMedicinesProduct(data: List<Medicines.Product>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMedicinesProduct(product: Medicines.Product)

    @Query("SELECT * FROM $MEDICINES")
    fun getAllAllMedicinesProduct(): MutableList<Medicines.Product>

    @Query("SELECT * FROM $MEDICINES WHERE id==:id AND medicineName==:medicineName ")
    fun isInCart(id: Int, medicineName: String): Medicines.Product

    @Delete
    fun delete(product: Medicines.Product)

    @Update
    fun update(product: Medicines.Product)

    @Query("DELETE FROM $MEDICINES")
    fun empty()
}
package com.Doctoor.app.data.database

import androidx.room.*
import com.Doctoor.app.model.response.Equipments

@Dao
interface EquipmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTestsProduct(data: List<Equipments.Equipment>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEquipment(equipment: Equipments.Equipment)

    @Query("SELECT * FROM $EQUIPMENT")
    fun getAllAllEquipments(): MutableList<Equipments.Equipment>

    @Query("SELECT * FROM $EQUIPMENT WHERE id==:id")
    fun isInCart(id: Int): Equipments.Equipment

    @Delete
    fun delete(equipment: Equipments.Equipment)

    @Update
    fun update(equipment: Equipments.Equipment)

    @Query("DELETE FROM $EQUIPMENT")
    fun empty()
}
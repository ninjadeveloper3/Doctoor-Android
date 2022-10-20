package com.Doctoor.app.data.database

import androidx.room.*
import com.Doctoor.app.model.response.Tests

@Dao
interface LabTestDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTestsProduct(data: List<Tests.Test>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTest(test: Tests.Test)

    @Query("SELECT * FROM $LABTEST")
    fun getAllAllTests(): MutableList<Tests.Test>

    @Query("SELECT * FROM $LABTEST WHERE id==:id")
    fun isInCart(id: Int): Tests.Test

    @Delete
    fun delete(test: Tests.Test)

    @Update
    fun update(test: Tests.Test)

    @Query("DELETE FROM $LABTEST")
    fun empty()
}
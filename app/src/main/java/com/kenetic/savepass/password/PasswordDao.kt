package com.kenetic.savepass.password

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PasswordDao {

    @Insert
    suspend fun insertPassData(passwordData: PasswordData)

    @Update
    suspend fun updatePassData(passwordData: PasswordData)

    @Delete
    suspend fun deletePassData(passwordData: PasswordData)

    @Query("SELECT * FROM password_data")
    fun getAllPassData(): Flow<List<PasswordData>>

    @Query("SELECT * FROM password_data WHERE ID = :id")
    fun getByIdPassData(id: Int): Flow<PasswordData>

}
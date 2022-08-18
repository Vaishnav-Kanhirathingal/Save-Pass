package com.kenetic.savepass.password

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PasswordDao {

    //----------------------------------------------------------------------------------------------
    @Insert
    suspend fun insertPassData(passwordData: PasswordData)

    @Update
    suspend fun updatePassData(passwordData: PasswordData)

    @Delete
    suspend fun deletePassData(passwordData: PasswordData)

    @Query("SELECT * FROM password_data")
    fun getAllPassData(): Flow<List<PasswordData>>

    @Query("SELECT id FROM password_data")
    fun getAllPassDataId(): Flow<List<Int>>

    @Query("SELECT * FROM password_data WHERE ID = :id")
    fun getByIdPassData(id: Int): Flow<PasswordData>

    @Query("UPDATE password_data SET access = 0 WHERE access = 1")
    fun resetAccessForAll()

    //----------------------------------------------------------------------------------------------
    @Query("SELECT id FROM password_data WHERE is_an_application = :isAnApplication")
    fun getAllSpecifiedService(isAnApplication: Boolean): Flow<List<Int>>

    //----------------------------------------------------------------------------------------------
    @Query("SELECT * FROM password_data WHERE id = :idNum")
    fun getById(idNum: Int): Flow<PasswordData>
}
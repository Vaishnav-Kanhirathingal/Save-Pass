package com.kenetic.savepass.password

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "password_data")
data class PasswordData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "service_name") var serviceName: String,
    @ColumnInfo(name = "service_password") var servicePassword: String,
    @ColumnInfo(name = "is_an_application") var isAnApplication: Boolean = false,
    @ColumnInfo(name = "use_finger_print") var useFingerPrint: Boolean = true,
    @ColumnInfo(name = "access") var access: Boolean = false
)
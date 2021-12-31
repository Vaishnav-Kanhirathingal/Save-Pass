package com.kenetic.savepass.password

import android.app.Application

class PasswordApplication:Application() {
    val database:PasswordRoomDatabase by lazy {
        PasswordRoomDatabase.getDatabase(this)
    }
}
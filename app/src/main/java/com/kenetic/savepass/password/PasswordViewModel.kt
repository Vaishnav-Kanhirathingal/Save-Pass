package com.kenetic.savepass.password

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PasswordViewModel(private val passwordDao: PasswordDao) : ViewModel() {

    private val TAG = "PasswordViewModel"

    fun delete(passwordData: PasswordData) {
        CoroutineScope(Dispatchers.IO).launch {
            passwordDao.resetAccessForAll()
            passwordDao.deletePassData(passwordData)
        }
    }

    fun insert(passwordData: PasswordData) {
        CoroutineScope(Dispatchers.IO).launch {
            passwordDao.resetAccessForAll()
            passwordDao.insertPassData(passwordData)
        }
    }

    fun update(passwordData: PasswordData) {
        CoroutineScope(Dispatchers.IO).launch {
            passwordDao.resetAccessForAll()
            passwordDao.updatePassData(passwordData)
        }
    }

    fun getAccess(pass: PasswordData) {
        CoroutineScope(Dispatchers.IO).launch {
            Log.i(TAG, "getAccess called")
            passwordDao.resetAccessForAll()
            pass.access = true
            passwordDao.updatePassData(pass)
            Log.i(TAG, "access = ${pass.access}")
        }
    }


    fun resetAllAccess() {
        CoroutineScope(Dispatchers.IO).launch {
            Log.i(TAG, "resetAccess called")
            passwordDao.resetAccessForAll()
        }
    }

    fun getById(id:Int):Flow<PasswordData>{
        return passwordDao.getById(id)
    }
    fun getAllId():Flow<List<Int>>{
        return passwordDao.getAllPassDataId()
    }

    fun getWeb():Flow<List<Int>> {
        return passwordDao.getAllSpecifiedService(false)

    }
    fun getApp():Flow<List<Int>>{
        return passwordDao.getAllSpecifiedService(true)
    }
}

class PasswordViewModelFactory(private val passwordDao: PasswordDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PasswordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PasswordViewModel(passwordDao) as T
        } else {
            throw IllegalArgumentException("unknown model class")
        }
    }
}
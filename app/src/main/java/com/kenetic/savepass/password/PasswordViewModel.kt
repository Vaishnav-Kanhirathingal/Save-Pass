package com.kenetic.savepass.password

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PasswordViewModel(private val passwordDao: PasswordDao) : ViewModel() {

    private var previousPassData: PasswordData? = null
    var passList: LiveData<List<PasswordData>> = passwordDao.getAllPassData().asLiveData()

    fun getById(id: Int): LiveData<PasswordData> = passwordDao.getByIdPassData(id).asLiveData()

    fun delete(passwordData: PasswordData) {
        viewModelScope.launch {
            if (previousPassData != null) {
                viewModelScope.launch {
                    passwordDao.updatePassData(previousPassData!!)
                    previousPassData = null
                }
            }
            passwordDao.deletePassData(passwordData)
        }
    }

    fun insert(passwordData: PasswordData) {
        viewModelScope.launch {
            if (previousPassData != null) {
                viewModelScope.launch {
                    passwordDao.updatePassData(previousPassData!!)
                    previousPassData = null
                }
            }
            passwordDao.insertPassData(passwordData)
            previousPassData = null
        }
    }

    fun update(passwordData: PasswordData) {
        viewModelScope.launch {
            if (previousPassData != null) {
                viewModelScope.launch {
                    passwordDao.updatePassData(previousPassData!!)
                    previousPassData = null
                }
            }
            passwordDao.updatePassData(passwordData)
        }
    }

    fun getAccess(pass: PasswordData) {
        pass.access = true
        viewModelScope.launch {
            if (previousPassData != null) {
                viewModelScope.launch {
                    passwordDao.updatePassData(previousPassData!!)
                    //previousPassData = null
                }
            }
            passwordDao.updatePassData(pass)
            pass.access = false
            previousPassData = pass
        }
    }

    fun resetPreviousAccess() {
        viewModelScope.launch {
            if (previousPassData != null) {
                passwordDao.updatePassData(previousPassData!!)
            }
        }
    }

    fun resetAllAccess(){
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("ViewModel", "coroutine for resetAccessForAll launched")
            passwordDao.resetAccessForAll()
        }
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
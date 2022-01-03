package com.kenetic.savepass.password

import androidx.lifecycle.*
import kotlinx.coroutines.launch

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
                    previousPassData = null
                }
            }
            passwordDao.updatePassData(pass)
            pass.access = false
            previousPassData = pass
        }
    }

    private suspend fun resetPreviousAccess() {
        if (previousPassData != null) {
            passwordDao.updatePassData(previousPassData!!)
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
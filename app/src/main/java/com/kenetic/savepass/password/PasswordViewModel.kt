package com.kenetic.savepass.password

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import kotlin.IllegalArgumentException

class PasswordViewModel(private val passwordDao: PasswordDao) : ViewModel() {
    var passList: LiveData<List<PasswordData>> = passwordDao.getAllPassData().asLiveData()

    fun getById(id: Int): LiveData<PasswordData> = passwordDao.getByIdPassData(id).asLiveData()

    fun delete(passwordData: PasswordData) {
        viewModelScope.launch {
            passwordDao.deletePassData(passwordData)
        }
    }
    fun insert(passwordData: PasswordData) {
        viewModelScope.launch {
            passwordDao.insertPassData(passwordData)
        }
    }
    fun update(passwordData: PasswordData) {
        viewModelScope.launch {
            passwordDao.updatePassData(passwordData)
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
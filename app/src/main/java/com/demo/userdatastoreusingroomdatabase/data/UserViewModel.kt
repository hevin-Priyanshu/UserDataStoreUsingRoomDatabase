package com.demo.userdatastoreusingroomdatabase.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel (application: Application) : AndroidViewModel(application)  {


    private val repository : UserRepository
    val allUsers: LiveData<List<User>>


    init {
        val dao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(dao)
        allUsers = repository.allUsers
    }


    fun deleteUser(user: User) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(user)
    }

    fun updateUser(user: User) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(user)
    }

    fun addUser(user: User) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(user)
    }

     fun getUserById(userId: Long): User {
        return repository.getUserById(userId)
    }

}
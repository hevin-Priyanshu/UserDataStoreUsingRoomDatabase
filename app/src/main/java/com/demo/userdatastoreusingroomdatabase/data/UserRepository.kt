package com.demo.userdatastoreusingroomdatabase.data

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {

    val allUsers: LiveData<List<User>> = userDao.readAllData()

    fun insert(user: User) {
        userDao.addUser(user)
    }

    fun delete(user: User){
        userDao.deleteUser(user)
    }

    fun update(user: User){
        userDao.updateUser(user)
    }

      fun getUserById(userId: Long): User {
        return userDao.getUserById(userId)
    }

}
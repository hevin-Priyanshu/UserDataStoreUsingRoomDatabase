package com.demo.userdatastoreusingroomdatabase.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun addUser(user: User)

    @Query("SELECT * FROM user_table ORDER BY id ASC")
     fun readAllData(): LiveData<List<User>>

    @Delete
     fun deleteUser(user: User)

    @Update
     fun updateUser(user: User)

    @Query("SELECT * FROM user_table WHERE id = :userId")
     fun getUserById(userId: Long): User
}
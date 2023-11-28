package com.demo.userdatastoreusingroomdatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import com.demo.userdatastoreusingroomdatabase.data.User
import com.demo.userdatastoreusingroomdatabase.data.UserDatabase
import com.demo.userdatastoreusingroomdatabase.data.UserViewModel


class UserActivityItem : AppCompatActivity() {

    private lateinit var viewModel: UserViewModel
    private lateinit var editTextFirstName: EditText
    private lateinit var editTextLastName: EditText
    private lateinit var editTextAge: EditText
    private lateinit var updateButton: Button
    private lateinit var addBtn: Button
    private var userId: Long = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_item)

        viewModel = ViewModelProvider(
            this@UserActivityItem,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[UserViewModel::class.java]

        editTextFirstName = findViewById(R.id.edit_text_firstName)
        editTextLastName = findViewById(R.id.edit_text_lastName)
        editTextAge = findViewById(R.id.edit_text_age)
        updateButton = findViewById(R.id.reset_btn)
        addBtn = findViewById(R.id.submitBtn)


        if (intent.hasExtra("userPosition")){
        val userPosition = intent.getIntExtra("userPosition", -1)

            if (userPosition == -1) {
                addBtn.visibility = View.VISIBLE
                updateButton.visibility = View.GONE
            } else {
                addBtn.visibility = View.GONE
                updateButton.visibility = View.VISIBLE
            }
        }


        //important code
        if(intent.hasExtra("onPosition")){
            val updateBtnId = intent.getIntExtra("onPosition", 0)
            if(updateBtnId == 0){
                updateButton.visibility = View.GONE
                addBtn.visibility = View.VISIBLE
            }
            else{
                updateButton.visibility = View.VISIBLE
                addBtn.visibility = View.GONE
            }
        }

        userId = intent.getLongExtra("userId", -1)
        if (userId != -1L) {
            // Load user data for updating
            val user = viewModel.getUserById(userId)
            // Populate the UI with existing user data
            editTextFirstName.setText(user.firstName)
            editTextLastName.setText(user.lastName)
            editTextAge.setText(user.age.toString())
        }

        addBtn.setOnClickListener {
            saveOrUpdateUser()
        }

        updateButton.setOnClickListener {
            saveOrUpdateUser()
        }

    }

    private fun saveOrUpdateUser() {
        val firstName = editTextFirstName.text.toString().trim()
        val lastName = editTextLastName.text.toString().trim()
        val age = editTextAge.text.toString().trim()

        if (firstName.isNotEmpty() && lastName.isNotEmpty() && age.isNotEmpty()) {
//            val user = User(userId, firstName, lastName, age)

            if (userId == -1L) {
                val user = User(0, firstName, lastName, age)
                // Insert a new user if the userId is -1
//                viewModel.addUser(user)
                UserDatabase.getDatabase(this).userDao().addUser(user)
                Toast.makeText(this@UserActivityItem, "User added successfully", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val user = User(userId, firstName, lastName, age)
                // Update the existing user
                viewModel.updateUser(user)
                Toast.makeText(
                    this@UserActivityItem, "User updated successfully", Toast.LENGTH_SHORT
                ).show()
            }
            finish() // Close the activity after saving/updating
        } else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
    }
}



package com.demo.userdatastoreusingroomdatabase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.userdatastoreusingroomdatabase.data.User
import com.demo.userdatastoreusingroomdatabase.data.UserDatabase
import com.demo.userdatastoreusingroomdatabase.data.UserViewModel
import com.demo.userdatastoreusingroomdatabase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel = ViewModelProvider(
            this@MainActivity, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[UserViewModel::class.java]


        viewModel.allUsers.observe(this) { users ->
            if (users.isEmpty()) {
                // No user data in the database, show demo data
                showDemoData()
            } else {
                // User data exists in the database, hide demo data and update RecyclerView
                hideDemoData()
            }
        }


        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this@MainActivity, UserActivityItem::class.java)
            intent.putExtra("onPosition", 0)
            startActivity(intent)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        UserDatabase.getDatabase(this).userDao().readAllData().observe(this) {
            Log.d("TAG", "onCreate: $it")
            binding.recyclerView.adapter = MyAdapter(this, it as ArrayList<User>)
        }

    }

    private fun showDemoData() {
        binding.cardView.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
    }

    // Function to hide demo data
    private fun hideDemoData() {
        binding.cardView.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
    }

}
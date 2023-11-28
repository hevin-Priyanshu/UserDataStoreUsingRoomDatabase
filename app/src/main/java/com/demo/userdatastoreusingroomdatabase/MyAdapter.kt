package com.demo.userdatastoreusingroomdatabase

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.demo.userdatastoreusingroomdatabase.data.User
import com.demo.userdatastoreusingroomdatabase.data.UserDatabase


class MyAdapter(
    private val context: Context,
    private var list: ArrayList<User>,
) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout =
            LayoutInflater.from(context).inflate(R.layout.recycler_view_item, parent, false)
        return MyViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.editTextFirstName.text = list[position].firstName
        holder.editTextLastName.text = list[position].lastName
        holder.editTextAge.text = list[position].age


        holder.deleteBtn.setOnClickListener {
//            list.remove(list[position])
//            notifyDataSetChanged()
            UserDatabase.getDatabase(context).userDao().deleteUser(list[position])
            Toast.makeText(context, "User Deleted successfully", Toast.LENGTH_SHORT).show()

        }

//        holder.updateBtn.setOnClickListener {
//            // Open new activity here
//            val intent = Intent(context, UserActivityItem::class.java)
//            // You can also pass data to the new activity if needed
//            intent.putExtra("userId", list[position].id)
//            startActivity(context, intent, null)
//
//        }

        holder.clickOnItem.setOnClickListener {

            val intent = Intent(context, UserActivityItem::class.java)
            intent.putExtra("userId", list[position].id)
            intent.putExtra("userPosition", position)
            startActivity(context, intent, null)
        }

    }

}


class MyViewHolder(itemView: View) : ViewHolder(itemView) {
    var editTextFirstName: TextView = itemView.findViewById(R.id.textViewFirstName)
    var editTextLastName: TextView = itemView.findViewById(R.id.textViewLastName)
    var editTextAge: TextView = itemView.findViewById(R.id.textViewAge)
    var deleteBtn: Button = itemView.findViewById(R.id.delete_btn)
    var clickOnItem: CardView = itemView.findViewById(R.id.clickOnItem)

}
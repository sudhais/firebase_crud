package com.example.firebase_crud

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class mainAdapter(private val taskList: MutableList<mainModel>) : RecyclerView.Adapter<mainAdapter.ViewHolder>() {

    private val firebaseHelper = FirebaseHelper()

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name:TextView = itemView.findViewById(R.id.name)
        val email:TextView = itemView.findViewById(R.id.email)
        val subject:TextView = itemView.findViewById(R.id.subject)
        val btn_delete:ImageView = itemView.findViewById(R.id.btn_delete)
        val btn_edit: ImageView = itemView.findViewById(R.id.btn_edit)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  taskList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = taskList[position]
        holder.name.text = task.name
        holder.email.text = task.email
        holder.subject.text = task.subject

        holder.btn_delete.setOnClickListener {
            // Remove the item from the data list
//            taskList.removeAt(position)
            // Notify the adapter that an item has been removed
//            notifyItemRemoved(position)
            val context = holder.itemView.context
            firebaseHelper.deleteTask(task.id!!, {
                // Task creation was successful
                Toast.makeText(context, "Task created successfully", Toast.LENGTH_SHORT).show()

            }, { exception ->
                // Task creation failed, handle the error
                Toast.makeText(context, "Task creation failed: ${exception.message}", Toast.LENGTH_SHORT).show()
            })

        }

        holder.btn_edit.setOnClickListener {

            val context = holder.itemView.context

            val intent = Intent(context, EditItem::class.java)
            intent.putExtra("id", task.id)
            intent.putExtra("name", task.name)
            intent.putExtra("email", task.email)
            intent.putExtra("subject", task.subject)

            context.startActivity(intent)
        }
    }
}
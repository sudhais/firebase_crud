package com.example.firebase_crud

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import java.text.FieldPosition

class FirebaseHelper {
      var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference.child("teachers")

    fun createTask(task: mainModel, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val taskRef = databaseReference.child(task.id!!)
//        task.id = taskRef.key // Assign the generated key as the task ID
        taskRef.setValue(task)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun updateTask(task: mainModel, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        databaseReference.child(task.id!!).setValue(task)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }


    fun deleteTask(id: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        databaseReference.child(id).removeValue()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun getTasks(listener: ValueEventListener) {
        databaseReference.addValueEventListener(listener)
    }
}
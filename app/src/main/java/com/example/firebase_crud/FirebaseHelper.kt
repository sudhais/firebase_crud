package com.example.firebase_crud

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FirebaseHelper {
    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference.child("teachers")

    fun createTask(task: mainModel) {
        val taskRef = databaseReference.push()
        taskRef.setValue(task)
    }

    fun updateTask(task: mainModel) {
        databaseReference.child(task.name ?: "").setValue(task)
    }

    fun deleteTask(taskId: String) {
        databaseReference.child(taskId).removeValue()
    }

    fun getTasks(listener: ValueEventListener) {
        databaseReference.addValueEventListener(listener)
    }
}
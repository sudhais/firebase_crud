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
    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference.child("teachers")

    fun createTask(task: mainModel, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val taskRef = databaseReference.push()
//        task.id = taskRef.key // Assign the generated key as the task ID
        taskRef.setValue(task)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun updateTask(name:String, task: mainModel) {
        databaseReference.child(name)
    }

//    fun getKey(task: mainModel):String {
//        var data:String = ""
//        val tasksListener = object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                var existData:mainModel
//                for (dataSnapshot in snapshot.children) {
//                    existData = dataSnapshot.getValue(mainModel::class.java)
//                    if(){
//                        data = dataSnapshot.key.toString()
//                    }
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Handle errors
//            }
//        }
//        getTasks(tasksListener)
//
//        return data
//
//    }

    fun deleteTask(name: String) {
        databaseReference.child(name).removeValue()
    }

    fun getTasks(listener: ValueEventListener) {
        databaseReference.addValueEventListener(listener)
    }
}
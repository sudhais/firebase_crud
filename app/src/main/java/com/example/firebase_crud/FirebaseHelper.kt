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

    fun createTaskWithImage(task:itemModel, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
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

//    fun getTasks(listener: ValueEventListener) {
//        databaseReference.addValueEventListener(listener)
//    }

    fun getTasks(callback: (MutableList<itemModel>?) -> Unit) {
        databaseReference.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val taskList = mutableListOf<itemModel>()
                    if(snapshot.exists()){
                        for (dataSnapshot in snapshot.children) {
                            var task = dataSnapshot.getValue(itemModel::class.java)
                            task?.id = dataSnapshot.key
                            task?.let { taskList.add(it) }
                        }
                        callback(taskList)
                    }
                    callback(taskList)
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle errors
                }
            }
        )


    }

    fun getSingleData(id:String, callback: (mainModel?) -> Unit){
        var ref = databaseReference.child(id)
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val teacherName = dataSnapshot.child("name").value.toString()
                    val teacherEmail = dataSnapshot.child("email").value.toString()
                    val teacherSubject = dataSnapshot.child("subject").value.toString()
                        //or
                    val newTeach = dataSnapshot.getValue(mainModel::class.java)

                    val teacher = mainModel(id,teacherName, teacherEmail, teacherSubject)
                    callback(teacher)
                } else {
                    callback(null) // Teacher not found
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors that occur during the data retrieval
                callback(null)
            }
        })
    }
}
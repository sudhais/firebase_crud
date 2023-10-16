package com.example.firebase_crud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.database.DatabaseReference

class AddItem : ComponentActivity() {
    private val firebaseHelper = FirebaseHelper()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)


        var txt_name:EditText = findViewById(R.id.txt_name)
        var txt_email:EditText = findViewById(R.id.txt_email)
        var txt_subject:EditText = findViewById(R.id.txt_subject)
        var btn_create:Button = findViewById(R.id.btn_create)

        btn_create.setOnClickListener {
            val taskRef = firebaseHelper.databaseReference.push().key!!
            val newTask = mainModel(
                taskRef,
                name = txt_name.text.toString(),
                email = txt_email.text.toString(),
                subject = txt_subject.text.toString()
            )

            Log.e("1234", "${newTask}")
            firebaseHelper.createTask(newTask, {
                // Task creation was successful
                Toast.makeText(this, "Task created successfully", Toast.LENGTH_SHORT).show()

                // Optionally, clear the input fields
                txt_name.text.clear()
                txt_email.text.clear()
                txt_subject.text.clear()
            }, { exception ->
                // Task creation failed, handle the error
                Toast.makeText(this, "Task creation failed: ${exception.message}", Toast.LENGTH_SHORT).show()
            })
        }
    }
}
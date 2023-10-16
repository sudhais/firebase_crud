package com.example.firebase_crud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity

class EditItem : ComponentActivity() {

    private val firebaseHelper = FirebaseHelper()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_item)

        var edt_name:EditText = findViewById(R.id.edt_name)
        var edt_email:EditText = findViewById(R.id.edt_email)
        var edt_subject:EditText = findViewById(R.id.edt_subject)
        var btn_update:Button = findViewById(R.id.btn_update)

        val name = intent.getStringExtra("name")
        val email = intent.getStringExtra("email")
        val subject = intent.getStringExtra("subject")

        edt_name.setText(name)
        edt_email.setText(email)
        edt_subject.setText(subject)

        btn_update.setOnClickListener {
            val task:mainModel = mainModel(edt_name.text.toString(), edt_email.text.toString(), edt_subject.text.toString())

            if (name != null) {
                firebaseHelper.updateTask(name, task)
            }
        }
    }
}
package com.example.firebase_crud

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.database.DatabaseReference
import java.io.ByteArrayOutputStream
import java.lang.Exception

class AddItem : ComponentActivity() {
    private val firebaseHelper = FirebaseHelper()

    lateinit var btn_image:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)


        var txt_name:EditText = findViewById(R.id.txt_name)
        var txt_email:EditText = findViewById(R.id.txt_email)
        var txt_subject:EditText = findViewById(R.id.txt_subject)
        var btn_create:Button = findViewById(R.id.btn_create)
         btn_image = findViewById(R.id.btn_image)
        var imgView : ImageView = findViewById(R.id.imageView)

        var image:String? = ""

        btn_create.setOnClickListener {
            val taskRef = firebaseHelper.databaseReference.push().key!!
            val newTask = mainModel(
                taskRef,
                name = txt_name.text.toString(),
                email = txt_email.text.toString(),
                subject = txt_subject.text.toString()
            )

//            Log.e("1234", "${newTask}")
//            firebaseHelper.createTask(newTask, {
//                // Task creation was successful
//                Toast.makeText(this, "Task created successfully", Toast.LENGTH_SHORT).show()
//
//                // Optionally, clear the input fields
//                txt_name.text.clear()
//                txt_email.text.clear()
//                txt_subject.text.clear()
//            }, { exception ->
//                // Task creation failed, handle the error
//                Toast.makeText(this, "Task creation failed: ${exception.message}", Toast.LENGTH_SHORT).show()
//            })

            var imgTask = itemModel(
                taskRef,
                name = txt_name.text.toString(),
                email = txt_email.text.toString(),
                subject = txt_subject.text.toString(),
                image
            )
            firebaseHelper.createTaskWithImage(imgTask, {
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

         val ActivityResultLauncher = registerForActivityResult<Intent,ActivityResult>(
            ActivityResultContracts.StartActivityForResult()
        ){result:ActivityResult ->
            if(result.resultCode == RESULT_OK){
                val uri = result.data!!.data
                try {
                    val inputStream = contentResolver.openInputStream(uri!!)
                    val myBitmap = BitmapFactory.decodeStream(inputStream)
                    val stream = ByteArrayOutputStream()
                    myBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                    val bytes = stream.toByteArray()
                    image = Base64.encodeToString(bytes, Base64.DEFAULT)
                    imgView.setImageBitmap(myBitmap)
                    inputStream!!.close()
                    Toast.makeText(this,"image selected", Toast.LENGTH_LONG).show()

                }catch (ex:Exception){
                    Toast.makeText(this,ex.message.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }

        btn_image.setOnClickListener {
            val myfileintent = Intent(Intent.ACTION_GET_CONTENT)
            myfileintent.setType("image/*")
            ActivityResultLauncher.launch(myfileintent)
        }
    }


}
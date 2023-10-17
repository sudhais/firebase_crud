package com.example.firebase_crud

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase_crud.ui.theme.Firebase_crudTheme
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MainActivity : ComponentActivity() {

    private val firebaseHelper = FirebaseHelper()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: mainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rv)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = mainAdapter(mutableListOf())
        recyclerView.adapter = adapter
        var btn_add:Button = findViewById(R.id.btn_add)

        val check = firebaseHelper.getSingleData("-NgqxRjdbHCnKjmHkhm2"){mainModel ->
            if (mainModel != null) {
                // Teacher details retrieved successfully
                println("Teacher Name: ${mainModel.name}")
                println("Teacher Email: ${mainModel.email}")
                println("Teacher Subject: ${mainModel.subject}")
            } else {
                // Handle the case where the teacher is not found
                println("Teacher not found")
            }
        }
        Log.e("1111","${check}")

//        val tasksListener = object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val taskList = mutableListOf<mainModel>()
//                if(snapshot.exists()){
//                    for (dataSnapshot in snapshot.children) {
//                        var task = dataSnapshot.getValue(mainModel::class.java)
//                        task?.id = dataSnapshot.key
//                        task?.let { taskList.add(it) }
////                    Log.e("1111","${dataSnapshot.key}")
//                    }
//                }
//                adapter = mainAdapter(taskList)
//                recyclerView.adapter = adapter
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Handle errors
//            }
//        }

        firebaseHelper.getTasks(){taskList ->
            adapter = mainAdapter(taskList!!)
            recyclerView.adapter = adapter
        }

//        firebaseHelper.getTasks(tasksListener)

//        val test = firebaseHelper.getKey(mainModel("test","test@gmail.com","test"))
//        Log.e("2222", "${test}")

//        adapter.

        btn_add.setOnClickListener{
            val intent = Intent(this,AddItem::class.java)
            startActivity(intent)
        }
    }
}

package com.veenaworldwide

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class Splash : AppCompatActivity() {

    var firebaseDatabase: FirebaseDatabase? = null
    var databaseReference: DatabaseReference? = null

    init {
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase!!.getReference("url")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        getdata()
    }

    private fun getdata() {
        databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue(String::class.java)

                Log.d("FirebaseURL", "onDataChange: $value")
                val mainIntent = Intent(this@Splash, MainActivity::class.java)
                mainIntent.putExtra("urlValue", value);
                startActivity(mainIntent)
                finish()
            }

            override fun onCancelled(error: DatabaseError) {
                firebaseDatabase = FirebaseDatabase.getInstance()
                databaseReference = firebaseDatabase!!.getReference("url")
                getdata()
            }
        })
    }
}
package com.example.sportetu

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class Passerelle : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    var userID: String? = null
    var mStore: FirebaseFirestore? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passerelle)
        mStore = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()
        try {
            userID = mAuth!!.currentUser!!.uid
        }catch (e:KotlinNullPointerException){
            Toast.makeText(this, "à la prochaine!", Toast.LENGTH_SHORT).show()
        }

        val documentReference = mStore!!.collection(
            userID!!
        ).document("profil_utilisateur")
        documentReference.update("nbconnexions", FieldValue.increment(1))
        startActivity(Intent(applicationContext, main_activity::class.java))
        finish()
    }
}
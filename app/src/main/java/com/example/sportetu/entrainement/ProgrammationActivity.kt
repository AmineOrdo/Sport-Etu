package com.example.sportetu.entrainement

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.sportetu.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProgrammationActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_programmation)


        var activite : String
        var date:String
        var heure:String

        var mAuth: FirebaseAuth? = FirebaseAuth.getInstance()
        var mStore = FirebaseFirestore.getInstance()

        var userID:String
        userID = mAuth!!.currentUser!!.uid



        val champActi = findViewById<EditText>(R.id.nomActiviteTF)
        val champDate = findViewById<EditText>(R.id.dateActiviteTF)
        val champHeure = findViewById<EditText>(R.id.heureActiviteTF)

        val button = findViewById<Button>(R.id.buttonProgrammer)

        button.setOnClickListener {
            activite = champActi.getText().toString()
            date = champActi.getText().toString()
            heure = champHeure.getText().toString()

            var activiteAdd :String
            activiteAdd = activite+date+heure

            val documentReference = mStore.collection(userID).document("activiteAdd")

            val Activite: MutableMap<String, Any> = HashMap()

            Activite["sport"] = activite
            Activite["date"] = date
            Activite["heure de début"] = heure


        }




    }
}


  package com.example.sportetu.entrainement

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.sportetu.IHM
import com.example.sportetu.R
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_programmation.*
import kotlinx.android.synthetic.main.recommandation_test.*
import java.text.SimpleDateFormat
import java.util.*


class ProgrammationActivity : AppCompatActivity() {

      lateinit var activite:String
      lateinit var date:String
      lateinit var heure:String
      lateinit var duree:String
      lateinit var status: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_programmation)


        var mAuth: FirebaseAuth? = FirebaseAuth.getInstance()
        var mStore = FirebaseFirestore.getInstance()

        var userID:String
        userID = mAuth!!.currentUser!!.uid
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        var month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        mStore.collection(userID).document("Sport_utilisateur").addSnapshotListener(this) { documentSnapshot, e ->
            var sportPref1 = documentSnapshot!!.getString("Sportpref1").toString()
            var sportPref2 = documentSnapshot!!.getString("Sportpref2").toString()
            var sportPref3 = documentSnapshot!!.getString("Sportpref3").toString()


            val rmd = (1..3).random()

            if (rmd == 1) {
                sportConseiller.text = sportPref1
            } else if (rmd == 2) {
                sportConseiller.text = sportPref2
            } else if (rmd == 3) {
                sportConseiller.text = sportPref3
            }
        }



        pickTimeBtn.setOnClickListener {

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                    timeTv.setText("" + dayOfMonth + " " + (monthOfYear+1) + " " + year)//"" + dayOfMonth + "/" + (month + 1) + "/" + year )
                }, year, month, day)
            dpd.show()

        }


        //Choix de l'heure
        pickHourBtn.setOnClickListener {
            val cal = Calendar.getInstance();
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                hourTv.setText(SimpleDateFormat("HH:mm").format(cal.time));

            }
            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }


//bouton enregistrer
        buttonProgrammer.setOnClickListener {


            /*var prout =Recommandation()
            prout.recommandation(date, heure, duree)
             //activite= testRecommandation.getText().toString()*/

            var activiteChoisie =nomActiviteTF.getText().toString()
            var SportConseille = sportConseiller.getText().toString()
            if(TextUtils.isEmpty(activiteChoisie)){
                activite=sportConseiller.getText().toString()
            } else {
                activite = nomActiviteTF.getText().toString()
            }

            duree = durreHourActiviteTf.getText().toString()+":"+dureeMinutesActiviteTF.getText().toString()
            status="planifie"
            date = timeTv.getText().toString()
            heure = hourTv.getText().toString()



            var activiteAdd :String
            activiteAdd = activite+date+heure

            val documentReference = mStore.collection(userID).document(activiteAdd)

            val Activite = hashMapOf<String, Any>(
                "nomActivite" to activite,
                "date" to date,
                "heuredebut" to heure,
                "duree" to duree,
                "status" to status
            )

            documentReference.set(Activite)
                .addOnSuccessListener {
                    Log.d(
                        "TAG",
                        "onSuccess: activite ajoute$userID"
                    )
                }.addOnFailureListener(OnFailureListener { e ->
                    Log.w(
                        "TAG",
                        "Error writing document",
                        e
                    )
                })

            val intent = Intent(this, IHM::class.java)
            startActivity(intent)
            finish()

        }




    }
}


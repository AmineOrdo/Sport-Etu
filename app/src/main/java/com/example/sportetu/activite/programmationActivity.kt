
  package com.example.sportetu.activite

import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.sportetu.R
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

import kotlinx.android.synthetic.main.activity_programmation.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates


  class programmationActivity : AppCompatActivity() {

      lateinit var activite:String
      lateinit var date:String
      lateinit var heure:String
      lateinit var duree:String
      lateinit var status: String

      lateinit var dateCalendar : String
      lateinit var pendingIntent: PendingIntent
      var durreInt by Delegates.notNull<Int>()

      @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_programmation)

          val constraintLayout = findViewById<ConstraintLayout>(R.id.layoutprog)
          val animationDrawable = constraintLayout.background as AnimationDrawable
          animationDrawable.setEnterFadeDuration(2000)
          animationDrawable.setExitFadeDuration(4000)
          animationDrawable.start()

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


            val rmdGeneral = (1..3).random()
            val rmdFav = (1..3).random()
            val rmdTout = (1..2).random()
            val rmdCategorie = (0..5).random()

            //algorithme de proposition de sport
            val sportIndividuel = arrayOf("musculation","boxe","judo","natation","athlétisme","cyclisme")
            val sportCollectif = arrayOf("football","basketball","badminton","handball","rugby","baseball")

            if(rmdGeneral==1 ||rmdGeneral==2 ){
                if (rmdFav == 1) {
                    sportConseiller.text = sportPref1
                } else if (rmdFav == 2) {
                    sportConseiller.text = sportPref2
                } else if (rmdFav == 3) {
                    sportConseiller.text = sportPref3
                }

            } else if(rmdGeneral==3){
                if(rmdTout==1){
                    sportConseiller.text=sportCollectif[rmdCategorie]
                }else if (rmdTout==2){
                    sportConseiller.text=sportIndividuel[rmdCategorie]
                }

            }

        }


//choix de la date
        pickTimeBtn.setOnClickListener {

            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                    timeTv.setText("" + dayOfMonth + " " + (monthOfYear + 1) + " " + year)

                    dateCalendar = ("" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth)


                },
                year,
                month,
                day
            )
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

            var activiteChoisie =nomActiviteTF.getText().toString()
            var SportConseille = sportConseiller.getText().toString()
            if(TextUtils.isEmpty(activiteChoisie)){
                activite=sportConseiller.getText().toString()
            } else {
                activite = nomActiviteTF.getText().toString()
            }


            duree = durreHourActiviteTf.getText().toString()
            durreInt=duree.toInt()
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



            dateCalendar= dateCalendar +"-"+heure
            var fulldate = SimpleDateFormat("yyyy-MM-dd-HH:mm").parse(dateCalendar)


            val serviceIntent = Intent(this, service::class.java)
            serviceIntent.putExtra("nomActivite", activite)
            serviceIntent.putExtra("date", date)
            serviceIntent.putExtra("heuredebut", heure)
            serviceIntent.putExtra("duree", duree)
            serviceIntent.putExtra("status", "valide")
            serviceIntent.putExtra("nomDoc", activiteAdd)
            serviceIntent.putExtra("echeance", fulldate.getTime() + (durreInt * 60 * 60 * 1000))

            startForegroundService(serviceIntent)



            val intent = Intent(this, main_activity::class.java)
            startActivity(intent)


        }




    }
}


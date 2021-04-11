package com.example.sportetu

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.sportetu.entrainement.ProgrammationActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class service : Service() {
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        var notification = createNotification()
        startForeground(1, notification)

    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Toast.makeText(this, "Activité planifiée", Toast.LENGTH_LONG).show()
        val nomActivite: String = intent.getStringExtra("nomActivite").toString()
        val date: String = intent.getStringExtra("date").toString()
        val heuredebut: String = intent.getStringExtra("heuredebut").toString()
        val duree: String = intent.getStringExtra("duree").toString()
        val status: String = intent.getStringExtra("valide").toString()
        val nomDoc: String = intent.getStringExtra("nomDoc").toString()
        val echeance: Long = intent.getLongExtra("echeance", System.currentTimeMillis())

        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        var mAuth: FirebaseAuth? = FirebaseAuth.getInstance()
        var mStore = FirebaseFirestore.getInstance()

        var userID: String
        userID = mAuth!!.currentUser!!.uid


        val alarmTime = echeance
        val handler = null

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            alarmTime,
            "TAG",
            @RequiresApi(Build.VERSION_CODES.N)
            object : AlarmManager.OnAlarmListener {
                override fun onAlarm() {
                    val documentReference = mStore.collection(userID).document(nomDoc)

                    val modifStatus = hashMapOf<String, Any>(
                        "nomActivite" to nomActivite,
                        "date" to date,
                        "heuredebut" to heuredebut,
                        "duree" to duree,
                        "status" to "valide"
                    )
                    documentReference.set(modifStatus)
                        .addOnSuccessListener {

                            Log.d(
                                "TAG",
                                "onSuccess: status modifie$userID"
                            )
                        }.addOnFailureListener(OnFailureListener { e ->
                            Log.w(
                                "TAG",
                                "Error writing document",
                                e
                            )
                        })
                    stopForeground(true)
                    createNotificationChannel()
                    notifActiviteTermine()
                    val documentReference2 =
                        mStore.collection(userID).document("profil_utilisateur")
                    documentReference2.update("nbActivite", FieldValue.increment(1))
                    onDestroy()

                }
            },
            null
        )




        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Activité terminée", Toast.LENGTH_LONG).show()
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel("channel_id", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


    fun notifActiviteTermine() {
        val builder = NotificationCompat.Builder(
            this, "channel_id"
        )
            .setSmallIcon(R.mipmap.logo_image)
            .setContentTitle("Sport'Etu | activité terminée")
            .setContentText("votre activité est à présent terminée")
            .setColorized(true)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            //.setDefaults(Notification.DEFAULT_VIBRATE)
            .setColor(Color.parseColor("#6B94F8"))
            .setAutoCancel(true)

        val intent = Intent(this, Authentification::class.java)

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(pendingIntent)

        val notificationManager = getSystemService(
            NOTIFICATION_SERVICE
        ) as NotificationManager
        notificationManager.notify(0, builder.build())
    }





    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotification(): Notification {
        val notificationChannelId = "ENDLESS SERVICE CHANNEL"

        // depending on the Android API that we're dealing with we will have
        // to use a specific method to create the notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager;
            val channel = NotificationChannel(
                notificationChannelId,
                "notifications nécessaires à l'application",
                NotificationManager.IMPORTANCE_HIGH
            ).let {
                it.description = "Endless Service channel"
                it.enableLights(true)
                it.lightColor = Color.BLUE
                it.enableVibration(true)
                it.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
                it
            }
            notificationManager.createNotificationChannel(channel)
        }

        val pendingIntent: PendingIntent =
            Intent(this, ProgrammationActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent, 0)
            }

        val builder: Notification.Builder =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) Notification.Builder(
                this,
                notificationChannelId
            ) else Notification.Builder(this, notificationChannelId)

        return builder
            .setContentTitle("activité planifiée")
            .setContentText("votre activité est à présent planifiée")
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.mipmap.logo_image)
            .setTicker("Ticker text")
            .setColor(Color.parseColor("#6B94F8"))
            .setAutoCancel(true)
            .build()
    }

}



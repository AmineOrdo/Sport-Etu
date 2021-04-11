package com.example.sportetu

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_password_reset.*

class password_reset : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_reset)


        //animations du fond d'écran
        val constraintLayout = findViewById<ConstraintLayout>(R.id.layout_reset)
        val animationDrawable = constraintLayout.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()

        mAuth = FirebaseAuth.getInstance()
    btn_reset.setOnClickListener{
        val email = mail_reset.text.toString().trim()

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(applicationContext, "Entrez une adresse email valide", Toast.LENGTH_SHORT).show()
        } else {
            mAuth!!.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Demande envoyée! Vérifiez votre adresse mail", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Cet adresse mail n'est pas enregistré", Toast.LENGTH_SHORT).show()
                    }
                }
        }
     }

    }

}

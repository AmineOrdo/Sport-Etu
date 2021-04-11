package com.example.sportetu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Authentification extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText email, mdp;
    Button connexion;
    TextView mdpOublie, redirectionRegister;
    ProgressBar progressBar;
    CheckBox Remember;

    public static final String PREFS_NAME = "identifiants";
    private static final String PREF_USERNAME = "username";
    private static final String PREF_PASSWORD = "password";

    String mailPref, passwordPref;
    boolean check;

@Override
public void onBackPressed(){
    return;
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification);

        //animations du fond d'écran
        ConstraintLayout constraintLayout = findViewById(R.id.layoutauth);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        email= findViewById(R.id.mail_login);
        mdp= findViewById(R.id.mdp_login);
        connexion= findViewById(R.id.connexion);
        mdpOublie=findViewById(R.id.mdp_forget);
        redirectionRegister=findViewById(R.id.redirection_register);
        progressBar=findViewById(R.id.progressBar);

        Remember=findViewById(R.id.Remember_check);

        //si la date de péremption des indentifiants n'est pas dépassé,on écrit les identifiants dans les editText , sinon, on reset les identifiants
        SharedPreferences pref = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
            mailPref = pref.getString(PREF_USERNAME, null);
            passwordPref =pref.getString(PREF_PASSWORD, null);
            email.setText(mailPref);
            mdp.setText(passwordPref);





        //si on a plus d'identifiants dans les préférences ou décoche le checkbox
        if(mailPref ==null && passwordPref ==null){
            Remember.setChecked(false);
        }else{
            Remember.setChecked(true);
        }


        mAuth = FirebaseAuth.getInstance();

        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email2 = email.getText().toString().trim();
                String mdp2 = mdp.getText().toString().trim();

                //si les identifiants sur le shared preferences sont null et que on a cocher le checkbox, on créer de nouveaux indentifiants dans les préférences



                // conditions sur le mail et mot de passe
                if (TextUtils.isEmpty((email2))) {
                    email.setError("il faut renseigner ton adresse mail ");
                    return;
                }

                if (TextUtils.isEmpty(mdp2)) {
                    mdp.setError("il faut renseigner ton mot de passe");
                    return;
                }

                if (mdp.length() < 5) {
                    mdp.setError("mot de passe incorrect");
                    return;
                }

                if ((Remember.isChecked())) {
                    getSharedPreferences(PREFS_NAME,MODE_PRIVATE)
                            .edit()
                            .putString(PREF_USERNAME,email2)
                            .putString(PREF_PASSWORD, mdp2)
                            .commit();
                }
                if (!Remember.isChecked()){
                        SharedPreferences pref = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.clear();
                        editor.apply();
                }
                //authentification de l'utilisateur sur firebase
                progressBar.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(email2,mdp2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(Authentification.this, "Connexion réussie!", Toast.LENGTH_SHORT).show();
                             startActivity(new Intent(getApplicationContext(),Passerelle.class));

                        }
                        else{
                            Toast.makeText(Authentification.this,"identifiants incorrects   "+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });



        redirectionRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Inscription.class));
            }
        });

        mdpOublie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), password_reset.class));
            }
        });

    }
}
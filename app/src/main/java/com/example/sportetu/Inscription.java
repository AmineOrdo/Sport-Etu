package com.example.sportetu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Inscription extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText email, mdp, prenom, nom;
    Button inscription;
    TextView  redirectionLogin;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        email = findViewById(R.id.mail_register);
        mdp = findViewById(R.id.mdp_register);
        prenom = findViewById(R.id.prenom);
        nom = findViewById(R.id.nom);
        redirectionLogin = findViewById(R.id.redirection_login);
        inscription = findViewById(R.id.inscription);
        progressBar = findViewById(R.id.progressBar2);

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),IHM.class));
            finish();
        }
        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String email2= email.getText().toString().trim();
                String mdp2= email.getText().toString().trim();

                // conditions sur le mail et mot de pass
                if(TextUtils.isEmpty((email2))){
                    email.setError("une adresse mail est nécessaire pour s'inscrire ");
                    return;
                }

                if( TextUtils.isEmpty(mdp2)){
                    mdp.setError("un mot de passe est nécessaire pour s'inscrire");
                    return;
                }

                if(mdp.length()<5){
                    mdp.setError("il faut un mot de passe de plus de 5 caractères");
                    return;
                }

                //eregistrement de l'utilisateur sur firebase
                progressBar.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(email2,mdp2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Inscription.this, "Ton compte est créé, Bienvenue!", Toast.LENGTH_SHORT).show();
                            // startActivity(new Intent(getApplicationContext(),<activité 3 sports>.class));
                        }else{
                            Toast.makeText(Inscription.this,"une erreur est survenue, veuillez réessayer        "+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });


            }
        });

        redirectionLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Authentification.class));
            }
        });
    }
}
package com.example.sportetu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class dialogActivitychoixSports extends AppCompatActivity {
    Button buttonNext;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_fenetre_1);



        //fenetre de dialogue
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_fenetre_1);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        buttonNext = dialog.findViewById(R.id.next1);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(getApplicationContext(), choix3Sports.class));

            }
        });
        dialog.show();

    }
}
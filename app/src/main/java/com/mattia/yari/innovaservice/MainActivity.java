package com.mattia.yari.innovaservice;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button btInt = findViewById(R.id.button_home_intervento);

        btInt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ContactDialog().show(getSupportFragmentManager(), "contact_us");
            }
        });
        }
}
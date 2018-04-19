package com.proyecto.diego.a5estrellas.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.proyecto.diego.a5estrellas.R;

public class WelcomeActivity extends AppCompatActivity {

    Button btnInicio , btnRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        btnInicio = (Button) findViewById(R.id.buttonInicioWelcome);
        btnRegistro = (Button) findViewById(R.id.buttonRegistroWelcome);

        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLogin = new Intent(WelcomeActivity.this,LoginActivity.class);
                startActivity(intentLogin);
            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRegister = new Intent(WelcomeActivity.this,RegisterActivity.class);
                startActivity(intentRegister);
            }
        });

    }
}

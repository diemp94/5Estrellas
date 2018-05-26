package com.proyecto.diego.a5estrellas.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.proyecto.diego.a5estrellas.R;

public class WelcomeActivity extends AppCompatActivity {

    Button btnInicio , btnRegistro;
    FirebaseAuth.AuthStateListener mAuthlistener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        btnInicio = (Button) findViewById(R.id.buttonInicioWelcome);
        btnRegistro = (Button) findViewById(R.id.buttonRegistroWelcome);

        mAuthlistener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.i("WELCOME", "iniciada con " + user.getEmail());
                    goToMainActivity();
                } else {
                    Log.i("WELCOME", "sesion cerrada");
                }
            }
        };
        FirebaseAuth.getInstance().addAuthStateListener(mAuthlistener);

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

    private void goToMainActivity(){
        Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}

package com.proyecto.diego.a5estrellas.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.proyecto.diego.a5estrellas.R;

import java.io.FileInputStream;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPass1, editTextPass2;
    Button btnRegistro;
    String email, pass1, pass2;
    FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        bindUI(); //Metodo para castear todos los elementos de la UI

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = editTextEmail.getText().toString();
                pass1 = editTextPass1.getText().toString();
                pass2 = editTextPass2.getText().toString();

                if(!email.isEmpty() || !pass1.isEmpty() || !pass2.isEmpty()){
                    if(pass1.matches(pass2)){
                        registrar(email,pass1);
                    }else {
                        Toast.makeText(RegisterActivity.this,"Contraseñas diferentes",Toast.LENGTH_SHORT).show();
                    }
                   // Toast.makeText(RegisterActivity.this,"Ingresa usuario y contraseña",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RegisterActivity.this,"Ingresa usuario y contraseña",Toast.LENGTH_SHORT).show();
                }
            }
        });
        mAuthListener = new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user !=null){
                    Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(intent);
                }else{
                    Log.i("SESION","Sesion cerrada");
                }
            }
        };
    }

    private void registrar(String email, String pass){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                }else{
                    Toast.makeText(RegisterActivity.this,task.getException().getMessage().toString(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }



    //Metodo para castear todos los elementos de la UI
    public void bindUI(){
        editTextEmail = (EditText) findViewById(R.id.editTextEmailRegistro);
        editTextPass1 = (EditText) findViewById(R.id.editTextPass1Registro);
        editTextPass2 = (EditText) findViewById(R.id.editTextPass2Registro);
        btnRegistro = (Button) findViewById(R.id.buttonRegistro);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null){
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
    }
}

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.proyecto.diego.a5estrellas.Clases.FirebaseReferences;
import com.proyecto.diego.a5estrellas.Clases.MyMovies;
import com.proyecto.diego.a5estrellas.Clases.Users;
import com.proyecto.diego.a5estrellas.R;

import java.io.FileInputStream;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextUser, editTextEmail, editTextPass1, editTextPass2;
    Button btnRegistro;
    String email, pass1, pass2, user;
    FirebaseAuth.AuthStateListener mAuthListener; //Autenticación en firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        bindUI(); //Metodo para castear todos los elementos de la UI tiene que ir primero

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = editTextUser.getText().toString();
                email = editTextEmail.getText().toString();
                pass1 = editTextPass1.getText().toString();
                pass2 = editTextPass2.getText().toString();

                if(!user.isEmpty() || !email.isEmpty() || !pass1.isEmpty() || !pass2.isEmpty()){
                    if(pass1.matches(pass2)){
                        registrar(user,email,pass1);
                    }else {
                        Toast.makeText(RegisterActivity.this,"Contraseñas diferentes",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(RegisterActivity.this,"Ingresa usuario, correo y contraseña",Toast.LENGTH_SHORT).show();
                }
            }
        });
        mAuthListener = new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser fbUser = firebaseAuth.getCurrentUser();
                if (fbUser !=null){
                    Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else{
                    Log.i("SESION","Sesion cerrada");
                }
            }
        };
    }


    //Metodo para registrar al usuario en Firebase, verifica que no exista un usuario igual
    private void registrar(final String user, final String email, String pass){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){ //Si se registro correctamente
                    FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser(); //Se obtiene la informacion del nuevo usuario para ingresarlo a FB
                    FirebaseDatabase database = FirebaseDatabase.getInstance(); //Se instancia a la base de datos
                    Users newUser = new Users(user,email,authUser.getUid(),null); //se crea al usuario sin ningun registro de peliculas
                    DatabaseReference myUserRef  = database.getReference(FirebaseReferences.MY_MOVIES); //Ingresa al padre
                    myUserRef.child(FirebaseReferences.USERS).child(authUser.getUid()).push().setValue(newUser); //Se ingresa al hijo usuario y se crea un nuveo usuario con nuevos atributos

                }else{ //No se registro correctamente
                    Toast.makeText(RegisterActivity.this,task.getException().getMessage().toString(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //Metodo para castear todos los elementos de la UI
    public void bindUI(){
        editTextUser = (EditText) findViewById(R.id.editTextUserRegistro);
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

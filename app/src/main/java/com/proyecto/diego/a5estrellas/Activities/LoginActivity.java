package com.proyecto.diego.a5estrellas.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.proyecto.diego.a5estrellas.R;

import java.util.EmptyStackException;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    String userFireBaseVerification;
    FirebaseAuth.AuthStateListener mAuthlistener;
    Button btnLogin;
    EditText editTextEmail, editTextPass;
    TextView txtViewGoToRegister, txtViewLoginWarning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindUI(); //Metodo para unir la interfaz con los botones programados

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String password = editTextPass.getText().toString();


                if (login(email, password)) {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                txtViewLoginWarning.setText("Contraseña Incorrecta");
                                //Toast.makeText(LoginActivity.this,"Usuario no registrado",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    saveOnPreferences(email, password);
                }

            }
        });

        mAuthlistener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.i("SESION", "iniciada con " + user.getEmail());
                    goToMainActivity();
                } else {
                    Log.i("SESION", "sesion cerrada");
                }
            }
        };

        txtViewGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentGoToRegister = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intentGoToRegister);
            }
        });

    }


    public void bindUI() {
        btnLogin = (Button) findViewById(R.id.btn_Login);
        editTextEmail = (EditText) findViewById(R.id.editText_email);
        editTextPass = (EditText) findViewById(R.id.editText_pass);
        txtViewGoToRegister = (TextView) findViewById(R.id.textViewGotoRegister);
        txtViewLoginWarning = (TextView) findViewById(R.id.textViewLoginWarning);

    }

    //Este metodo aun no se implementa
    private void setCredencialsIfExist(){
        String email = getUserMailPrefs();
        String pass = getUserPassPrefs();
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)){
            editTextEmail.setText(email);
            editTextPass.setText(pass);
        }
    }


    private boolean login(String email, String pass){
        if(!isaValidEmail(email)){
            Toast.makeText(this,"Ingresa Email Válido",Toast.LENGTH_LONG).show();
            return false;
        }else if (!isValidPass(pass)){
            txtViewLoginWarning.setText("La contraseña tiene que tener mas de 5 caracteres");
            //Toast.makeText(this,"La contraseña tiene que tener mas de 5 caracteres",Toast.LENGTH_LONG).show();
            return false;
        }else {
            return true;
        }
    }

    private void saveOnPreferences(String email, String password){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email",email);
        editor.putString("pass",password);
        editor.apply();
    }

    private boolean isaValidEmail (String email){
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPass (String password){
        return password.length() > 5;
    }

    private void goToMainActivity(){
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private String getUserMailPrefs(){
        return prefs.getString("emal","");
    }
    private String getUserPassPrefs(){
        return prefs.getString("pass","");
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthlistener); //se inicializa el mAuthlistener
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthlistener != null){
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthlistener);
        }
    }
}

package com.proyecto.diego.a5estrellas.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.proyecto.diego.a5estrellas.R;

import java.util.EmptyStackException;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences prefs;

    Button btnLogin;
    EditText editTextEmail, editTextPass;

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
                    goToMainActivity();
                    saveOnPreferences(email, password);
                }
            }
        });

    }


    public void bindUI() {
        btnLogin = (Button) findViewById(R.id.btn_Login);
        editTextEmail = (EditText) findViewById(R.id.editText_email);
        editTextPass = (EditText) findViewById(R.id.editText_pass);
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
            Toast.makeText(this,"Email no valido",Toast.LENGTH_LONG).show();
            return false;
        }else if (!isValidPass(pass)){
            Toast.makeText(this,"La contraseña tiene que tener mas de 5 caracteres",Toast.LENGTH_LONG).show();
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
}

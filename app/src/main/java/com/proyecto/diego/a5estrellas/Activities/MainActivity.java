package com.proyecto.diego.a5estrellas.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SearchView; //Libreria importada para la busqueda
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.proyecto.diego.a5estrellas.R;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //el nombre tiene que ser el mismo que el del loginActivity para recuperar los mismos valores del sharedPreferences
        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        //se implementa el toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
    }

    //Metodos para agregar los iconos del toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Inflater inflater = new Inflater();
        getMenuInflater().inflate(R.menu.menu, menu);


        //SearchView
        // Video de ayuda: https://www.youtube.com/watch?v=hoEY2n8CCSk

        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem menusearch = menu.findItem(R.id.menuSearch); //objeto que se encuentra en menu_search
        SearchView searchView = (SearchView) menusearch.getActionView(); //se instancia un search view para las busquedas

        //se establecen los metodos del SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    //Option del menu para hacer el LogOut
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_logout:
                 logOut();
                 removeSharedPreferences();
                return true;
            case R.id.menu_ayuda:

                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    private void logOut(){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    private void removeSharedPreferences(){
        prefs.edit().clear().apply();
    }

}

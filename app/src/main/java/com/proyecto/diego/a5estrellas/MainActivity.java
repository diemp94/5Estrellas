package com.proyecto.diego.a5estrellas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SearchView; //Libreria importada para la busqueda
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //se implementa el toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
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

}

package com.proyecto.diego.a5estrellas.Fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.proyecto.diego.a5estrellas.Activities.CalificationDialog;
import com.proyecto.diego.a5estrellas.Activities.DescriptionActivity;
import com.proyecto.diego.a5estrellas.Activities.MainActivity;
import com.proyecto.diego.a5estrellas.Adapter.AdapterMyMovie;
import com.proyecto.diego.a5estrellas.Clases.FirebaseReferences;
import com.proyecto.diego.a5estrellas.Clases.ItemLongClickListener;
import com.proyecto.diego.a5estrellas.Clases.MyMovies;
import com.proyecto.diego.a5estrellas.R;

import java.util.ArrayList;


public class MyMoviesFragment extends Fragment {


    public ArrayList<MyMovies> listMyMovies;
    public RecyclerView recyclerViewMovies;
    private RecyclerView.LayoutManager mLayoutManager;
    AdapterMyMovie adapterMyMovie;
    EditText editTextCalification;
    Float calificationUser;

    public MyMoviesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_movies, container, false);;
        setHasOptionsMenu(true); //Indispensable para que muestre el icono del menu en dicho fragment
        recyclerViewMovies = (RecyclerView) view.findViewById(R.id.recycler_myMovies);
        listMyMovies = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(getContext());
        //recyclerViewMovies.setLayoutManager(new LinearLayoutManager(getContext()));
       recyclerViewMovies.setLayoutManager(mLayoutManager);
        fillMovies();

        adapterMyMovie = new AdapterMyMovie(listMyMovies, R.layout.recycler_view_item);

       adapterMyMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(getContext(),"Seleccion "+listMyMovies.get(recyclerViewMovies.getChildAdapterPosition(view)).getNombre() ,Toast.LENGTH_SHORT).show();
                Intent intentDescription = new Intent(getContext(), DescriptionActivity.class);
                intentDescription.putExtra("description",listMyMovies.get(recyclerViewMovies.getChildAdapterPosition(view)));
                startActivity(intentDescription);
            }
        });

       adapterMyMovie.setItemLongClickListener(new ItemLongClickListener() {
           @Override
           public void OnItemLongCLick(View v, int position) {

               // ALERT DIALOG
               String movieName = listMyMovies.get(recyclerViewMovies.getChildAdapterPosition(v)).getNombre() ;

               final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
               LayoutInflater inflater = MyMoviesFragment.this.getLayoutInflater();
               View mView =inflater.inflate(R.layout.calificacion_dialog,null);
               editTextCalification = (EditText)  mView.findViewById(R.id.editTextCalification);
               builder.setView(getActivity().getLayoutInflater().inflate(R.layout.calificacion_dialog,null))
                       .setTitle("Calificar "+ movieName)
                       .setPositiveButton("Calificar", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               String cadena = editTextCalification.getText().toString();
                               if (!cadena.isEmpty()){
                                   calificationUser = Float.parseFloat(cadena);
                                   if (calificationUser > 10){
                                       Toast.makeText(getContext(),"Calificación no valida",Toast.LENGTH_SHORT).show();
                                   }else {
                                       Toast.makeText(getContext(),"Calificación de: "+calificationUser,Toast.LENGTH_SHORT).show();
                                   }
                               }else{
                                   Toast.makeText(getContext(),"Cadena vacia",Toast.LENGTH_SHORT).show();
                               }

                           }
                       })
                       .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {

                           }
                       });
               AlertDialog alert = builder.create();
               alert.show();
           }
       });

        recyclerViewMovies.setAdapter(adapterMyMovie);


        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //SearchView
        // Video de ayuda: https://www.youtube.com/watch?v=hoEY2n8CCSk
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem menusearch = menu.findItem(R.id.menuSearch); //objeto que se encuentra en menu_search
        SearchView searchView = (SearchView) menusearch.getActionView(); //se instancia un search view para las busquedas

        //se establecen los metodos del SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapterMyMovie.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterMyMovie.getFilter().filter(newText);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    //Rellena los datos
    private void fillMovies() {

       FirebaseDatabase database = FirebaseDatabase.getInstance();
       DatabaseReference myMoviesRef = database.getReference(FirebaseReferences.MY_MOVIES); //referencia al padre de FB
       myMoviesRef.child(FirebaseReferences.MOVIES).addValueEventListener(new ValueEventListener() {// Referencia al hijo en FB
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               listMyMovies.removeAll(listMyMovies);
               //Ciclo For que recorre toda la base de datos
               for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                   MyMovies mv = snapshot.getValue(MyMovies.class);
                   listMyMovies.add(mv);
               }
               Log.i("MOVIE",  dataSnapshot.toString());
               adapterMyMovie.notifyDataSetChanged();

           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });



        //Se rellena con esto si se quieren hacer pruebas
       /* listMyMovies.add(new MyMovies("IronMan 2","Sigue mejorando su traje",R.mipmap.ic_launcher));
        listMyMovies.add(new MyMovies("Thor","No es digno y lo mandan a la tierra",R.mipmap.ic_launcher));
        listMyMovies.add(new MyMovies("Thor 3","Es el ragnarok y sale hulk",R.mipmap.ic_launcher));
        listMyMovies.add(new MyMovies("Avengers","primera union de todos los heroes",R.mipmap.ic_launcher));
        listMyMovies.add(new MyMovies("Avengers 2","En esta pelicula muere el que es rapido",R.mipmap.ic_launcher));*/
    }
}

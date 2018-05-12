package com.proyecto.diego.a5estrellas.Fragments;


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
import android.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.proyecto.diego.a5estrellas.Activities.DescriptionActivity;
import com.proyecto.diego.a5estrellas.Adapter.AdapterMyMovie;
import com.proyecto.diego.a5estrellas.Clases.FirebaseReferences;
import com.proyecto.diego.a5estrellas.Clases.ItemLongClickListener;
import com.proyecto.diego.a5estrellas.Clases.MyMovies;
import com.proyecto.diego.a5estrellas.R;

import java.util.ArrayList;


public class MyListFragment extends Fragment {

    public ArrayList<MyMovies> listMyMoviesList;
    public RecyclerView recyclerViewList;
    public TextView textViewListEmpty;
    private RecyclerView.LayoutManager mLayoutManager;
    AdapterMyMovie adapterMyMovieList;
    private boolean isLongClickUsed;

    public MyListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_list, container, false);

        recyclerViewList = (RecyclerView) view.findViewById(R.id.recycler_myList);
        textViewListEmpty = (TextView) view.findViewById(R.id.texViewListEmpty);
        listMyMoviesList = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewList.setLayoutManager(mLayoutManager);
        setHasOptionsMenu(true); //Indispensable para que muestre el icono del menu en dicho fragment
        isLongClickUsed = false; //se inicializa la bandera del long Click en estado bajo
        fillMovies();           //Se rellenan las peliculas
        getListKey();           //Obtiene las llaves de las peliculas generadadas desde "Description Activity"

        adapterMyMovieList = new AdapterMyMovie(listMyMoviesList, R.layout.recycler_view_item);

        adapterMyMovieList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLongClickUsed==false) {
                    Intent intentDescription = new Intent(getContext(), DescriptionActivity.class);
                    intentDescription.putExtra("description", listMyMoviesList.get(recyclerViewList.getChildAdapterPosition(view)));
                    startActivity(intentDescription);
                }else{
                    isLongClickUsed = false; //SI NO SE HIZO UN LONG CLICK SE BAJA LA BANDERA
                }
            }
        });

        adapterMyMovieList.setItemLongClickListener(new ItemLongClickListener() {
            @Override
            public void OnItemLongCLick(View v, int position) {

                View view = v;
                View parent = (View) v.getParent();
                while (!(parent instanceof RecyclerView)){
                    view=parent;
                    parent = (View) parent.getParent();
                }
                int CurrentpositionRecycleViewList = recyclerViewList.getChildAdapterPosition(view);
                isLongClickUsed = true; //BANDERA QUE SE LEVANTA AL HACER UN LONG CLICK
                delete(CurrentpositionRecycleViewList,movieListKey); //LLAMA AL METODO DELETE QUE ELIMINA LA PELICULA DE LA BASE DE DATOS
            }
        });
        recyclerViewList.setAdapter(adapterMyMovieList);
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
                adapterMyMovieList.getFilter().filter(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapterMyMovieList.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    //METODO QUE RELLENA LAS PELICULAS DE LA BASE DE DATOS EN LA SECCION DE CADA USUARIO
    public void fillMovies(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myMoviesRef = database.getReference(FirebaseReferences.MY_MOVIES); //referencia al padre de FB

        //myUserRef.child(FirebaseReferences.USERS).child(authUser.getUid()).child("MiLista").push().setValue(1);
        myMoviesRef.child(FirebaseReferences.USERS).child(currentUser.getUid()).child("MiLista").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listMyMoviesList.removeAll(listMyMoviesList);
                //Ciclo For que recorre toda la base de datos
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    MyMovies mv = snapshot.getValue(MyMovies.class);
                    listMyMoviesList.add(mv);
                    //Log.i("Lista", String.valueOf(listMyMoviesList.contains(mv)));
                }if (!listMyMoviesList.isEmpty()){
                    textViewListEmpty.setVisibility(TextView.INVISIBLE);
                }
                //Log.i("MOVIE",  dataSnapshot.toString());
                adapterMyMovieList.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public ArrayList<String> movieListKey = new ArrayList<>(); //Arreglo que guarda las llaves de las peliculas agegadas a la lista
    //METODO QUE OBTIENE LAS LLAVES DE LAS PELICULAS
    public void getListKey(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myMoviesRef = database.getReference(FirebaseReferences.MY_MOVIES); //referencia al padre de FB
        myMoviesRef.child(FirebaseReferences.USERS).child(currentUser.getUid()).child("MiLista").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Ciclo For que recorre toda la base de datos
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String key = String.valueOf(dataSnapshot.getKey());
                    boolean validKey =false;
                    for(int i=0; i<movieListKey.size();i++){
                        if(key.matches(movieListKey.get(i))){
                            validKey = true;
                        }
                    }
                    if(!validKey) //si la llave no se repeti la añade a la lista
                        movieListKey.add(key);
                }
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                movieListKey.removeAll(movieListKey);
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

    }

    //Metodo que elimina el objeto de "Mi Lista" en la base de datos, requiere de una posicion (del RecyclerView) y del arreglo con las llaves generadas de FireBase
    public void delete(final int currentPosition, final ArrayList<String> movieListKey){
            this.movieListKey = movieListKey;

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myMoviesRef = database.getReference(FirebaseReferences.MY_MOVIES);
        myMoviesRef.child(FirebaseReferences.USERS).child(currentUser.getUid()).child("MiLista").child(movieListKey.get(currentPosition)).setValue(null);
        movieListKey.remove(currentPosition);
    }

}

//SE ELIMINO EL BOTON CREAR, SE UTILIZABA PARA PRUEBAS
/*        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                MyMovies mv = new MyMovies("Avengers","Se reunen todos","https://firebasestorage.googleapis.com/v0/b/estrellas-717e5.appspot.com/o/peliculas%2FNoImage.jpg?alt=media&token=c51c20b5-a378-4c4d-b190-3af51af38732","Info mas detallada");
                //myUserRef.child(FirebaseReferences.USERS).child(authUser.getUid()).child("MiLista").push().setValue(1);
                myMoviesRef.child(FirebaseReferences.USERS).child(currentUser.getUid()).child("MiLista").push().setValue(mv);
            }
        });*/
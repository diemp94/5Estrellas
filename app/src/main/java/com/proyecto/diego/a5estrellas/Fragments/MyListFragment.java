package com.proyecto.diego.a5estrellas.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.proyecto.diego.a5estrellas.Activities.DescriptionActivity;
import com.proyecto.diego.a5estrellas.Activities.LoginActivity;
import com.proyecto.diego.a5estrellas.Adapter.AdapterMyMovie;
import com.proyecto.diego.a5estrellas.Clases.FirebaseReferences;
import com.proyecto.diego.a5estrellas.Clases.MyMovies;
import com.proyecto.diego.a5estrellas.R;

import java.util.ArrayList;


public class MyListFragment extends Fragment {

    public ArrayList<MyMovies> listMyMoviesList;
    public RecyclerView recyclerViewList;
    public TextView textViewListEmpty;
    private RecyclerView.LayoutManager mLayoutManager;
    AdapterMyMovie adapterMyMovieList;

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

        fillMovies();
        adapterMyMovieList = new AdapterMyMovie(listMyMoviesList, R.layout.recycler_view_item);

        adapterMyMovieList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(),"Seleccion "+listMyMoviesList.get(recyclerViewList.getChildAdapterPosition(view)).getNombre() ,Toast.LENGTH_SHORT).show();
                Intent intentDescription = new Intent(getContext(), DescriptionActivity.class);
                intentDescription.putExtra("description",listMyMoviesList.get(recyclerViewList.getChildAdapterPosition(view)));
                startActivity(intentDescription);
            }
        });


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myMoviesRef  = database.getReference(FirebaseReferences.MY_MOVIES);
        recyclerViewList.setAdapter(adapterMyMovieList);
        return view;
    }


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
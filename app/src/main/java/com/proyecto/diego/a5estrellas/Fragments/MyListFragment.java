package com.proyecto.diego.a5estrellas.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.MenuView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.proyecto.diego.a5estrellas.Activities.LoginActivity;
import com.proyecto.diego.a5estrellas.Clases.FirebaseReferences;
import com.proyecto.diego.a5estrellas.Clases.MyMovies;
import com.proyecto.diego.a5estrellas.R;


public class MyListFragment extends Fragment {


    Button btnCrear ;

    public MyListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_list, container, false);

        btnCrear = (Button)view.findViewById(R.id.buttonCrear);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myMoviesRef  = database.getReference(FirebaseReferences.MY_MOVIES);

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyMovies mv = new MyMovies("Avengers","Se reunen todos");
                myMoviesRef.child(FirebaseReferences.MOVIES).push().setValue(mv);
            }
        });


        /*myMoviesRef.child(FirebaseReferences.MOVIES).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                MyMovies mv = new MyMovies();
                mv = dataSnapshot.getValue(MyMovies.class);
                Log.i("MOVIE",  dataSnapshot.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/


        return view;
    }

}

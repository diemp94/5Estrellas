package com.proyecto.diego.a5estrellas.Fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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


public class MyMoviesFragment extends Fragment {


    public ArrayList<MyMovies> listMyMovies;
    public RecyclerView recyclerViewMovies;
    private RecyclerView.LayoutManager mLayoutManager;
    AdapterMyMovie adapterMyMovie;

    public MyMoviesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_movies, container, false);;

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
               Toast.makeText(getContext(),"Click largo "+listMyMovies.get(recyclerViewMovies.getChildAdapterPosition(v)).getInfo() ,Toast.LENGTH_SHORT).show();
           }
       });

        recyclerViewMovies.setAdapter(adapterMyMovie);

        return view;
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
                   if(mv.getNombre()!=null)
                       Log.i("Pel-name",  mv.getNombre());
                   if(mv.getInfo()!=null)
                       Log.i("Pel-info",  mv.getInfo());
                   if(mv.getFoto()!=null)
                       Log.i("Pel-info",  mv.getFoto());

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

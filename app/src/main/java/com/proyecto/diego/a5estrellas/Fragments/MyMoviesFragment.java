package com.proyecto.diego.a5estrellas.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.proyecto.diego.a5estrellas.Adapter.AdapterMyMovie;
import com.proyecto.diego.a5estrellas.Clases.MyMovies;
import com.proyecto.diego.a5estrellas.R;

import java.util.ArrayList;


public class MyMoviesFragment extends Fragment {


    public ArrayList<MyMovies> listMyMovies;
    public RecyclerView recyclerViewMovies;
    private RecyclerView.LayoutManager mLayoutManager;

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
        AdapterMyMovie adapterMyMovie = new AdapterMyMovie(listMyMovies, R.layout.recycler_view_item);
        //adapterMyMovie.notifyDataSetChanged();
        recyclerViewMovies.setAdapter(adapterMyMovie);

        return view;
    }

    private void fillMovies() {
        listMyMovies.add(new MyMovies("IronMan 2","Sigue mejorando su traje",R.mipmap.ic_launcher));
        listMyMovies.add(new MyMovies("Thor","No es digno y lo mandan a la tierra",R.mipmap.ic_launcher));
        listMyMovies.add(new MyMovies("Thor 3","Es el ragnarok y sale hulk",R.mipmap.ic_launcher));
        listMyMovies.add(new MyMovies("Avengers","primera union de todos los heroes",R.mipmap.ic_launcher));
        listMyMovies.add(new MyMovies("Avengers 2","En esta pelicula muere el que es rapido",R.mipmap.ic_launcher));
    }


}

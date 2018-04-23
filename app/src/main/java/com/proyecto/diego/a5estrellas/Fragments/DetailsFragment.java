package com.proyecto.diego.a5estrellas.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.proyecto.diego.a5estrellas.Clases.MyMovies;
import com.proyecto.diego.a5estrellas.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {


    TextView textViewDescripcion;
    ImageView imageViewDescripcion;

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);


        textViewDescripcion = (TextView) view.findViewById(R.id.textDetailsFragment);
        imageViewDescripcion = (ImageView) view.findViewById(R.id.imageDetailFragment);

        Bundle objetoPelicula = getArguments();
        MyMovies movies = null;

        if(objetoPelicula != null){
            movies = (MyMovies) objetoPelicula.getSerializable("objeto"); //mismo nombre del objeto del mainActivity
            //imageViewDescripcion.setImageResource(movies.getFoto());
            textViewDescripcion.setText(movies.getInfoDetalle());
        }

        return view;
    }

}

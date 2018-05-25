package com.proyecto.diego.a5estrellas.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.proyecto.diego.a5estrellas.R;


public class CatalogFragment extends Fragment {

    public RecyclerView recyclerViewMovies;

    public CatalogFragment() {
    }

    FirebaseStorage mStorage; //Crea un objeto que hace referencia a la base de datos de FB
    private ImageView mImageView;
    private TextView textViewMovieName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catalog, container, false);
        mImageView = (ImageView) view.findViewById(R.id.imageViewMovieFB);
        textViewMovieName = (TextView) view.findViewById(R.id.textViewMovieName);

        mStorage = FirebaseStorage.getInstance(); //Instancias el objeto
        StorageReference storageRef = mStorage.getReference();  //se crea el objeto que obtendra los datos de FB
        StorageReference imagesRef = storageRef.child("peliculas").child("avengers.jpg"); //se obtiene la imagen colocando el nombre que contiene en FB
        StorageReference urlstorage = mStorage.getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/estrellas-717e5.appspot.com/o/peliculas%2Fthor1.jpg?alt=media&token=efebe5fc-a802-4007-bac2-a64a4a46a179");
        Glide.with(getContext()).using(new FirebaseImageLoader()).load(urlstorage).into(mImageView);

        return view;
    }
}

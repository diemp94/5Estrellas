package com.proyecto.diego.a5estrellas.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.proyecto.diego.a5estrellas.R;


public class CatalogFragment extends Fragment {


    public CatalogFragment() {
    }

    FirebaseStorage mStorage; //Crea un objeto que hace referencia a la base de datos de FB
    private ImageView mImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catalog, container, false);
        mImageView = (ImageView) view.findViewById(R.id.imageViewMovieFB);

        mStorage = FirebaseStorage.getInstance(); //Instancias el objeto
        StorageReference storageRef = mStorage.getReference();  //se crea el objeto que obtendra los datos de FB
        StorageReference imagesRef = storageRef.child("peliculas").child("avengers.jpg"); //se obtiene la imagen colocando el nombre que contiene en FB
        Glide.with(getContext()).using(new FirebaseImageLoader()).load(imagesRef).into(mImageView); //Libreria glide sirve para Cargar la imagen en el imageView

        return view;
    }
}

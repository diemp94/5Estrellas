package com.proyecto.diego.a5estrellas.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.proyecto.diego.a5estrellas.Clases.MyMovies;
import com.proyecto.diego.a5estrellas.R;

public class DescriptionActivity extends AppCompatActivity {

    //FIREBASE
    FirebaseStorage mStorage; //Crea un objeto que hace referencia a la base de datos de FB
    ImageView imageViewDescription;
    TextView txtViewNameDescription, txtViewDescription, txtViewClasification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        bindUI();
        MyMovies mv = (MyMovies) getIntent().getExtras().getSerializable("description");

        mStorage = FirebaseStorage.getInstance(); //Instancias el objeto
        StorageReference urlstorage = mStorage.getReferenceFromUrl(mv.getFoto()); //Obtiene la URL del objeto actual
        Glide.with(imageViewDescription.getContext()).using(new FirebaseImageLoader()).load(urlstorage).into(imageViewDescription);
        txtViewNameDescription.setText(mv.getNombre());
        txtViewDescription.setText(mv.getInfoDetalle());
    }

    public void bindUI(){
        imageViewDescription = (ImageView) findViewById(R.id.imageViewDescription);
        txtViewNameDescription = (TextView) findViewById(R.id.textViewNameDescription);
        txtViewDescription = (TextView) findViewById(R.id.textViewDescription);
        txtViewClasification = (TextView) findViewById(R.id.textViewClasification);
    }
}

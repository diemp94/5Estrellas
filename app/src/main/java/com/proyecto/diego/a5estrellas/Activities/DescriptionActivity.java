package com.proyecto.diego.a5estrellas.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.proyecto.diego.a5estrellas.Clases.FirebaseReferences;
import com.proyecto.diego.a5estrellas.Clases.MyMovies;
import com.proyecto.diego.a5estrellas.Fragments.MyListFragment;
import com.proyecto.diego.a5estrellas.R;

import java.util.ArrayList;

public class DescriptionActivity extends AppCompatActivity {

    //FIREBASE
    FirebaseStorage mStorage; //Crea un objeto que hace referencia a la base de datos de FB
    ImageView imageViewDescription;
    TextView txtViewNameDescription, txtViewDescription, txtViewClasification;
    Button btnAddToMyList;
    MyMovies mv;
    public ArrayList<MyMovies> listMyMoviesList;
    public boolean movieOnMyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        bindUI();
        mv = (MyMovies) getIntent().getExtras().getSerializable("description");
        listMyMoviesList = new ArrayList<>();
        movieOnMyList = false;
        fillMovies(mv);

        mStorage = FirebaseStorage.getInstance(); //Instancias el objeto
        StorageReference urlstorage = mStorage.getReferenceFromUrl(mv.getFoto()); //Obtiene la URL del objeto actual
        Glide.with(imageViewDescription.getContext()).using(new FirebaseImageLoader()).load(urlstorage).into(imageViewDescription);
        txtViewNameDescription.setText(mv.getNombre());
        txtViewDescription.setText(mv.getInfoDetalle());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myMoviesRef  = database.getReference(FirebaseReferences.MY_MOVIES);

        btnAddToMyList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                fillMovies(mv);
                if(movieOnMyList){
                    Toast.makeText(DescriptionActivity.this,"Ya se encuentra en tu lista",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(DescriptionActivity.this,"Agregada a tu lista",Toast.LENGTH_SHORT).show();
                    myMoviesRef.child(FirebaseReferences.USERS).child(currentUser.getUid()).child("MiLista").push().setValue(mv);
                }
            }
        });

    }

    public void fillMovies(final MyMovies mv){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myMoviesRef = database.getReference(FirebaseReferences.MY_MOVIES); //referencia al padre de FB
        this.mv = mv;
        myMoviesRef.child(FirebaseReferences.USERS).child(currentUser.getUid()).child("MiLista").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listMyMoviesList.removeAll(listMyMoviesList);
                //Ciclo For que recorre toda la base de datos
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    MyMovies mvfill = snapshot.getValue(MyMovies.class);
                    listMyMoviesList.add(mvfill);
                    Log.i("mv ",mv.getNombre());
                    Log.i("mv fill",mvfill.getNombre());
                    Log.i("mv final", String.valueOf(mv.getNombre().equals(mvfill.getNombre())));
                    //Log.i("mv final", String.valueOf(mv.getFoto().toString().matches(mvfill.getFoto().toString())));
                    if(mvfill.getFoto().equals(mv.getFoto())){
                        movieOnMyList = true;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void bindUI(){
        imageViewDescription = (ImageView) findViewById(R.id.imageViewDescription);
        txtViewNameDescription = (TextView) findViewById(R.id.textViewNameDescription);
        txtViewDescription = (TextView) findViewById(R.id.textViewDescription);
        txtViewClasification = (TextView) findViewById(R.id.textViewClasification);
        btnAddToMyList = (Button) findViewById(R.id.buttonDescripcionAgregarAMiLista);
    }
}

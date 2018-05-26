package com.proyecto.diego.a5estrellas.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.proyecto.diego.a5estrellas.Adapter.AdapterNews;
import com.proyecto.diego.a5estrellas.Clases.FirebaseReferences;
import com.proyecto.diego.a5estrellas.Clases.MyMovies;
import com.proyecto.diego.a5estrellas.Clases.News;
import com.proyecto.diego.a5estrellas.R;

import java.util.ArrayList;


public class CatalogFragment extends Fragment {

    public ArrayList<News> listNews;
    public RecyclerView recyclerViewNews;
    private RecyclerView.LayoutManager mLayoutManager;
    AdapterNews adapterNews;

    public CatalogFragment() {
    }

    FirebaseStorage mStorage; //Crea un objeto que hace referencia a la base de datos de FB
    private ImageView mImageView;
    private TextView textViewMovieName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catalog, container, false);
        recyclerViewNews = (RecyclerView) view.findViewById(R.id.recycler_news);
        listNews = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(getContext());

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        recyclerViewNews.setLayoutManager(mLayoutManager);
        fillNews();
        adapterNews = new AdapterNews(listNews, R.layout.card_view_item);

        adapterNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(),"perro",Toast.LENGTH_SHORT).show();
                String url = listNews.get(recyclerViewNews.getChildAdapterPosition(view)).getUrl();
                Log.i("URLNEW",url);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        recyclerViewNews.setAdapter(adapterNews);
        return view;
    }

    private void fillNews() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myMoviesRef = database.getReference(FirebaseReferences.MY_MOVIES); //referencia al padre de FB
        myMoviesRef.child(FirebaseReferences.NEWS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listNews.removeAll(listNews);
                //Ciclo For que recorre toda la base de datos
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    News news = snapshot.getValue(News.class);
                    listNews.add(news);
                }
                Log.i("MOVIE",  dataSnapshot.toString());
                adapterNews.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
    });
    }
}

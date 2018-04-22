package com.proyecto.diego.a5estrellas.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.proyecto.diego.a5estrellas.Activities.MainActivity;
import com.proyecto.diego.a5estrellas.Clases.MyMovies;
import com.proyecto.diego.a5estrellas.Fragments.MyMoviesFragment;
import com.proyecto.diego.a5estrellas.R;

import java.util.ArrayList;

public class AdapterMyMovie extends RecyclerView.Adapter<AdapterMyMovie.ViewHolderMyMovie> {

    FirebaseStorage mStorage; //Crea un objeto que hace referencia a la base de datos de FB

    ArrayList<MyMovies> listMovies;
    private int layout;

    public AdapterMyMovie(ArrayList<MyMovies> listMovies, int layout) {
        this.listMovies = listMovies;
        this.layout = layout;
    }

    @Override
    public AdapterMyMovie.ViewHolderMyMovie onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);

        return new ViewHolderMyMovie(view);
    }

    @Override
    public void onBindViewHolder(AdapterMyMovie.ViewHolderMyMovie holder, int position) {
        holder.txtNameMyMovie.setText(listMovies.get(position).getNombre());
        holder.txtInfoMyMovie.setText(listMovies.get(position).getInfo());

       //AGREGA LAS IMAGENES DESDE FIREBASE UTILIZANDO UNA URL
        mStorage = FirebaseStorage.getInstance(); //Instancias el objeto
        StorageReference urlstorage = mStorage.getReferenceFromUrl(listMovies.get(position).getFoto()); //Obtiene la URL del objeto actual
        Glide.with(holder.fotoMyMovie.getContext()).using(new FirebaseImageLoader()).load(urlstorage).into(holder.fotoMyMovie);

        //SECCION HARDCODEADA PARA PRUEBAS, TIENE QUE AGARRAR LOS VALORES DESDE LA BASE DE DATOS
        //holder.fotoMyMovie.setImageResource(R.mipmap.ic_launcher);
        //holder.fotoMyMovie.setImageResource(listMovies.get(position).getFoto());
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }


    //Se castean los objetos con el UI
    public class ViewHolderMyMovie extends RecyclerView.ViewHolder {

        TextView txtNameMyMovie, txtInfoMyMovie;
        ImageView fotoMyMovie;

        public ViewHolderMyMovie(View itemView) {
            super(itemView);
            txtNameMyMovie = (TextView) itemView.findViewById(R.id.idNameMyMovie);
            txtInfoMyMovie = (TextView) itemView.findViewById(R.id.idInfoMyMovie);
            fotoMyMovie = (ImageView) itemView.findViewById(R.id.idImageMyMovie);
        }
    }


}

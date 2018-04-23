package com.proyecto.diego.a5estrellas.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.proyecto.diego.a5estrellas.Activities.MainActivity;
import com.proyecto.diego.a5estrellas.Clases.MyMovies;
import com.proyecto.diego.a5estrellas.Fragments.MyMoviesFragment;
import com.proyecto.diego.a5estrellas.R;

import java.util.ArrayList;

public class AdapterMyMovie extends RecyclerView.Adapter<AdapterMyMovie.ViewHolderMyMovie> implements View.OnClickListener, View.OnLongClickListener {

    //FIREBASE
    FirebaseStorage mStorage; //Crea un objeto que hace referencia a la base de datos de FB
    //LISTA DE PELICULAS (SON OBJETOS)
    ArrayList<MyMovies> listMovies;
    private int layout;

    //VIEW DEL ONCLICK
    private View.OnClickListener listener;
    private View.OnLongClickListener longListener;

    public AdapterMyMovie(ArrayList<MyMovies> listMovies, int layout) {
        this.listMovies = listMovies;
        this.layout = layout;
    }

    @Override
    public AdapterMyMovie.ViewHolderMyMovie onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);


        view.setOnClickListener(this);

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

        //SECCION HARDCODEADA PARA PRUEBAS
        //holder.fotoMyMovie.setImageResource(R.mipmap.ic_launcher);
        //holder.fotoMyMovie.setImageResource(listMovies.get(position).getFoto());
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    public void setOnLongClickListener(View.OnLongClickListener longListener){
        this.longListener = longListener;
    }

    //el metodo lo exige el IMPLEMENT
    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    //el metodo lo exige el IMPLEMENT
    @Override
    public boolean onLongClick(View view) {
        longListener.onLongClick(view);
        Toast.makeText(view.getContext(),"Click largo ",Toast.LENGTH_SHORT).show();
        return false;

    }


    //Se castean los objetos con el UI
    public class ViewHolderMyMovie extends RecyclerView.ViewHolder {

        TextView txtNameMyMovie, txtInfoMyMovie;
        ImageView fotoMyMovie;
        ImageButton btnDescripcion;

        public ViewHolderMyMovie(View itemView) {
            super(itemView);
            txtNameMyMovie = (TextView) itemView.findViewById(R.id.idNameMyMovie);
            txtInfoMyMovie = (TextView) itemView.findViewById(R.id.idInfoMyMovie);
            fotoMyMovie = (ImageView) itemView.findViewById(R.id.idImageMyMovie);


           /* btnDescripcion = (ImageButton) itemView.findViewById(R.id.imageButtonDescripcion);

            btnDescripcion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        Toast.makeText(view.getContext(),"Presionado"+position,Toast.LENGTH_SHORT);
                    }
                }
            });*/

        }
    }


}

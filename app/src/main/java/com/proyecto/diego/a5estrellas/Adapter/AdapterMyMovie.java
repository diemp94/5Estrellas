package com.proyecto.diego.a5estrellas.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.proyecto.diego.a5estrellas.Clases.MyMovies;
import com.proyecto.diego.a5estrellas.R;

import java.util.ArrayList;

public class AdapterMyMovie extends RecyclerView.Adapter<AdapterMyMovie.ViewHolderMyMovie> {

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
        holder.fotoMyMovie.setImageResource(listMovies.get(position).getFoto());
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }



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

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
import com.proyecto.diego.a5estrellas.Clases.News;
import com.proyecto.diego.a5estrellas.R;

import java.util.ArrayList;

public class AdapterNews extends RecyclerView.Adapter<AdapterNews.ViewHolderNews> implements View.OnClickListener {

    //FIREBASE
    FirebaseStorage mStorage; //Crea un objeto que hace referencia a la base de datos de FB

    //LISTA DE NOTICIAS (SON OBJETOS)
    ArrayList<News> listNews;
    private int layout;

    //VIEW DEL ONCLICK
    private View.OnClickListener listener;

    public AdapterNews(ArrayList<News> listNews, int layout){
        this.listNews = listNews;
        this.layout = layout;
    }

    @Override
    public ViewHolderNews onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        view.setOnClickListener(this);
        return new ViewHolderNews(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderNews holder, int position) {
        holder.textViewNewName.setText(listNews.get(position).getNombre());
        //AGREGA LAS IMAGENES DESDE FIREBASE UTILIZANDO UNA URL
        mStorage = FirebaseStorage.getInstance(); //Instancias el objeto
        StorageReference urlstorage = mStorage.getReferenceFromUrl(listNews.get(position).getImagen()); //Obtiene la URL del objeto actual
        Glide.with(holder.imageViewNew.getContext()).using(new FirebaseImageLoader()).load(urlstorage).into(holder.imageViewNew);
    }


    @Override
    public int getItemCount() {
        return listNews.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }

    public class ViewHolderNews extends RecyclerView.ViewHolder{

        ImageView imageViewNew;
        TextView textViewNewName;

        public ViewHolderNews (View itemView){
            super(itemView);

            imageViewNew = (ImageView) itemView.findViewById(R.id.imageViewNews);
            textViewNewName = (TextView) itemView.findViewById(R.id.textViewMovieName);

        }

    }

}

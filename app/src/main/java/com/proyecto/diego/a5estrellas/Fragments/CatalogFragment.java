package com.proyecto.diego.a5estrellas.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.proyecto.diego.a5estrellas.R;


public class CatalogFragment extends Fragment {


    public CatalogFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_catalog, container, false);
        return view;
    }

}

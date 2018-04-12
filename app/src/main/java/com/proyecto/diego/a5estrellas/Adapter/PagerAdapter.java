package com.proyecto.diego.a5estrellas.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.proyecto.diego.a5estrellas.Fragments.CatalogFragment;
import com.proyecto.diego.a5estrellas.Fragments.MyListFragment;
import com.proyecto.diego.a5estrellas.Fragments.MyMoviesFragment;

/**
 * Created by diego on 12/04/18.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;

    public PagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new MyMoviesFragment();
            case 1:
                return new CatalogFragment();
            case 2:
                return new MyListFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}

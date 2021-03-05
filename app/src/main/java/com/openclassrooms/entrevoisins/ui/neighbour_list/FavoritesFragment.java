package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.data.FavoritesPref;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FavoritesFragment extends Fragment {

    private NeighbourApiService mApiService;
    private Neighbour mNeighbour;
    private RecyclerView mRecyclerView;
    private List<Neighbour >mNeighboursFavoritesList = new ArrayList<>();
    private ArrayList<Integer> myFavListId;



    /**
     * Create and return a new instance
     * @return @{@link FavoritesFragment}
     */
    public static FavoritesFragment newInstance() {
        FavoritesFragment fragment = new FavoritesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getNeighbourApiService();
        myFavListId = FavoritesPref.readListFav(getActivity());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorites_collection, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(new FavoritesAdapter(mNeighboursFavoritesList));

        return view;
    }

    /**
     * Init the List of neighbours
     */
    private void initList() {

            // si ma liste id est differente de null alors j'ajoute un voisin a la litse Favoris
            if(myFavListId != null) {

                for (int i = 0; i < myFavListId.size(); i++) {

                    int id = myFavListId.get(i);
                    mNeighbour = mApiService.getNeighbours().get(id);
                    mNeighboursFavoritesList.add(mNeighbour);
                }

            }
    }


    @Override
    public void onResume() {
        super.onResume();
        initList();
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    }


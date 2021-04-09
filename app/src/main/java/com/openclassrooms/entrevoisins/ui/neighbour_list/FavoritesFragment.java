package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.content.Intent;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import static java.util.stream.Collectors.toList;


public class FavoritesFragment extends Fragment implements MyNeighbourRecyclerViewAdapter.NeighbourListener {

    private NeighbourApiService mApiService;
    private RecyclerView mRecyclerView;
    private List<Neighbour> mNeighbours;
    private List<Neighbour> mNeighboursFav;
    private Neighbour mNeighbour;


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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorites_collection, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        return view;
    }

    /**
     * Init the List of neighbours
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initList() {

        mNeighbours =  mApiService.getNeighbours();
        // filtre les voisins marqué comme "Favoris" pour n'affciher qu'eux
        mNeighboursFav = mNeighbours.stream().filter(Neighbour::isFavorite).collect(toList());
        mRecyclerView.setAdapter(new MyNeighbourRecyclerViewAdapter(mNeighboursFav, this));
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResume() {
        super.onResume();
        initList();
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

    }


    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Subscribe
    public void onDeleteNeighbour(DeleteNeighbourEvent event) {
        mApiService.deleteNeighbour(event.neighbour);
        initList();
    }

    @Override
    public void neighbourClick(int position) {
        Intent neighbourActivity = new Intent (getActivity(), NeighbourActivity.class);
        neighbourActivity.putExtra("ObjNeighbour",mNeighboursFav.get(position));
        mNeighbour = mNeighboursFav.get(position);
        neighbourActivity.putExtra("favorite",mNeighbour.isFavorite());
        startActivity(neighbourActivity);
    }
}

